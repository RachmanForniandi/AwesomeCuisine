package rachmanforniandi.awesomecuisine.data

import rachmanforniandi.awesomecuisine.models.FoodRecipe
import rachmanforniandi.awesomecuisine.data.networkUtils.ApiRecipeService
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val recipeService: ApiRecipeService) {

    suspend fun getRecipesData(queries:Map<String,String>):Response<FoodRecipe>{
        return recipeService.getRecipes(queries)
    }

    suspend fun searchRecipesData(queries:Map<String,String>):Response<FoodRecipe>{
        return recipeService.searchRecipes(queries)
    }
}