package com.arudo.blown.core.utils

import android.content.Context
import android.widget.ImageView
import com.arudo.blown.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun BackgroundImageContainer(context: Context, imagePath: String, view: ImageView) {
    Glide.with(context).load(imagePath).apply(
        RequestOptions.errorOf(R.drawable.ic_broken_image)
    ).into(view)
}