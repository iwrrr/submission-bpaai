package id.hwaryun.splash

import android.annotation.SuppressLint
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.hwaryun.core.base.BaseActivity
import id.hwaryun.shared.router.ActivityRouter
import id.hwaryun.shared.utils.subscribe
import id.hwaryun.splash.databinding.ActivitySplashBinding
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity :
    BaseActivity<ActivitySplashBinding, SplashViewModel>(ActivitySplashBinding::inflate) {

    override val viewModel: SplashViewModel by viewModels()

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
                        startActivity(router.authActivity(this))
                        finish()
                    } else {
                        startActivity(router.storyActivity(this))
                        finish()
                    }
                },
                doOnError = {}
            )
        }
    }
}