package id.hwaryun.core.utils

import android.content.Context
import id.hwaryun.core.exception.ApiErrorException
import id.hwaryun.core.exception.NoInternetConnectionException
import id.hwaryun.style.ProjectString

fun Context.getErrorMessage(exception: Exception): String {
    return when (exception) {
        is NoInternetConnectionException -> getString(ProjectString.message_error_no_internet)
        is ApiErrorException -> exception.message.orEmpty()
        else -> getString(ProjectString.message_error_unknown)
    }
}