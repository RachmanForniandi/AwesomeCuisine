package rachmanforniandi.awesomecuisine.util
import androidx.recyclerview.widget.DiffUtil
import rachmanforniandi.awesomecuisine.models.Result

class RecipesDiffUtil (private val oldList: List<Result>,
private val newList:List<Result>):DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        TODO("Not yet implemented")
    }

    override fun getNewListSize(): Int {
        TODO("Not yet implemented")
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("Not yet implemented")
    }
}