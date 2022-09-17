package id.hwaryun.auth.presentation.ui

import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.hwaryun.auth.databinding.ActivityAuthBinding
import id.hwaryun.core.base.BaseActivity

@AndroidEntryPoint
class AuthActivity :
    BaseActivity<ActivityAuthBinding, AuthViewModel>(ActivityAuthBinding::inflate) {

    override val viewModel: AuthViewModel by viewModels()

    override fun initView() = Unit
}