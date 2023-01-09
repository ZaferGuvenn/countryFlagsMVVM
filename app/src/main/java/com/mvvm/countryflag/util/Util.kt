package com.example.ulkeler1.Util

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mvvm.countryflag.R
import com.mvvm.countryflag.adapter.CountryClickListener

fun ImageView.downloadWithGlide(url:String?,circularProgresForPlaceHolder:CircularProgressDrawable){

    val options=RequestOptions.placeholderOf(circularProgresForPlaceHolder).error(R.drawable.ic_baseline_error_24)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)

}

fun circularProgressDrawableMaker(context: Context):CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth=8f
        centerRadius=6f
        start()

    }
}


@BindingAdapter("android:downloadUrl")
fun downloadUrlWithDataBinding(imageView: ImageView,url: String?){
    imageView.downloadWithGlide(url,circularProgressDrawableMaker(imageView.context))
}
