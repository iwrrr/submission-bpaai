package id.hwaryun.storygram.router

import android.content.Context
import android.content.Intent
import id.hwaryun.shared.router.ActivityRouter
import id.hwaryun.story.presentation.ui.StoryActivity
import id.hwaryun.storygram.auth.presentation.ui.AuthActivity

class ActivityRouterImpl : ActivityRouter {

    override fun authActivity(context: Context): Intent {
        return Intent(context, AuthActivity::class.java)
    }

    override fun storyActivity(context: Context): Intent {
        return Intent(context, StoryActivity::class.java)
    }
}