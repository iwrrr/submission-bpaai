package id.hwaryun.story.utils

import android.graphics.Bitmap
import android.net.Uri
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import id.hwaryun.story.R

object Extensions {

    fun ShapeableImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .into(this)
    }

    fun ShapeableImageView.loadImage(uri: Uri) {
        Glide.with(this.context)
            .load(uri)
            .into(this)
    }

    fun ShapeableImageView.loadImage(bitmap: Bitmap) {
        Glide.with(this.context)
            .load(bitmap)
            .into(this)
    }

    fun ShapeableImageView.clear() {
        Glide.with(this.context)
            .load(R.drawable.ic_placeholder)
            .into(this)
    }
}