package id.hwaryun.story.presentation.bottomsheet

import id.hwaryun.core.base.BaseBottomSheetFragment
import id.hwaryun.story.databinding.BottomSheetLogoutBinding

class LogoutBottomSheet :
    BaseBottomSheetFragment<BottomSheetLogoutBinding>(BottomSheetLogoutBinding::inflate) {

    var bottomSheetCallback: BottomSheetCallback? = null

    override fun initView() {
        binding.logout.setOnClickListener { bottomSheetCallback?.onLogout() }
        binding.cancel.setOnClickListener { dismiss() }
    }

    interface BottomSheetCallback {
        fun onLogout()
    }

    companion object {
        const val TAG = "DeleteBottomSheet"
    }
}