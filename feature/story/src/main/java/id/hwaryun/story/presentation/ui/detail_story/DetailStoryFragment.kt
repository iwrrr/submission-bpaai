package id.hwaryun.story.presentation.ui.detail_story

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.hwaryun.core.base.BaseFragment
import id.hwaryun.shared.utils.StringUtils
import id.hwaryun.story.data.model.viewparam.StoryViewParam
import id.hwaryun.story.databinding.FragmentDetailStoryBinding
import id.hwaryun.story.presentation.ui.StoryViewModel
import id.hwaryun.story.utils.Extensions.loadImage

@AndroidEntryPoint
class DetailStoryFragment :
    BaseFragment<FragmentDetailStoryBinding, StoryViewModel>(FragmentDetailStoryBinding::inflate) {

    override val viewModel: StoryViewModel by viewModels()

    override fun initView() {
        binding.btnBack.setOnClickListener { parentFragmentManager.popBackStack() }
        initData()
    }

    private fun initData() {
        val story = arguments?.getParcelable<StoryViewParam>("story")
        story?.let {
            binding.apply {
                tvName.text = story.name
                tvTime.text = StringUtils.prettyTime(story.createdAt)
                tvDescription.text = story.description
                ivPhoto.loadImage(story.photoUrl)
            }
        }
    }
}