package rachmanforniandi.awesomecuisine.ui.fragments


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import rachmanforniandi.awesomecuisine.R
import rachmanforniandi.awesomecuisine.adapters.RecipesAdapter
import rachmanforniandi.awesomecuisine.databinding.FragmentRecipesBinding
import rachmanforniandi.awesomecuisine.util.NetworkListener
import rachmanforniandi.awesomecuisine.util.NetworkResult
import rachmanforniandi.awesomecuisine.util.observeOnce
import rachmanforniandi.awesomecuisine.viewModel.MainViewModel
import rachmanforniandi.awesomecuisine.viewModel.RecipesViewModel
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipesFragment : Fragment(),SearchView.OnQueryTextListener {

    private var dataRequested = false
    private val args by navArgs<RecipesFragmentArgs>()

    private var _binding:FragmentRecipesBinding?= null
    private val binding get() = _binding!!


    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel
    private val adapter by lazy { RecipesAdapter() }
    private lateinit var networkListener: NetworkListener


    override fun onResume() {
        super.onResume()
        if (mainViewModel.recyclerViewState != null) {
            binding.listItemRecipe.layoutManager?.onRestoreInstanceState(mainViewModel.recyclerViewState)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        recipesViewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //mView =inflater.inflate(R.layout.fragment_recipes, container, false)
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner= this
        binding.mainViewModel = mainViewModel

        //setHasOptionsMenu(true)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.recipes_menu, menu)
                val search = menu.findItem(R.id.menu_search)
                val searchView = search.actionView as? SearchView
                searchView?.isSubmitButtonEnabled = true
                searchView?.setOnQueryTextListener(this@RecipesFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        setupRecyclerviewRecipesData()
        //requestApiData()
        //readDatabase()

        recipesViewModel.readBackOnline.observe(viewLifecycleOwner) {
            recipesViewModel.backOnline = it
        }

        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                networkListener = NetworkListener()
                networkListener.checkNetworkAvailability(requireActivity())
                    .collect { status->
                        Log.d("NetworkListener",status.toString())
                        recipesViewModel.networkStatus = status
                        recipesViewModel.showNetworkStatus()
                        readDatabase()
                    }
            }
        }

        binding.recipesFab.setOnClickListener {
            if (recipesViewModel.networkStatus){
                findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheetFragment)
            }else{
                recipesViewModel.showNetworkStatus()
            }
        }
        //Log.e("testApi",""+requestApiData())
        return binding.root
    }
    private fun setupRecyclerviewRecipesData(){
        binding.listItemRecipe.adapter = adapter
        //binding.listItemRecipe.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }
    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
    }*/
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null){
            searchApiData(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
    private fun readDatabase(){
        lifecycleScope.launch {
            mainViewModel.readRecipesLocal.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet ||
                    database.isNotEmpty() && dataRequested) {
                    Log.d("recipesFragment", "readDatabase called!")
                    adapter.setData(database.first().foodRecipe)
                    hideShimmerEffect()
                } else {
                    if (!dataRequested){
                        requestApiData()
                        dataRequested = true
                    }

                }
            }
        }

    }

    private fun requestApiData(){
        Log.d("recipesFragment","request api data called!")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { adapter.setData(it) }
                    recipesViewModel.saveMealAndDietType()
                }

                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(), Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }
    private fun searchApiData(searchQuery:String){
        showShimmerEffect()
        mainViewModel.searchRecipes(recipesViewModel.applyForSearchQueries(searchQuery))
        mainViewModel.searchedRecipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    val foodRecipe = response.data
                    foodRecipe?.let { adapter.setData(it) }
                }

                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(), Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun loadDataFromCache(){
        lifecycleScope.launch {
            mainViewModel.readRecipesLocal.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    adapter.setData(database.first().foodRecipe)
                }
            }
        }
    }

    private fun showShimmerEffect(){
        binding.shimmerFrameLayout.startShimmer()
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.listItemRecipe.visibility = View.GONE
    }

    private fun hideShimmerEffect(){
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.listItemRecipe.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainViewModel.recyclerViewState = binding.listItemRecipe.layoutManager?.onSaveInstanceState()
        _binding =null
    }

}