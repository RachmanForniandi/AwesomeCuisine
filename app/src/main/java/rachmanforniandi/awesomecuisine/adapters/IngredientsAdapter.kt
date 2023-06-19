package rachmanforniandi.awesomecuisine.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import rachmanforniandi.awesomecuisine.R
import rachmanforniandi.awesomecuisine.databinding.ItemIngredientsBinding
import rachmanforniandi.awesomecuisine.models.ExtendedIngredient
import rachmanforniandi.awesomecuisine.util.Constants.Companion.BASE_IMAGE_URL
import rachmanforniandi.awesomecuisine.util.RecipesDiffUtil
import java.util.Locale

class IngredientsAdapter:RecyclerView.Adapter<IngredientsAdapter.IngredientsHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    class IngredientsHolder(view: ItemIngredientsBinding):RecyclerView.ViewHolder(view.root) {
        val imageOfIngredients = view.imgIngredients
        val txtOfIngredientsName = view.tvIngredientName
        val txtOfIngredientsAmount = view.tvIngredientAmount
        val txtOfIngredientsUnit = view.tvIngredientUnit
        val txtOfIngredientsConsistency = view.tvIngredientConsistency
        val txtOfIngredientsOriginal = view.tvIngredientOriginal

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsHolder {
        return IngredientsHolder(ItemIngredientsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: IngredientsHolder, position: Int) {
        val ingredient = ingredientsList[position]
        holder.txtOfIngredientsName.text = ingredient.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(
            Locale.ROOT) else it.toString() }
        holder.txtOfIngredientsAmount.text = ingredient.amount.toString()
        holder.txtOfIngredientsUnit.text = ingredient.unit
        holder.txtOfIngredientsConsistency.text = ingredient.consistency
        holder.txtOfIngredientsOriginal.text = ingredient.original
        holder.imageOfIngredients.load(
            BASE_IMAGE_URL + ingredientsList[position].image){
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }
    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientsDiffUtil =
            RecipesDiffUtil(ingredientsList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}