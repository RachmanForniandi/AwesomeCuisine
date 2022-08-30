package rachmanforniandi.awesomecuisine.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*
import rachmanforniandi.awesomecuisine.R
import rachmanforniandi.awesomecuisine.adapters.PagerAdapter
import rachmanforniandi.awesomecuisine.data.database.entities.FavoritesEntity
import rachmanforniandi.awesomecuisine.databinding.ActivityDetailsBinding
import rachmanforniandi.awesomecuisine.ui.fragments.IngredientsFragment
import rachmanforniandi.awesomecuisine.ui.fragments.InstructionsFragment
import rachmanforniandi.awesomecuisine.ui.fragments.OverviewFragment
import rachmanforniandi.awesomecuisine.util.Constants.Companion.RECIPE_RESULT_KEY
import rachmanforniandi.awesomecuisine.viewModel.MainViewModel

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel:MainViewModel by viewModels()
    private lateinit var binding:ActivityDetailsBinding

    private var recipeSaved = false
    private var savedRecipeId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_details)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarDetails)
        binding.toolbarDetails.setTitleTextColor(ContextCompat.getColor(this,R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_RESULT_KEY,args.result)

        val adapter = PagerAdapter(resultBundle,fragments,titles,supportFragmentManager)
        binding.viewPagerDetails.adapter = adapter
        binding.tabLayoutDetails.setupWithViewPager(binding.viewPagerDetails)
    }

    override fun onCreateOptionsMenu(menu: Menu?):Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.save_to_favorites_menu)
        menuItem?.let { checkedSavedRecipes(it) }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }else if(item.itemId == R.id.save_to_favorites_menu && !recipeSaved){
            saveToFavorites(item)
        }else if(item.itemId == R.id.save_to_favorites_menu && recipeSaved){
            removeFromFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkedSavedRecipes(item: MenuItem) {
        mainViewModel.readFavoriteRecipes.observe(this,{ favoritesEntity->
            try {
                for (savedRecipe in favoritesEntity){
                    if (savedRecipe.result.recipeId == args.result.recipeId){
                        changeMenuItemColor(item,R.color.yellow)
                        savedRecipeId = savedRecipe.id
                        recipeSaved =true
                    }else{
                        changeMenuItemColor(item,R.color.white)
                    }
                }
            }catch (e:Exception){
                Log.d("DetailActivity",e.message.toString())
            }
        })
    }
    private fun saveToFavorites(item: MenuItem) {
        val favEntity = FavoritesEntity(0,
            args.result)
        mainViewModel.insertFavoriteRecipesData(favEntity)
        changeMenuItemColor(item,R.color.yellow)
        showSnackBar("Recipe saved")
        recipeSaved = true
    }

    private fun removeFromFavorites(item:MenuItem){
        val favEntity = FavoritesEntity(savedRecipeId,args.result)
        mainViewModel.deleteFavoriteRecipesData(favEntity)
        changeMenuItemColor(item,R.color.white)
        showSnackBar("Removed from favorites")
        recipeSaved = false
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(detailsLayout,msg,Snackbar.LENGTH_SHORT)
            .setAction("Okay"){}
            .show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this,color))
    }


}