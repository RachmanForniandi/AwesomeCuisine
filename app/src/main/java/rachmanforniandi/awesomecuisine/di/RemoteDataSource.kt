package rachmanforniandi.awesomecuisine.di

import rachmanforniandi.awesomecuisine.models.FoodRecipe
import rachmanforniandi.awesomecuisine.networkUtils.ApiRecipeService
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val recipeService: ApiRecipeService) {

    suspend fun getRecipesData(queries:Map<String,String>):Response<FoodRecipe>{
        return recipeService.getRecipes(queries)
    }
}