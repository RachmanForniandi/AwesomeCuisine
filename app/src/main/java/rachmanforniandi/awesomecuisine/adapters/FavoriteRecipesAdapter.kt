package rachmanforniandi.awesomecuisine.adapters

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_favorite_recipes.view.*
import kotlinx.android.synthetic.main.item_recipes_favorite.view.*
import rachmanforniandi.awesomecuisine.R
import rachmanforniandi.awesomecuisine.data.database.entities.FavoritesEntity
import rachmanforniandi.awesomecuisine.databinding.ItemRecipesFavoriteBinding
import rachmanforniandi.awesomecuisine.ui.fragments.FavoriteRecipesFragmentDirections
import rachmanforniandi.awesomecuisine.util.RecipesDiffUtil


class FavoriteRecipesAdapter(private val requireActivity: FragmentActivity): RecyclerView.Adapter<FavoriteRecipesAdapter.FavoriteRecipesHolder>(),ActionMode.Callback  {
    private var favRecipes = emptyList<FavoritesEntity>()

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
        val favRecipe = favRecipes[position]
        holder.bind(favRecipe)

        /**
         * Single Click Listener
         * */
        holder.itemView.favRecipesMainLayout.setOnClickListener {
            val action = FavoriteRecipesFragmentDirections
                .actionFavoriteRecipesFragmentToDetailsActivity(favRecipe.result)
            holder.itemView.findNavController().navigate(action)
        }

        /**
         * long Click Listener
         * */
        holder.itemView.favRecipesMainLayout.setOnClickListener {
            requireActivity.startActionMode(this)
            true
        }
    }

    override fun getItemCount(): Int {
        return favRecipes.size
    }



    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorites_contextual_menu,menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {

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