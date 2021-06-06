package rachmanforniandi.awesomecuisine.networkUtils

import rachmanforniandi.awesomecuisine.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiRecipeService {
    @GET("/recipes/complexSearch")
    fun getRecipes(
        @QueryMap queries:Map<String,String>
    ):Response<FoodRecipe>
}