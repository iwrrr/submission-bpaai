package id.hwaryun.story.presentation.ui.list_story

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.hwaryun.core.base.BaseFragment
import id.hwaryun.shared.router.ActivityRouter
import id.hwaryun.shared.utils.subscribe
import id.hwaryun.story.R
import id.hwaryun.story.data.model.viewparam.StoryViewParam
import id.hwaryun.story.databinding.FragmentListStoryBinding
import id.hwaryun.story.presentation.adapter.StoryAdapter
import id.hwaryun.story.presentation.bottomsheet.LogoutBottomSheet
import id.hwaryun.story.presentation.ui.StoryViewModel
import id.hwaryun.story.presentation.ui.add_story.AddStoryFragment
import id.hwaryun.story.presentation.ui.detail_story.DetailStoryFragment
import javax.inject.Inject

@AndroidEntryPoint
class ListStoryFragment :
    BaseFragment<FragmentListStoryBinding, StoryViewModel>(FragmentListStoryBinding::inflate) {

    override val viewModel: StoryViewModel by viewModels()

    @Inject
    lateinit var router: ActivityRouter

    private val storyAdapter: StoryAdapter by lazy { StoryAdapter(::onStoryClicked) }

    override fun initView() {
        swipeRefresh()

        binding.btnAdd.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AddStoryFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnLogout.setOnClickListener {
            onLogoutClicked()
        }
    }

    override fun observeData() {
        viewModel.getStories()
        viewModel.storyResult.observe(viewLifecycleOwner) {
            it.subscribe(
                doOnLoading = {
                    showLoading(true)
                },
                doOnSuccess = { result ->
                    showLoading(false)
                    binding.rvStories.adapter = storyAdapter
                    binding.rvStories.layoutManager = LinearLayoutManager(requireContext())
                    result.payload?.let { stories ->
                        storyAdapter.submitList(stories)
                    }
                },
                doOnEmpty = {
                    showLoading(false)
                    showEmpty(true)
                },
                doOnError = { error ->
                    showLoading(false)
                    showError(true, error.message)
                }
            )
        }
    }

    private fun observeLogout() {
        viewModel.clearToken()
        viewModel.logoutResult.observe(viewLifecycleOwner) {
            it.subscribe(
                doOnSuccess = {
                    startActivity(router.authActivity(requireContext()))
                    requireActivity().finish()
                },
                doOnError = { error ->
                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun swipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            observeData()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.swipeRefresh.isRefreshing = false
        binding.rvStories.isVisible = !isLoading
    }

    private fun showEmpty(isEmpty: Boolean) {
        binding.empty.root.isVisible = isEmpty
        binding.rvStories.isVisible = !isEmpty
    }

    private fun showError(isError: Boolean, message: String?) {
        binding.error.root.isVisible = isError
        binding.error.tvError.text = message
        binding.rvStories.isVisible = !isError
    }

    private fun onStoryClicked(story: StoryViewParam) {
        val detailStoryFragment = DetailStoryFragment()
        val bundle = Bundle().apply {
            putParcelable("story", story)
        }

        detailStoryFragment.arguments = bundle
        parentFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, detailStoryFragment, null)
            addToBackStack(null)
            commit()
        }
    }

    private fun onLogoutClicked() {
        val deleteBottomSheet = LogoutBottomSheet()
        deleteBottomSheet.show(childFragmentManager, LogoutBottomSheet.TAG)
        deleteBottomSheet.bottomSheetCallback = object : LogoutBottomSheet.BottomSheetCallback {
            override fun onLogout() {
                observeLogout()
            }
        }
    }
}