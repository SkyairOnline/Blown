package com.arudo.blown.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import kotlinx.parcelize.Parcelize

@Parcelize
data class RequirementsEntity(

    @NonNull
    val minimum: String,

    @NonNull
    val recommended: String
) : Parcelable
