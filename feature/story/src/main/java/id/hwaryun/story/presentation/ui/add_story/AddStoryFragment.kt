package id.hwaryun.story.presentation.ui.add_story

import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.hwaryun.core.base.BaseFragment
import id.hwaryun.core.exception.FieldErrorException
import id.hwaryun.shared.utils.subscribe
import id.hwaryun.story.R
import id.hwaryun.story.constants.PostFieldConstants
import id.hwaryun.story.databinding.FragmentAddStoryBinding
import id.hwaryun.story.presentation.bottomsheet.ImageBottomSheet
import id.hwaryun.story.presentation.ui.StoryViewModel
import id.hwaryun.story.utils.Extensions.clear
import id.hwaryun.story.utils.Extensions.loadImage
import id.hwaryun.story.utils.checkPermissions
import java.io.File

@AndroidEntryPoint
class AddStoryFragment :
    BaseFragment<FragmentAddStoryBinding, StoryViewModel>(FragmentAddStoryBinding::inflate) {

    override val viewModel: StoryViewModel by viewModels()

    private var getFile: File? = null

    override fun initView() {
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnPost.setOnClickListener {
            val description = binding.etDescription.text.toString().trim()

            if (getFile == null) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.image_required),
                    Toast.LENGTH_SHORT
                )
                    .show()
                return@setOnClickListener
            }

            val photo = getFile as File
            viewModel.postStory(description, photo)
        }

        binding.ivPhoto.setOnClickListener {
            checkPermissions(
                requireActivity(),
                requireContext(),
                ::initBottomSheet
            )
        }
    }

    override fun observeData() {
        viewModel.postResult.observe(viewLifecycleOwner) { postResult ->
            postResult.subscribe(
                doOnLoading = {
                    showLoading(true)
                },
                doOnSuccess = {
                    showLoading(false)
                    resetField()
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.story_success),
                        Toast.LENGTH_LONG
                    ).show()

                    parentFragmentManager.popBackStack()
                },
                doOnError = {
                    showLoading(false)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.story_failed),
                        Toast.LENGTH_LONG
                    ).show()

                    if (postResult.exception is FieldErrorException) {
                        handleFieldError(postResult.exception as FieldErrorException)
                    } else {
                        postResult.exception?.let { e -> showError(true, e) }
                    }
                }
            )
        }
    }

    private fun initBottomSheet() {
        val bottomSheet = ImageBottomSheet()
        bottomSheet.show(childFragmentManager, ImageBottomSheet.TAG)
        bottomSheet.bottomSheetCallback = object : ImageBottomSheet.BottomSheetCallback {
            override fun onSelectImage(bitmap: Bitmap, file: File) {
                binding.ivPhoto.loadImage(bitmap)
                getFile = file
                bottomSheet.dismiss()
            }

            override fun onSelectImage(uri: Uri, file: File) {
                binding.ivPhoto.loadImage(uri)
                getFile = file
                bottomSheet.dismiss()
            }
        }
    }

    private fun handleFieldError(exception: FieldErrorException) {
        exception.errorFields.forEach { errorField ->
            if (errorField.first == PostFieldConstants.DESCRIPTION_FIELD) {
                binding.etDescription.error = getString(errorField.second)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun resetField() {
        binding.etDescription.text?.clear()
        binding.ivPhoto.clear()
    }
}