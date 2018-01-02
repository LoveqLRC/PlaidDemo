@file:JvmName("DrawableUtils")

package loveq.rc.plaiddemo.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat

/**
 * Created by rc on 2017/12/26.
 * Description:
 */
fun Drawable.toBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
    var canvas = Canvas(bitmap)
    setBounds(0, 0, canvas.width, canvas.height)
    draw(canvas)
    return bitmap
}

fun drawableToBitmap(context: Context, @DrawableRes drawableId: Int) =
        ContextCompat.getDrawable(context, drawableId)?.toBitmap()


