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
import rachmanforniandi.awesomecuisine.util.retrieveParcelable


class InstructionsFragment : Fragment() {
    private var _binding:FragmentInstructionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInstructionsBinding.inflate(inflater,container,false)

        val args = arguments
        val myBundle: Result? = args?.retrieveParcelable(Constants.RECIPE_RESULT_KEY)

        if (myBundle != null){
            binding.instructionsWv.webViewClient = object : WebViewClient() {}
            val webUrl:String = myBundle.sourceUrl
            binding.instructionsWv.loadUrl(webUrl)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}