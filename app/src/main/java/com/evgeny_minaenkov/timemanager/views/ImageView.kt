package com.evgeny_minaenkov.timemanager.views

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

sealed class ImageErrorStatus {
    object NoImageProvided : ImageErrorStatus()
    object NoImageLoaded : ImageErrorStatus()
    data class RemoteError(val cause: Exception?) : ImageErrorStatus()
}

sealed class ImageViewState {
    object Loading : ImageViewState()
    data class Display(val image: Bitmap) : ImageViewState()
    data class Error(val status: ImageErrorStatus) : ImageViewState()
}

@Composable
fun ImageView(
    imageUri: String?,
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier.fillMaxSize(),
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String? = null,
    loader: @Composable (() -> Unit)? = null,
    error: @Composable (() -> Unit)? = null
) {
    val imageViewState = loadImage(url = imageUri)

    Box(modifier = modifier) {
        when (val state = imageViewState.value) {
            ImageViewState.Loading -> loader?.let { it() }
                ?: CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is ImageViewState.Display -> {
                Image(
                    bitmap = state.image.asImageBitmap(),
                    contentDescription = contentDescription,
                    contentScale = contentScale,
                    modifier = imageModifier
                )
            }
            is ImageViewState.Error -> error?.let { it() } ?: Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Error -> " + when (state.status) {
                    ImageErrorStatus.NoImageProvided -> "Url is empty, please provide image url"
                    ImageErrorStatus.NoImageLoaded -> "Backend can't load image. Sorry"
                    is ImageErrorStatus.RemoteError -> "Custom backend error ${state.status.cause?.localizedMessage}"
                }
            )
        }
    }
}

@Composable
fun loadImage(url: String?): MutableState<ImageViewState> {
    val imageViewState: MutableState<ImageViewState> =
        remember(url) { mutableStateOf(ImageViewState.Loading) }

    if (url.isNullOrBlank()) {
        imageViewState.value = ImageViewState.Error(ImageErrorStatus.NoImageProvided)
    } else {
        Picasso.get().load(url).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                val image = bitmap ?: run {
                    imageViewState.value = ImageViewState.Error(ImageErrorStatus.NoImageLoaded)
                    return@onBitmapLoaded
                }

                imageViewState.value = ImageViewState.Display(image)
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                e?.printStackTrace()
                imageViewState.value =
                    ImageViewState.Error(ImageErrorStatus.RemoteError(e))
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

        })
    }

    return imageViewState
}