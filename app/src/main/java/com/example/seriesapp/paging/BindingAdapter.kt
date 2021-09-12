package com.example.seriesapp.paging

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.example.seriesapp.R
import com.example.seriesapp.data.characters.Result
import com.example.seriesapp.utils.loadImageGeneric
import com.example.seriesapp.utils.px


object BindingAdapter  {

    @BindingAdapter("imageUrl", "format")
    @JvmStatic
    fun <T,H> loadImage(view: ImageView, character: T, format: H) {
        val progressBar: ProgressBar? = (view.parent as? ConstraintLayout)?.findViewById<ProgressBar>(R.id.progressBar)

        (character as Result).apply {        //i pass T type object so to be generic and i cast it to Result in order to use its attributes
                val url = if(!thumbnail.path.contains("https"))
                    thumbnail.path.replace("http", "https")
                else
                    thumbnail.path

                val completeUrl = "$url/${format.toString()}.${thumbnail.extension}"

                loadImageGeneric(view, completeUrl){
                    progressBar?.visibility=View.GONE
                }
            }

    }

    @BindingAdapter("setGravity")
    @JvmStatic
    fun setGravity(view: LinearLayout, description: String) {
       if(description.isEmpty())
           view.gravity = Gravity.CENTER
        else
           view.gravity = Gravity.TOP
    }


}