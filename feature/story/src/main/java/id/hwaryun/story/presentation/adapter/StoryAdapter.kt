package id.hwaryun.story.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.hwaryun.shared.utils.StringUtils
import id.hwaryun.story.data.model.viewparam.StoryViewParam
import id.hwaryun.story.databinding.ItemStoriesBinding
import id.hwaryun.story.utils.Extensions.loadImage
import id.hwaryun.story.utils.StoryDiffUtil

class StoryAdapter(
    private val onClick: (StoryViewParam) -> Unit
) : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    private var oldList = arrayListOf<StoryViewParam>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(oldList[position])
    }

    override fun getItemCount(): Int = oldList.size

    fun submitList(newList: List<StoryViewParam>) {
        val diffUtil = StoryDiffUtil(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldList.clear()
        oldList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class StoryViewHolder(private val binding: ItemStoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(story: StoryViewParam) {
            with(binding) {
                tvName.text = story.name
                tvTime.text = StringUtils.prettyTime(story.createdAt)
                tvDescription.text = story.description
                ivPhoto.loadImage(story.photoUrl)

                itemView.setOnClickListener { onClick(story) }
            }
        }
    }
}