package rachmanforniandi.awesomecuisine.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import rachmanforniandi.awesomecuisine.R
import rachmanforniandi.awesomecuisine.adapters.FavoriteRecipesAdapter
import rachmanforniandi.awesomecuisine.databinding.FragmentFavoriteRecipesBinding
import rachmanforniandi.awesomecuisine.databinding.FragmentFoodJokeBinding
import rachmanforniandi.awesomecuisine.util.Constants.Companion.API_KEY
import rachmanforniandi.awesomecuisine.util.NetworkResult
import rachmanforniandi.awesomecuisine.viewModel.MainViewModel

@AndroidEntryPoint
class FoodJokeFragment : Fragment() {

    private val mainViewModel by viewModels<MainViewModel>()

    private var _binding: FragmentFoodJokeBinding? = null
    private val binding get() = _binding!!
    private var foodJoke = "No Food Joke"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFoodJokeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = mainViewModel

        mainViewModel.getFoodJoke(API_KEY)
        mainViewModel.foodJokeResponse.observe(viewLifecycleOwner,{ response->
            when(response){
                is NetworkResult.Success->{
                    binding.tvFoodJoke.text = response.data?.text
                }
                is NetworkResult.Error->{
                    loadDataFromCache()
                    Toast.makeText(requireActivity(),response.message.toString(),
                    Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading->{
                    Log.d("FoodJokeFragment","Loading")
                }
            }

        })
        return binding.root
    }

    private fun loadDataFromCache(){
        lifecycleScope.launch {
            mainViewModel.readFoodJokeLocal.observe(viewLifecycleOwner,{database->
                if (!database.isNullOrEmpty()){
                    binding.tvFoodJoke.text = database[0].foodJoke.text
                    foodJoke = database[0].foodJoke.text
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}