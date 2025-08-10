package com.example.newsapp.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun loadPictureWithGlide(url: String?, @DrawableRes defaultImage: Int): MutableState<Bitmap?> {
    val context = LocalContext.current
    val bitmapState = remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(url) {
        if (url == null) {
            // fallback immediately
            val fallback = BitmapFactory.decodeResource(context.resources, defaultImage)
            bitmapState.value = fallback
            return@LaunchedEffect
        }

        withContext(Dispatchers.IO) {
            try {
                val futureTarget: FutureTarget<Bitmap> = Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .submit()

                val bitmap = futureTarget.get()
                bitmapState.value = bitmap

                // clear the target (important to avoid memory leaks)
                Glide.with(context).clear(futureTarget)
            } catch (e: Exception) {
                e.printStackTrace()
                val fallback = BitmapFactory.decodeResource(context.resources, defaultImage)
                bitmapState.value = fallback
            }
        }
    }

    return bitmapState
}
