package com.arudo.blown.core.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.arudo.blown.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun BackgroundImageContainer(context: Context, imagePath: String, view: ImageView) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.centerRadius = 10f
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.start()

    Glide.with(context).load(imagePath).apply(
        RequestOptions.overrideOf(640, 360).placeholder(circularProgressDrawable).centerCrop()
            .error(R.drawable.ic_broken_image)
    ).into(view)
}