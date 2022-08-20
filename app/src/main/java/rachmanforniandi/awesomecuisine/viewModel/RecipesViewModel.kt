package rachmanforniandi.awesomecuisine.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import rachmanforniandi.awesomecuisine.util.Constants
import rachmanforniandi.awesomecuisine.util.Constants.Companion.DEFAULT_DIET_TYPE
import rachmanforniandi.awesomecuisine.util.Constants.Companion.DEFAULT_MEAL_TYPE
import rachmanforniandi.awesomecuisine.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import rachmanforniandi.awesomecuisine.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import rachmanforniandi.awesomecuisine.util.Constants.Companion.QUERY_API_KEY
import rachmanforniandi.awesomecuisine.util.Constants.Companion.QUERY_DIET
import rachmanforniandi.awesomecuisine.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import rachmanforniandi.awesomecuisine.util.Constants.Companion.QUERY_NUMBER
import rachmanforniandi.awesomecuisine.util.Constants.Companion.QUERY_TYPE

class RecipesViewModel(application: Application) : AndroidViewModel(application) {

    fun applyQueries():HashMap<String,String>{
        val queries:HashMap<String,String> = HashMap()
        queries[QUERY_NUMBER]= DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY]= Constants.API_KEY
        queries[QUERY_TYPE]=DEFAULT_MEAL_TYPE
        queries[QUERY_DIET]=DEFAULT_DIET_TYPE
        queries[QUERY_ADD_RECIPE_INFORMATION]="true"
        queries[QUERY_FILL_INGREDIENTS]="true"
        return queries
    }

}