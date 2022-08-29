package rachmanforniandi.awesomecuisine.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import rachmanforniandi.awesomecuisine.data.database.entities.FavoritesEntity
import rachmanforniandi.awesomecuisine.databinding.ItemRecipesFavoriteBinding
import rachmanforniandi.awesomecuisine.util.RecipesDiffUtil


class FavoriteRecipesAdapter: RecyclerView.Adapter<FavoriteRecipesAdapter.FavoriteRecipesHolder>()  {
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
    }

    override fun getItemCount(): Int {
        return favRecipes.size
    }

    fun setData(newFavoritesRecipes: List<FavoritesEntity>){
        val favRecipesDiffUtil = RecipesDiffUtil(favRecipes,newFavoritesRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favRecipesDiffUtil)
        favRecipes = newFavoritesRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }
}