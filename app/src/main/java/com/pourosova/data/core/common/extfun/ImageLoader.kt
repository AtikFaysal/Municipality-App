package com.pourosova.data.core.common.extfun

import android.widget.ImageView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

fun Picasso.loadImage(drawable:Int, url:String, imageView: ImageView) {
    val imageUrl = url.ifEmpty { "no_image" }
    this.load(imageUrl).placeholder(drawable).into(imageView)
}

fun ImageView.loadImage(placeholder:Int, url : String)
{
    val imageUrl = url.ifEmpty { "no_image" }
    Picasso.get().load(imageUrl).placeholder(drawable)
        .into(this)
}

fun ImageView.loadImageUrl(drawable:Int, url : String)
{
    val imageUrl = url.ifEmpty { "no_image" }

    Picasso.get().load(imageUrl).placeholder(drawable)
        .into(this)
}

fun CircleImageView.loadImage(placeholder:Int, url : String)
{
    val imageUrl = url.ifEmpty { "no_image" }
    Picasso.get().load(imageUrl).placeholder(placeholder)
        .into(this)
}