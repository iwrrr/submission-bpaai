package id.hwaryun.storygram.router

import android.content.Context
import android.content.Intent
import id.hwaryun.auth.presentation.ui.AuthActivity
import id.hwaryun.shared.router.ActivityRouter
import id.hwaryun.story.presentation.ui.StoryActivity

class ActivityRouterImpl : ActivityRouter {

    override fun authActivity(context: Context): Intent {
        return Intent(context, AuthActivity::class.java)
    }

    override fun storyActivity(context: Context): Intent {
        return Intent(context, StoryActivity::class.java)
    }
}