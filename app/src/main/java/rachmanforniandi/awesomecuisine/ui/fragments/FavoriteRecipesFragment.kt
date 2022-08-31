package rachmanforniandi.awesomecuisine.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import rachmanforniandi.awesomecuisine.adapters.FavoriteRecipesAdapter
import rachmanforniandi.awesomecuisine.databinding.FragmentFavoriteRecipesBinding
import rachmanforniandi.awesomecuisine.viewModel.MainViewModel

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private val mAdapter by lazy { FavoriteRecipesAdapter(requireActivity(),mainViewModel) }

    private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavoriteRecipesBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.mAdapter = mAdapter
        setupRecyclerFav(binding.listFavoriteRecipe)
        /*mainViewModel.readFavoriteRecipes.observe(viewLifecycleOwner,{ favoritesEntity->
            mAdapter.setData(favoritesEntity)
        })*/

        return binding.root
    }

    private fun setupRecyclerFav(listFavorite: RecyclerView) {
        listFavorite.adapter = mAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mAdapter.clearContextualActionMode()
    }

}