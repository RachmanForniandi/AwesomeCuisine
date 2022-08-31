package rachmanforniandi.awesomecuisine.adapters

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recipes_favorite.view.*
import rachmanforniandi.awesomecuisine.R
import rachmanforniandi.awesomecuisine.data.database.entities.FavoritesEntity
import rachmanforniandi.awesomecuisine.databinding.ItemRecipesFavoriteBinding
import rachmanforniandi.awesomecuisine.ui.fragments.FavoriteRecipesFragmentDirections
import rachmanforniandi.awesomecuisine.util.RecipesDiffUtil


class FavoriteRecipesAdapter(private val requireActivity: FragmentActivity): RecyclerView.Adapter<FavoriteRecipesAdapter.FavoriteRecipesHolder>(),ActionMode.Callback  {
    private var favRecipes = emptyList<FavoritesEntity>()
    private var multiSelection = false
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
        val currentRecipe = favRecipes[position]
        holder.bind(currentRecipe)

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
                multiSelection = false
                false
            }

        }
    }

    private fun applySelection(holder:FavoriteRecipesHolder,currentRecipe:FavoritesEntity){
        if (selectedRecipes.contains(currentRecipe)){
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder,R.color.cardBackgroundColor,R.color.strokeColor)
        }else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder,R.color.cardBackgroundLightColor,R.color.colorPrimary)
        }
    }

    private fun changeRecipeStyle(holder:FavoriteRecipesHolder, backgroundColor:Int, strokeColor:Int){
        holder.itemView.favRecipesRowLayout.setBackgroundColor(
            ContextCompat.getColor(requireActivity,backgroundColor)
        )
        holder.itemView.row_recipes_fav.strokeColor =
            ContextCompat.getColor(requireActivity,strokeColor)
    }

    override fun getItemCount(): Int {
        return favRecipes.size
    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorites_contextual_menu,menu)
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
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
}