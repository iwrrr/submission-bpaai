package id.hwaryun.story.presentation.ui

import android.content.Intent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.hwaryun.core.base.BaseActivity
import id.hwaryun.shared.router.ActivityRouter
import id.hwaryun.shared.utils.subscribe
import id.hwaryun.story.databinding.ActivityStoryBinding
import javax.inject.Inject

@AndroidEntryPoint
class StoryActivity :
    BaseActivity<ActivityStoryBinding, StoryViewModel>(ActivityStoryBinding::inflate) {

    override val viewModel: StoryViewModel by viewModels()

    @Inject
    lateinit var router: ActivityRouter

    override fun initView() {
        viewModel.getToken()
    }

    override fun observeData() {
        viewModel.tokenResult.observe(this) {
            it.subscribe(
                doOnSuccess = { token ->
                    if (token.payload.isNullOrEmpty()) {
                        navigateToLogin()
                    }
                }
            )
        }
    }

    private fun navigateToLogin() {
        startActivity(router.authActivity(this).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
        finish()
    }
}