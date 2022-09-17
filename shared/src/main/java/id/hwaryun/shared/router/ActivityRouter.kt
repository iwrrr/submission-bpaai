package id.hwaryun.shared.router

import android.content.Context
import android.content.Intent

interface ActivityRouter {
    fun authActivity(context: Context): Intent
    fun storyActivity(context: Context): Intent
}