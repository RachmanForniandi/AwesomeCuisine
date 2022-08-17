package rachmanforniandi.awesomecuisine.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import rachmanforniandi.awesomecuisine.models.FoodRecipe
import rachmanforniandi.awesomecuisine.networkUtils.Constants
import rachmanforniandi.awesomecuisine.networkUtils.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity (var foodRecipe: FoodRecipe){

    @PrimaryKey(autoGenerate = false)
    var id:Int =0
}