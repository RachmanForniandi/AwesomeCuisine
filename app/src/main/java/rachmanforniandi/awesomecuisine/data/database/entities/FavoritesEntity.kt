package rachmanforniandi.awesomecuisine.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import rachmanforniandi.awesomecuisine.models.Result
import rachmanforniandi.awesomecuisine.util.Constants.Companion.FAVORITE_RECIPES_TABLE

@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var result:Result
        ){
}