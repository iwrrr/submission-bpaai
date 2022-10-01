package id.hwaryun.storygram.auth.presentation.ui

import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.hwaryun.core.base.BaseActivity
import id.hwaryun.storygram.auth.databinding.ActivityAuthBinding

@AndroidEntryPoint
class AuthActivity :
    BaseActivity<ActivityAuthBinding, AuthViewModel>(ActivityAuthBinding::inflate) {

    override val viewModel: AuthViewModel by viewModels()

    override fun initView() = Unit
}