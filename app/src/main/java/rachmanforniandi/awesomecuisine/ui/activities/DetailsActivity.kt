package rachmanforniandi.awesomecuisine.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import rachmanforniandi.awesomecuisine.R
import rachmanforniandi.awesomecuisine.adapters.PagerAdapter
import rachmanforniandi.awesomecuisine.databinding.ActivityDetailsBinding
import rachmanforniandi.awesomecuisine.ui.fragments.IngredientsFragment
import rachmanforniandi.awesomecuisine.ui.fragments.InstructionsFragment
import rachmanforniandi.awesomecuisine.ui.fragments.OverviewFragment

class DetailsActivity : AppCompatActivity() {
    private val args by navArgs<DetailsActivityArgs>()
    private lateinit var binding:ActivityDetailsBinding

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
        resultBundle.putParcelable("recipeBundle",args.result)

        val adapter = PagerAdapter(resultBundle,fragments,titles,supportFragmentManager)
        binding.viewPagerDetails.adapter = adapter
        binding.tabLayoutDetails.setupWithViewPager(binding.viewPagerDetails)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}