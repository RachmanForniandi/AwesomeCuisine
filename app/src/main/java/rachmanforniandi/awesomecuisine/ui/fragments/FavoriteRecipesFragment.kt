package rachmanforniandi.awesomecuisine.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite_recipes.*
import kotlinx.android.synthetic.main.fragment_favorite_recipes.view.*
import rachmanforniandi.awesomecuisine.R
import rachmanforniandi.awesomecuisine.adapters.FavoriteRecipesAdapter
import rachmanforniandi.awesomecuisine.adapters.RecipesAdapter
import rachmanforniandi.awesomecuisine.databinding.FragmentFavoriteRecipesBinding
import rachmanforniandi.awesomecuisine.viewModel.MainViewModel
import rachmanforniandi.awesomecuisine.viewModel.RecipesViewModel

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {
    private val mAdapter by lazy { FavoriteRecipesAdapter() }
    private val mainViewModel: MainViewModel by viewModels()

    /*private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite_recipes, container, false)
        view.list_favorite_recipe.adapter = mAdapter
        mainViewModel.readFavoriteRecipes.observe(viewLifecycleOwner,{ favoritesEntity->
            mAdapter.setData(favoritesEntity)
        })

        return view
    }

}