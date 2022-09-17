package id.hwaryun.shared.utils

import android.text.TextUtils
import android.text.format.DateUtils
import android.util.Patterns
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object StringUtils {

    fun isEmailValid(input: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(input)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(input).matches()
        }
    }

    fun prettyTime(date: String): String {
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("GMT")
            val time: Long = sdf.parse(date)?.time ?: 0
            val now = System.currentTimeMillis()
            val ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)
            ago.toString()
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }
}