package rachmanforniandi.awesomecuisine.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import rachmanforniandi.awesomecuisine.databinding.ItemRecipesBinding
import rachmanforniandi.awesomecuisine.databinding.RecipesRowLayoutBinding
import rachmanforniandi.awesomecuisine.models.FoodRecipe
import rachmanforniandi.awesomecuisine.models.Result
import rachmanforniandi.awesomecuisine.util.RecipesDiffUtil

class RecipesAdapter: RecyclerView.Adapter<RecipesAdapter.RecipesHolder>() {
    private var recipes = emptyList<Result>()

    class RecipesHolder (private val binding: RecipesRowLayoutBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): RecipesHolder {
                val binding= RecipesRowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                return RecipesHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesHolder {
        return RecipesHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecipesHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setData(newData:FoodRecipe){
        val recipesDiffUtil = RecipesDiffUtil(recipes,newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }



}