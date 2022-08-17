package rachmanforniandi.awesomecuisine.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import rachmanforniandi.awesomecuisine.models.FoodRecipe
import rachmanforniandi.awesomecuisine.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity (var foodRecipe: FoodRecipe){

    @PrimaryKey(autoGenerate = false)
    var id:Int =0
}