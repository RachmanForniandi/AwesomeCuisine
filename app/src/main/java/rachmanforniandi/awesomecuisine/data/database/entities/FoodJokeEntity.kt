package rachmanforniandi.awesomecuisine.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import rachmanforniandi.awesomecuisine.models.FoodJoke
import rachmanforniandi.awesomecuisine.util.Constants.Companion.FOOD_JOKE_TABLE

@Entity(tableName = FOOD_JOKE_TABLE)
class FoodJokeEntity (
    @Embedded
    var foodJoke:FoodJoke
    ){
    @PrimaryKey(autoGenerate = false)
    var id:Int= 0
}
