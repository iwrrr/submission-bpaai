package id.hwaryun.auth.presentation.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import id.hwaryun.auth.constants.RegisterFieldConstants
import id.hwaryun.auth.databinding.FragmentRegisterBinding
import id.hwaryun.auth.presentation.ui.AuthViewModel
import id.hwaryun.core.base.BaseFragment
import id.hwaryun.core.exception.FieldErrorException
import id.hwaryun.shared.router.ActivityRouter
import id.hwaryun.shared.utils.listen
import id.hwaryun.shared.utils.subscribe
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment :
    BaseFragment<FragmentRegisterBinding, AuthViewModel>(FragmentRegisterBinding::inflate) {

    override val viewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var router: ActivityRouter

    override fun initView() {
        playAnimation()

        binding.btnRegister.setOnClickListener {
            viewModel.registerUser(
                binding.etName.text?.trim().toString(),
                binding.etEmail.text?.trim().toString(),
                binding.etPassword.text?.trim().toString(),
            )
        }

        binding.tvLogin.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.etPassword.listen(
            beforeTextChanged = {
                binding.tlPassword.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
            }
        )
    }

    override fun observeData() {
        viewModel.registerResult.observe(this) { registerResult ->
            resetField()
            registerResult.subscribe(
                doOnLoading = {
                    showLoading(true)
                },
                doOnSuccess = {
                    showLoading(false)
                    navigateToHome()
                },
                doOnError = {
                    showLoading(false)
                    if (registerResult.exception is FieldErrorException) {
                        handleFieldError(registerResult.exception as FieldErrorException)
                    } else {
                        registerResult.exception?.let { e -> showError(true, e) }
                    }
                }
            )
        }
    }

    private fun handleFieldError(exception: FieldErrorException) {
        exception.errorFields.forEach { errorField ->
            if (errorField.first == RegisterFieldConstants.NAME_FIELD) {
                binding.tlName.error = getString(errorField.second)
            }
            if (errorField.first == RegisterFieldConstants.EMAIL_FIELD) {
                binding.tlEmail.error = getString(errorField.second)
            }
            if (errorField.first == RegisterFieldConstants.PASSWORD_FIELD) {
                binding.tlPassword.endIconMode = TextInputLayout.END_ICON_NONE
                binding.tlPassword.error = getString(errorField.second)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun resetField() {
        binding.tlName.isErrorEnabled = false
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
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(binding.tlName, View.ALPHA, 1f).setDuration(300)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.tlEmail, View.ALPHA, 1f).setDuration(300)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.tlPassword, View.ALPHA, 1f).setDuration(300)
        val register = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(300)
        val haveAnAccount =
            ObjectAnimator.ofFloat(binding.tvHaveAnAccount, View.ALPHA, 1f).setDuration(300)
        val login = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA, 1f).setDuration(300)

        AnimatorSet().apply {
            playSequentially(
                title,
                nameEditTextLayout,
                emailEditTextLayout,
                passwordEditTextLayout,
                register,
                haveAnAccount,
                login
            )
            startDelay = 500
        }.start()
    }
}