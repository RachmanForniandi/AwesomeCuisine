package rachmanforniandi.awesomecuisine.adapters

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_recipes_favorite.view.*
import rachmanforniandi.awesomecuisine.R
import rachmanforniandi.awesomecuisine.data.database.entities.FavoritesEntity
import rachmanforniandi.awesomecuisine.databinding.ItemRecipesFavoriteBinding
import rachmanforniandi.awesomecuisine.ui.fragments.FavoriteRecipesFragmentDirections
import rachmanforniandi.awesomecuisine.util.RecipesDiffUtil
import rachmanforniandi.awesomecuisine.viewModel.MainViewModel


class FavoriteRecipesAdapter(private val requireActivity: FragmentActivity,
private val mainViewModel: MainViewModel): RecyclerView.Adapter<FavoriteRecipesAdapter.FavoriteRecipesHolder>(),ActionMode.Callback  {
    private var favRecipes = emptyList<FavoritesEntity>()
    private var multiSelection = false
    private lateinit var mActionMode:ActionMode
    private lateinit var rootView: View
    private var mViewHolder = arrayListOf<FavoriteRecipesHolder>()
    private var selectedRecipes = arrayListOf<FavoritesEntity>()

    class FavoriteRecipesHolder (private val binding: ItemRecipesFavoriteBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(favoritesEntity: FavoritesEntity) {
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRecipesHolder {
        return FavoriteRecipesHolder(ItemRecipesFavoriteBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: FavoriteRecipesHolder, position: Int) {
        mViewHolder.add(holder)
        rootView = holder.itemView.rootView

        val currentRecipe = favRecipes[position]
        holder.bind(currentRecipe)
        saveItemStateOnScroll(currentRecipe,holder)

        /**
         * Single Click Listener
         * */
        holder.itemView.favRecipesRowLayout.setOnClickListener {
            if (multiSelection){
                applySelection(holder, currentRecipe)
            }else{
                val action = FavoriteRecipesFragmentDirections
                    .actionFavoriteRecipesFragmentToDetailsActivity(currentRecipe.result)
                holder.itemView.findNavController().navigate(action)
            }
        }

        /**
         * long Click Listener
         * */
        holder.itemView.favRecipesRowLayout.setOnLongClickListener {
            if (!multiSelection){
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder,currentRecipe)
                true
            }else{
                applySelection(holder,currentRecipe)
                true
            }

        }
    }

    private fun saveItemStateOnScroll(currentRecipe:FavoritesEntity,holder:FavoriteRecipesHolder){
        if (selectedRecipes.contains(currentRecipe)){
            changeRecipeStyle(holder,R.color.cardBackgroundLightColor,R.color.colorPrimary)
        }else {
            changeRecipeStyle(holder,R.color.cardBackgroundColor,R.color.strokeColor)

        }
    }
    private fun applySelection(holder:FavoriteRecipesHolder,currentRecipe:FavoritesEntity){
        if (selectedRecipes.contains(currentRecipe)){
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder,R.color.cardBackgroundColor,R.color.strokeColor)
            applyActionModeTitle()
        }else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder,R.color.cardBackgroundLightColor,R.color.colorPrimary)
            applyActionModeTitle()
        }
    }

    private fun changeRecipeStyle(holder:FavoriteRecipesHolder, backgroundColor:Int, strokeColor:Int){
        holder.itemView.favRecipesRowLayout.setBackgroundColor(
            ContextCompat.getColor(requireActivity,backgroundColor)
        )
        holder.itemView.row_recipes_fav.strokeColor =
            ContextCompat.getColor(requireActivity,strokeColor)
    }

    private fun applyActionModeTitle(){
        when(selectedRecipes.size){
            0->{
                mActionMode.finish()
                multiSelection = false
            }
            1->{
                mActionMode.title = "${selectedRecipes.size} item selected"
            }
            else->{
                mActionMode.title = "${selectedRecipes.size} items selected"
            }
        }
    }

    override fun getItemCount(): Int {
        return favRecipes.size
    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorites_contextual_menu,menu)
        mActionMode = actionMode!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, menu: MenuItem?): Boolean {
        if (menu?.itemId == R.id.delete_favorite_recipe_menu){
            selectedRecipes.forEach {
                mainViewModel.deleteFavoriteRecipesData(it)
            }
            showSnackBar("${selectedRecipes.size} Recipe/s removed.")

            multiSelection = false
            selectedRecipes.clear()
            mode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        mViewHolder.forEach { holder->
            changeRecipeStyle(holder,R.color.cardBackgroundColor,R.color.strokeColor)
        }
        multiSelection = false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    private fun applyStatusBarColor(color:Int){
        requireActivity.window.statusBarColor =
            ContextCompat.getColor(requireActivity,color)
    }
    fun setData(newFavoritesRecipes: List<FavoritesEntity>){
        val favRecipesDiffUtil = RecipesDiffUtil(favRecipes,newFavoritesRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favRecipesDiffUtil)
        favRecipes = newFavoritesRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private fun showSnackBar(message:String){
        Snackbar.make(
            rootView,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}
            .show()
    }

    fun clearContextualActionMode(){
        if (this::mActionMode.isInitialized){
            mActionMode.finish()
        }
    }
}