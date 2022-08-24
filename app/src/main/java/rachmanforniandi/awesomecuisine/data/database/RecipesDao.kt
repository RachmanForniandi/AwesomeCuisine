package rachmanforniandi.awesomecuisine.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import rachmanforniandi.awesomecuisine.data.database.entities.FavoritesEntity
import rachmanforniandi.awesomecuisine.data.database.entities.RecipesEntity

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes():Flow<List<RecipesEntity>>

    @Query("SELECT * FROM favorite_recipes_table ORDER BY id ASC")
    fun readFavoriteRecipes():Flow<List<FavoritesEntity>>

    @Delete
    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity)

    @Query("DELETE FROM favorite_recipes_table")
    suspend fun deleteAllFavoriteRecipe()
}