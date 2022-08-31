package rachmanforniandi.awesomecuisine.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import rachmanforniandi.awesomecuisine.data.database.entities.FavoritesEntity
import rachmanforniandi.awesomecuisine.data.database.entities.RecipesEntity
import rachmanforniandi.awesomecuisine.models.FoodRecipe
import rachmanforniandi.awesomecuisine.repo.Repository
import rachmanforniandi.awesomecuisine.util.NetworkResult
import retrofit2.Response
import java.lang.Exception


class MainViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
):AndroidViewModel(application) {


    /** Room Db*/
    val readRecipesLocal: LiveData<List<RecipesEntity>> = repository.local.readDatabase().asLiveData()
    val readFavoriteRecipes: LiveData<List<FavoritesEntity>> = repository.local.readFavoriteRecipes().asLiveData()

    private fun insertRecipeLocal(recipesEntity: RecipesEntity)=
        viewModelScope.launch (Dispatchers.IO){
            repository.local.insertRecipes(recipesEntity)
        }

    fun insertFavoriteRecipesData(favoritesEntity: FavoritesEntity)=
        viewModelScope.launch (Dispatchers.IO){
            repository.local.insertFavoriteRecipes(favoritesEntity)
        }

    fun deleteFavoriteRecipesData(favoritesEntity: FavoritesEntity)=
        viewModelScope.launch (Dispatchers.IO){
            repository.local.deleteFavoriteRecipes(favoritesEntity)
        }

    fun deleteAllFavoriteRecipesData()=
        viewModelScope.launch (Dispatchers.IO){
            repository.local.deleteAllFavoriteRecipes()
        }


    /** Retrofit */
    var recipesResponse :MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var searchedRecipesResponse :MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()

    fun getRecipes(queries: Map<String,String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    fun searchRecipes(searchQuery: Map<String,String>) = viewModelScope.launch {
       searchRecipesSafeCall(searchQuery)
    }



    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()){
            try {
                val response = repository.remote.getRecipesData(queries)
                recipesResponse.value = handledFoodRecipesResponse(response)

                val foodRecipe = recipesResponse.value?.data
                if (foodRecipe != null){
                    offlineCacheRecipes(foodRecipe)
                }

            }catch (e:Exception){
                recipesResponse.value =NetworkResult.Error("Recipes not found.")
            }
        }else{
            recipesResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }
    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        searchedRecipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()){
            try {
                val response = repository.remote.searchRecipesData(searchQuery)
                searchedRecipesResponse.value = handledFoodRecipesResponse(response)
            }catch (e:Exception){
                searchedRecipesResponse.value =NetworkResult.Error("Recipes not found.")
            }
        }else{
            searchedRecipesResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun offlineCacheRecipes(recipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(recipe)
        insertRecipeLocal(recipesEntity)
    }

    private fun handledFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe> {
        when{
            response.message().toString().contains("timeout")->{
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 ->{
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()?.results.isNullOrEmpty() ->{
                val recipesData = response.body()
                return NetworkResult.Success(recipesData)
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                return NetworkResult.Success(foodRecipes)
            }
            else->{
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection():Boolean{
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        )as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities= connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when{
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->true
            else -> false
        }
    }
}