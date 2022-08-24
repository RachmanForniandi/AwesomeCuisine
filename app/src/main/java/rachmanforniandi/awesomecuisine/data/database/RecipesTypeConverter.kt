package rachmanforniandi.awesomecuisine.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import rachmanforniandi.awesomecuisine.models.FoodRecipe
import rachmanforniandi.awesomecuisine.models.Result

class RecipesTypeConverter {

    var gson= Gson()

    @TypeConverter
    fun fooRecipeToString(foodRecipe: FoodRecipe):String{
        return gson.toJson(foodRecipe)
    }

    @TypeConverter
    fun stringToFoodRecipe(data:String):FoodRecipe{
        val listType= object :TypeToken<FoodRecipe>(){}.type
        return gson.fromJson(data,listType)
    }

    @TypeConverter
    fun resultToString(result: Result):String{
        return gson.toJson(result)
    }

    @TypeConverter
    fun stringToString(data: String):Result{
        val listType = object : TypeToken<Result>() {}.type
        return gson.fromJson(data,listType)
    }
}