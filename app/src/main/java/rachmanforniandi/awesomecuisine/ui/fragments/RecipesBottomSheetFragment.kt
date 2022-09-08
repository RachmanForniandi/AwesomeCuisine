package rachmanforniandi.awesomecuisine.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import rachmanforniandi.awesomecuisine.databinding.FragmentRecipesBottomSheetBinding
import rachmanforniandi.awesomecuisine.util.Constants.Companion.DEFAULT_DIET_TYPE
import rachmanforniandi.awesomecuisine.util.Constants.Companion.DEFAULT_MEAL_TYPE
import rachmanforniandi.awesomecuisine.viewModel.RecipesViewModel
import java.lang.Exception
import java.util.*


class RecipesBottomSheetFragment : BottomSheetDialogFragment() {

    private var binding: FragmentRecipesBottomSheetBinding? = null
    private val bind get() = binding!!
    private lateinit var recipesViewModel: RecipesViewModel

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId=0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRecipesBottomSheetBinding.inflate(inflater,container,false)
        //return inflater.inflate(R.layout.fragment_recipes_bottom_sheet, container, false)

        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner,{
            value->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType
            updateChip(value.selectedMealTypeId,bind.mealTypeChipGroup)
            updateChip(value.selectedDietTypeId,bind.dietTypeChipGroup)
        })

        bind.mealTypeChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
            mealTypeChip = selectedMealType
            mealTypeChipId = selectedChipId
        }

        bind.dietTypeChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip2 = group.findViewById<Chip>(selectedChipId)
            val selectedDietType = chip2.text.toString().lowercase(Locale.ROOT)
            dietTypeChip = selectedDietType
            dietTypeChipId = selectedChipId
        }

        bind.btnApply.setOnClickListener {
            recipesViewModel.saveMealAndDietTypeTemp(
                mealTypeChip,mealTypeChipId,dietTypeChip,dietTypeChipId
            )
            val action = RecipesBottomSheetFragmentDirections.actionRecipesBottomSheetFragmentToRecipesFragment(true)
            findNavController().navigate(action)
        }

        return bind.root
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0){
            try {
                val targetView =chipGroup.findViewById<Chip>(chipId)
                targetView.isChecked = true
                chipGroup.requestChildFocus(targetView,targetView)
            }catch (e:Exception){
                Log.d("RecipeBottomSheet",e.message.toString())
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }




}