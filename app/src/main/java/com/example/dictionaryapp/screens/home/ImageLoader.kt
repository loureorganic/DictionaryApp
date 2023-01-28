package com.example.dictionaryapp.screens.home

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.dictionaryapp.R

class ImageLoader {

    fun loaderImage(context: Context, imageData: String?, holderImage: ImageView) {
        Glide.with(context).load(imageData)
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .fitCenter()
            .into(holderImage)

    }
}