package rachmanforniandi.awesomecuisine.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import coil.load
import kotlinx.android.synthetic.main.recipes_row_layout.*
import org.jsoup.Jsoup
import rachmanforniandi.awesomecuisine.R
import rachmanforniandi.awesomecuisine.bindingAdapters.RecipesRowBinding
import rachmanforniandi.awesomecuisine.databinding.FragmentOverviewBinding
import rachmanforniandi.awesomecuisine.databinding.RecipesRowLayoutBinding
import rachmanforniandi.awesomecuisine.models.Result
import rachmanforniandi.awesomecuisine.util.Constants.Companion.RECIPE_RESULT_KEY


class OverviewFragment : Fragment() {

    private lateinit var binding: FragmentOverviewBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOverviewBinding.inflate(inflater,container,false)
        //return inflater.inflate(R.layout.fragment_overview, container, false)

        val args = arguments
        val myBundle:Result = args?.getParcelable<Result>(RECIPE_RESULT_KEY) as Result

        binding.imgMainOverview.load(myBundle.image)
        binding.tvTitleRecipe.text = myBundle.title
        binding.tvLikes.text = myBundle.aggregateLikes.toString()
        binding.tvTime.text = myBundle.readyInMinutes.toString()
        //binding.tvSummary.text = myBundle?.summary
        /*myBundle?.summary.let {
            val summary = Jsoup.parse(it).text()
            binding.tvSummary.text = summary
        }*/
        RecipesRowBinding.parseHtml(binding.tvSummary,myBundle.summary)

        updateColors(myBundle.vegetarian, binding.tvVegetarian, binding.imgVegetarian)
        updateColors(myBundle.vegan, binding.tvVegan, binding.imgVegan)
        updateColors(myBundle.cheap, binding.tvCheap, binding.imgCheap)
        updateColors(myBundle.dairyFree, binding.tvDairyFree, binding.imgDairyFree)
        updateColors(myBundle.glutenFree, binding.tvGlutenFree, binding.imgGlutenFree)
        updateColors(myBundle.veryHealthy, binding.tvHealthy, binding.imgHealthy)


        if (myBundle.vegetarian == true){
            binding.imgVegetarian.setColorFilter(ContextCompat.getColor(requireActivity(),R.color.green))
            binding.tvVegetarian.setTextColor(ContextCompat.getColor(requireActivity(),R.color.green))
        }

        /*if (myBundle?.vegan == true){
            binding.imgVegan.setColorFilter(ContextCompat.getColor(requireActivity(),R.color.green))
            binding.tvVegan.setTextColor(ContextCompat.getColor(requireActivity(),R.color.green))
        }

        if (myBundle?.glutenFree == true){
            binding.imgGlutenFree.setColorFilter(ContextCompat.getColor(requireActivity(),R.color.green))
            binding.tvGlutenFree.setTextColor(ContextCompat.getColor(requireActivity(),R.color.green))
        }

        if (myBundle?.dairyFree == true){
            binding.imgDairyFree.setColorFilter(ContextCompat.getColor(requireActivity(),R.color.green))
            binding.tvDairyFree.setTextColor(ContextCompat.getColor(requireActivity(),R.color.green))
        }

        if (myBundle?.veryHealthy == true){
            binding.imgHealthy.setColorFilter(ContextCompat.getColor(requireActivity(),R.color.green))
            binding.tvHealthy.setTextColor(ContextCompat.getColor(requireActivity(),R.color.green))
        }

        if (myBundle?.cheap ==true){
            binding.imgCheap.setColorFilter(ContextCompat.getColor(requireActivity(),R.color.green))
            binding.tvCheap.setTextColor(ContextCompat.getColor(requireActivity(),R.color.green))
        }
*/
        return binding.root
    }
    private fun updateColors(stateIsOn:Boolean,textView: TextView,imageView: ImageView){
        if (stateIsOn){
            imageView.setColorFilter(ContextCompat.getColor(requireActivity(),R.color.green))
            textView.setTextColor(ContextCompat.getColor(requireActivity(),R.color.green))

        }
    }
}