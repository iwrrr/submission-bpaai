package id.hwaryun.story.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.hwaryun.shared.utils.StringUtils
import id.hwaryun.story.data.model.viewparam.StoryViewParam
import id.hwaryun.story.databinding.ItemStoriesBinding
import id.hwaryun.story.utils.Extensions.loadImage

class StoryAdapter(
    private val onClick: (StoryViewParam) -> Unit
) : PagingDataAdapter<StoryViewParam, StoryAdapter.StoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
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

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryViewParam>() {
            override fun areItemsTheSame(
                oldItem: StoryViewParam,
                newItem: StoryViewParam
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: StoryViewParam,
                newItem: StoryViewParam
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}