package id.hwaryun.story.utils

import androidx.recyclerview.widget.DiffUtil
import id.hwaryun.story.data.model.viewparam.StoryViewParam

class StoryDiffUtil(
    private val oldList: List<StoryViewParam>,
    private val newList: List<StoryViewParam>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> false
            oldList[oldItemPosition].description != newList[newItemPosition].description -> false
            oldList[oldItemPosition].photoUrl != newList[newItemPosition].photoUrl -> false
            oldList[oldItemPosition].createdAt != newList[newItemPosition].createdAt -> false
            oldList[oldItemPosition].lat != newList[newItemPosition].lat -> false
            oldList[oldItemPosition].long != newList[newItemPosition].long -> false
            else -> true
        }
    }
}