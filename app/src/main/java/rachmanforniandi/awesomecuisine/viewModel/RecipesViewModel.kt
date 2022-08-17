package rachmanforniandi.awesomecuisine.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import rachmanforniandi.awesomecuisine.networkUtils.Constants
import rachmanforniandi.awesomecuisine.networkUtils.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import rachmanforniandi.awesomecuisine.networkUtils.Constants.Companion.QUERY_API_KEY
import rachmanforniandi.awesomecuisine.networkUtils.Constants.Companion.QUERY_DIET
import rachmanforniandi.awesomecuisine.networkUtils.Constants.Companion.QUERY_FILL_INGREDIENTS
import rachmanforniandi.awesomecuisine.networkUtils.Constants.Companion.QUERY_NUMBER
import rachmanforniandi.awesomecuisine.networkUtils.Constants.Companion.QUERY_TYPE

class RecipesViewModel(application: Application) : AndroidViewModel(application) {

    fun applyQueries():HashMap<String,String>{
        val queries:HashMap<String,String> = HashMap()
        queries[QUERY_NUMBER]="50"
        queries[QUERY_API_KEY]= Constants.API_KEY
        queries[QUERY_TYPE]="snack"
        queries[QUERY_DIET]="vegan"
        queries[QUERY_ADD_RECIPE_INFORMATION]="true"
        queries[QUERY_FILL_INGREDIENTS]="true"
        return queries
    }

}