package rachmanforniandi.awesomecuisine.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import rachmanforniandi.awesomecuisine.R
import rachmanforniandi.awesomecuisine.databinding.FragmentInstructionsBinding
import rachmanforniandi.awesomecuisine.models.Result
import rachmanforniandi.awesomecuisine.util.Constants


class InstructionsFragment : Fragment() {
    private lateinit var binding:FragmentInstructionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInstructionsBinding.inflate(inflater,container,false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constants.RECIPE_RESULT_KEY)

        binding.instructionsWv.webViewClient = object : WebViewClient() {}
        val webUrl:String = myBundle!!.sourceUrl
        binding.instructionsWv.loadUrl(webUrl)


        return binding.root


    }


}