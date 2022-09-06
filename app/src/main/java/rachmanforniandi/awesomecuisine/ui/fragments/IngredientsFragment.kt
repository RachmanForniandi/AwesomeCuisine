package rachmanforniandi.awesomecuisine.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rachmanforniandi.awesomecuisine.R
import rachmanforniandi.awesomecuisine.adapters.IngredientsAdapter
import rachmanforniandi.awesomecuisine.databinding.FragmentIngredientsBinding
import rachmanforniandi.awesomecuisine.models.Result
import rachmanforniandi.awesomecuisine.util.Constants
import rachmanforniandi.awesomecuisine.util.Constants.Companion.RECIPE_RESULT_KEY


class IngredientsFragment : Fragment() {
    private lateinit var binding:FragmentIngredientsBinding
    private val adapter:IngredientsAdapter by lazy { IngredientsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentIngredientsBinding.inflate(inflater,container,false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)
        binding.listIngredients.adapter = adapter
        myBundle?.extendedIngredients?.let { adapter.setData(it) }

        return binding.root
    }


}