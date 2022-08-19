package rachmanforniandi.awesomecuisine.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.view.*
import kotlinx.coroutines.launch
import rachmanforniandi.awesomecuisine.R
import rachmanforniandi.awesomecuisine.adapters.RecipesAdapter
import rachmanforniandi.awesomecuisine.databinding.FragmentRecipesBinding
import rachmanforniandi.awesomecuisine.util.NetworkResult
import rachmanforniandi.awesomecuisine.util.observeOnce
import rachmanforniandi.awesomecuisine.viewModel.MainViewModel
import rachmanforniandi.awesomecuisine.viewModel.RecipesViewModel

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private var _binding:FragmentRecipesBinding?= null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel
    private val adapter by lazy { RecipesAdapter() }
    //private lateinit var mView:View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //mView =inflater.inflate(R.layout.fragment_recipes, container, false)
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner= this
        binding.mainViewModel = mainViewModel

        setupRecyclerviewRecipesData()
        //requestApiData()
        readDatabase()

        //Log.e("testApi",""+requestApiData())
        return binding.root
    }

    private fun readDatabase(){
        lifecycleScope.launch {
            mainViewModel.readRecipesLocal.observeOnce(viewLifecycleOwner,{ database->
                if (database.isNotEmpty()){
                    Log.d("recipesFragment","readDatabase called!")
                    adapter.setData(database[0].foodRecipe)
                    hideShimmerEffect()
                }else{
                    requestApiData()
                }
            })
        }

    }

    private fun requestApiData(){
        Log.d("recipesFragment","request api data called!")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner,{
            response->
            when(response){
                is NetworkResult.Success->{
                    hideShimmerEffect()
                    response.data?.let { adapter.setData(it) }
                }
                is NetworkResult.Error->{
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(requireContext(),
                        response.message.toString()
                        ,Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading->{
                    showShimmerEffect()
                }
            }
        })
    }

    private fun loadDataFromCache(){
        lifecycleScope.launch {
            mainViewModel.readRecipesLocal.observe(viewLifecycleOwner,{ database ->
                if (database.isNotEmpty()){
                    adapter.setData(database[0].foodRecipe)
                }
            })
        }
    }


    private fun setupRecyclerviewRecipesData(){
        binding.listItemRecipe.adapter = adapter
        binding.listItemRecipe.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect(){
        binding.listItemRecipe.showShimmer()
    }

    private fun hideShimmerEffect(){
        binding.listItemRecipe.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding =null
    }

}