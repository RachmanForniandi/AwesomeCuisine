package rachmanforniandi.awesomecuisine.data

import kotlinx.coroutines.flow.Flow
import rachmanforniandi.awesomecuisine.data.database.RecipesDao
import rachmanforniandi.awesomecuisine.data.database.entities.RecipesEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {
    fun readDatase():Flow<List<RecipesEntity>>{
        return recipesDao.readRecipes()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity){
        recipesDao.insertRecipes(recipesEntity)
    }
}