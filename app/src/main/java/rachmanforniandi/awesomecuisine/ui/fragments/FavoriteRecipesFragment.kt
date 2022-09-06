package rachmanforniandi.awesomecuisine.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import rachmanforniandi.awesomecuisine.R
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
    ): View {

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_recipes_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteAll_favorite_recipes_menu){
            mainViewModel.deleteAllFavoriteRecipesData()
            showSnackBar()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun showSnackBar(){
        Snackbar.make(
            binding.root,
            "All recipes removed",
            Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mAdapter.clearContextualActionMode()
    }

}