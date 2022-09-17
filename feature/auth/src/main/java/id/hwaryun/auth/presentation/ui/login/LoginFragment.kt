package id.hwaryun.auth.presentation.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import id.hwaryun.auth.R
import id.hwaryun.auth.constants.LoginFieldConstants
import id.hwaryun.auth.databinding.FragmentLoginBinding
import id.hwaryun.auth.presentation.ui.AuthViewModel
import id.hwaryun.auth.presentation.ui.register.RegisterFragment
import id.hwaryun.core.base.BaseFragment
import id.hwaryun.core.exception.FieldErrorException
import id.hwaryun.shared.router.ActivityRouter
import id.hwaryun.shared.utils.listen
import id.hwaryun.shared.utils.subscribe
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment :
    BaseFragment<FragmentLoginBinding, AuthViewModel>(FragmentLoginBinding::inflate) {

    override val viewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var router: ActivityRouter

    override fun initView() {
        playAnimation()

        binding.btnLogin.setOnClickListener {
            viewModel.loginUser(
                binding.etEmail.text?.trim().toString(),
                binding.etPassword.text?.trim().toString(),
            )
        }

        binding.tvRegister.setOnClickListener {
            val registerFragment = RegisterFragment()
            parentFragmentManager.beginTransaction().apply {
                add(R.id.fragmentContainerView, registerFragment)
                addToBackStack(null)
                commit()
            }
        }

        binding.etPassword.listen(
            beforeTextChanged = {
                binding.tlPassword.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
            }
        )
    }

    override fun observeData() {
        viewModel.loginResult.observe(this) { loginResult ->
            resetField()
            loginResult.subscribe(
                doOnLoading = {
                    showLoading(true)
                },
                doOnSuccess = {
                    showLoading(false)
                    navigateToHome()
                },
                doOnError = {
                    showLoading(false)
                    if (loginResult.exception is FieldErrorException) {
                        handleFieldError(loginResult.exception as FieldErrorException)
                    } else {
                        loginResult.exception?.let { e -> showError(true, e) }
                    }
                }
            )
        }
    }

    private fun handleFieldError(exception: FieldErrorException) {
        exception.errorFields.forEach { errorField ->
            if (errorField.first == LoginFieldConstants.EMAIL_FIELD) {
                binding.etEmail.error = getString(errorField.second)
            }
            if (errorField.first == LoginFieldConstants.PASSWORD_FIELD) {
                binding.tlPassword.endIconMode = TextInputLayout.END_ICON_NONE
                binding.etPassword.error = getString(errorField.second)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun resetField() {
        binding.tlEmail.isErrorEnabled = false
        binding.tlPassword.isErrorEnabled = false
        binding.tlPassword.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
    }

    private fun navigateToHome() {
        startActivity(router.storyActivity(requireContext()).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
        requireActivity().finish()
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.tvAppName, View.ALPHA, 1f).setDuration(300)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.tlEmail, View.ALPHA, 1f).setDuration(300)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.tlPassword, View.ALPHA, 1f).setDuration(300)
        val login = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(300)
        val notHaveAnAccount =
            ObjectAnimator.ofFloat(binding.tvNotHaveAnAccount, View.ALPHA, 1f).setDuration(300)
        val register = ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA, 1f).setDuration(300)

        AnimatorSet().apply {
            playSequentially(
                title,
                emailEditTextLayout,
                passwordEditTextLayout,
                login,
                notHaveAnAccount,
                register
            )
            startDelay = 500
        }.start()
    }
}