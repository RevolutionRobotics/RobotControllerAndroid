package com.revolution.robotics.core.interactor.api

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.revolution.robotics.core.cache.ImageCache

class ImageDownloader(
    private val imageCache: ImageCache,
    private val context: Context
) {

    companion object {
        private const val TAG = "Image downloader"
    }

    fun downloadImages(urls: List<String?>): Int {
        var downloaded = 0;
        for (url in urls) {
            url?.let {
                if (!imageCache.isSaved(it)) {
                    download(it)
                    downloaded++;
                }
            }
        }
        return downloaded
    }

    private fun download(url: String) {
        val requestOptions = RequestOptions()
            .downsample(DownsampleStrategy.CENTER_INSIDE)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)

        context.let {
            val bitmap = Glide.with(it)
                .asBitmap()
                .load(url)
                .apply(requestOptions)
                .submit()
                .get()

            imageCache.saveImage(url, bitmap)
        }
    }
}