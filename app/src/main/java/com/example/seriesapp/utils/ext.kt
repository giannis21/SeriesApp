package com.example.seriesapp.utils

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.media.Image
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.example.seriesapp.R
import com.example.seriesapp.data.characters.Result
import java.nio.charset.StandardCharsets.UTF_8
import java.security.MessageDigest


val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

private var currentTimeStamp:String =""

fun md5(): ByteArray {
   return MessageDigest.getInstance("MD5").digest(getStr().toByteArray(UTF_8))
}

fun ByteArray.toHex(): Map<String, String> {
    val hex= joinToString(separator = "") { byte -> "%02x".format(byte) }
    return mapOf(Pair(hex, currentTimeStamp))
}

fun getStr():String{
    currentTimeStamp=System.currentTimeMillis().toString()
    return currentTimeStamp + Constants.PRIVATE_KEY + Constants.API_KEY
}

fun <M> loadImageGeneric(view: M, completeUrl: String, imageLoadedListener: () ->Unit){
    try {

        val genericView:View = view as ImageView
        Glide.with(genericView.context).load(completeUrl)
            .apply(RequestOptions().transform(FitCenter(), GranularRoundedCorners(16.px.toFloat(), 0.toFloat(), 0.toFloat(), 16.px.toFloat())))
            .skipMemoryCache(true)
            .listener(object : RequestListener<Drawable> { //this listener telling me when the load finished succesfully or not, so i have to hide progressBar
                override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    imageLoadedListener.invoke()
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    imageLoadedListener.invoke()
                    return false
                }

            })
            .into(view)

    }catch (e: Exception){
        println(e.message)
    }
}