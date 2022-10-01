package id.hwaryun.story.presentation.ui.list_story

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.hwaryun.core.base.BaseFragment
import id.hwaryun.shared.router.ActivityRouter
import id.hwaryun.shared.utils.subscribe
import id.hwaryun.story.R
import id.hwaryun.story.data.model.viewparam.StoryViewParam
import id.hwaryun.story.databinding.FragmentListStoryBinding
import id.hwaryun.story.presentation.adapter.LoadingStateAdapter
import id.hwaryun.story.presentation.adapter.StoryAdapter
import id.hwaryun.story.presentation.bottomsheet.LogoutBottomSheet
import id.hwaryun.story.presentation.ui.StoryViewModel
import id.hwaryun.story.presentation.ui.add_story.AddStoryFragment
import id.hwaryun.story.presentation.ui.detail_story.DetailStoryFragment
import id.hwaryun.story.presentation.ui.map_story.MapsFragment
import id.hwaryun.style.ProjectString
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListStoryFragment :
    BaseFragment<FragmentListStoryBinding, StoryViewModel>(FragmentListStoryBinding::inflate) {

    override val viewModel: StoryViewModel by viewModels()

    @Inject
    lateinit var router: ActivityRouter

    private val storyAdapter: StoryAdapter by lazy { StoryAdapter(::onStoryClicked) }

    private var isFromOtherScreen = false

    override fun initView() {
        swipeRefresh()

        binding.btnAdd.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AddStoryFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnMap.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MapsFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnLogout.setOnClickListener {
            onLogoutClicked()
        }

        binding.rvStories.adapter = storyAdapter.withLoadStateHeaderAndFooter(
            header = LoadingStateAdapter {
                storyAdapter.retry()
            },
            footer = LoadingStateAdapter {
                storyAdapter.retry()
            }
        )

        binding.rvStories.adapter = storyAdapter.apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    if (positionStart == 0 && !isFromOtherScreen) {
                        binding.rvStories.smoothScrollToPosition(0)
                    }
                }
            })

            withLoadStateHeaderAndFooter(
                header = LoadingStateAdapter {
                    storyAdapter.retry()
                },
                footer = LoadingStateAdapter {
                    storyAdapter.retry()
                }
            )

            lifecycleScope.launch {
                storyAdapter.loadStateFlow.collect {
                    showLoading((it.refresh is LoadState.Loading))
                    binding.error.root.isVisible =
                        it.source.refresh is LoadState.NotLoading && it.append.endOfPaginationReached && storyAdapter.itemCount < 1
                    if (it.refresh is LoadState.Error) {
                        showError(true, getString(ProjectString.message_error_unknown))
                    }
                }
            }
        }

        binding.rvStories.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun observeData() {
        viewModel.getStories()
        viewModel.storyResult.observe(viewLifecycleOwner) {
            it.subscribe(
                doOnSuccess = { result ->
                    result.payload?.let { stories ->
                        storyAdapter.submitData(lifecycle, stories)
                    }
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
            storyAdapter.refresh()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.swipeRefresh.isRefreshing = false
        binding.rvStories.isVisible = !isLoading
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