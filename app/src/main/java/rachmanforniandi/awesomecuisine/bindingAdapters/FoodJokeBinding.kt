package rachmanforniandi.awesomecuisine.bindingAdapters

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView
import rachmanforniandi.awesomecuisine.data.database.entities.FoodJokeEntity
import rachmanforniandi.awesomecuisine.models.FoodJoke
import rachmanforniandi.awesomecuisine.util.NetworkResult

class FoodJokeBinding {

    companion object{

        @BindingAdapter("readDataOnlineFoodJoke","readDataLocalFoodJoke", requireAll = false)
        @JvmStatic
        fun setCardAndProgressVisibility(
            view: View,
            apiResponse:NetworkResult<FoodJoke>,
            db:List<FoodJokeEntity>?
        ){
            when (apiResponse){
                is NetworkResult.Loading ->{
                    when(view){
                        is ProgressBar ->{
                            view.visibility = View.VISIBLE
                        }
                        is MaterialCardView ->{
                            view.visibility = View.INVISIBLE
                        }
                    }
                }
                is NetworkResult.Error->{
                    when(view){
                        is ProgressBar ->{
                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView->{
                            view.visibility = View.VISIBLE
                            if (db != null){
                                if (db.isEmpty()){
                                    view.visibility = View.INVISIBLE
                                }
                            }
                        }
                    }
                }

                is NetworkResult.Success->{
                    when(view){
                        is ProgressBar ->{
                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView->{
                            view.visibility = View.VISIBLE

                        }
                    }
                }
            }

        }

        @BindingAdapter("readDataOnlineFoodJoke2","readDataLocalFoodJoke2", requireAll = true)
        @JvmStatic
        fun setErrorViewsVisibility(
            view: View,
            apiResponse: NetworkResult<FoodJoke>?,
            database: List<FoodJokeEntity>?
        ){
            if(database != null){
                if(database.isEmpty()){
                    view.visibility = View.VISIBLE
                    if(view is TextView){
                        if(apiResponse != null){
                            view.text = apiResponse.message.toString()
                        }
                    }
                }
            }
            if(apiResponse is NetworkResult.Success){
                view.visibility = View.INVISIBLE
            }
        }

    }
}