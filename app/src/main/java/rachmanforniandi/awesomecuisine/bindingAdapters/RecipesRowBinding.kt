package rachmanforniandi.awesomecuisine.bindingAdapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import org.jsoup.Jsoup
import rachmanforniandi.awesomecuisine.R
import rachmanforniandi.awesomecuisine.models.Result
import rachmanforniandi.awesomecuisine.ui.fragments.RecipesFragmentDirections
import java.lang.Exception

class RecipesRowBinding {

    companion object{

        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(recipeRowLayout:ConstraintLayout,result: Result){
            Log.d("onRecipeClickListener","CALLED")
            recipeRowLayout.setOnClickListener {
                try {
                    val action = RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(result)
                    recipeRowLayout.findNavController().navigate(action)
                }catch (e:Exception){
                    Log.d("onRecipeClickListener",e.toString())
                }
            }
        }

        @BindingAdapter("loadImageUrl")
        @JvmStatic
        fun loadImageUrl(imageView: ImageView,imgUrl:String){
            imageView.load(imgUrl){
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }

        /*@BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView,likes:Int){
            textView.text = likes.toString()
        }

        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic
        fun setNumberOfMinutes(textView: TextView,minutes:Int){
            textView.text = minutes.toString()
        }
*/
        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view:View,vegan:Boolean){
            if (vegan){
                when(view){
                    is TextView->{
                        view.setTextColor(
                            ContextCompat.getColor(view.context, R.color.green))
                    }
                    is ImageView->{
                        view.setColorFilter(ContextCompat.getColor(view.context, R.color.green))
                    }
                }
            }
        }

        @BindingAdapter("parseHtml")
        @JvmStatic
        fun parseHtml(txt:TextView,description:String?){
            if (description !=null){
                val desc = Jsoup.parse(description).text()
                txt.text = desc
            }
        }
    }
}