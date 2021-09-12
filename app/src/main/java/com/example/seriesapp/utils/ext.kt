package com.example.seriesapp.utils

import android.content.res.Resources
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