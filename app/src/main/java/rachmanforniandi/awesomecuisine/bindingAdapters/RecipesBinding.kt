package rachmanforniandi.awesomecuisine.bindingAdapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import rachmanforniandi.awesomecuisine.data.database.entities.RecipesEntity
import rachmanforniandi.awesomecuisine.models.FoodRecipe
import rachmanforniandi.awesomecuisine.util.NetworkResult

class RecipesBinding {

    companion object{

        @BindingAdapter("readApiResponse","readDatabase", requireAll = true)
        @JvmStatic
        fun handleReadDataErrors(view: View,apiResponse:NetworkResult<FoodRecipe>?,
        database:List<RecipesEntity>?) {
            when(view){
                is ImageView->{
                    view.isVisible =apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                }
                is TextView->{
                    view.isVisible =apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                    view.text = apiResponse?.message.toString()
                }
            }
        }

        /*@BindingAdapter("readApiResponseForText","readDatabaseForText", requireAll = true)
        @JvmStatic
        fun errorTextViewVisibility(textView: TextView, apiResponse:NetworkResult<FoodRecipe>?,
                                    database:List<RecipesEntity>?) {
            if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
                textView.visibility = View.VISIBLE
                textView.text = apiResponse.message.toString()
            } else if (apiResponse is NetworkResult.Loading) {
                textView.visibility = View.INVISIBLE
            } else if (apiResponse is NetworkResult.Success) {
                textView.visibility = View.INVISIBLE
            }
        }*/
    }
}