package com.flintcore.food_recycler_list_android.actionsUtils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class ImageManager {

    public static byte[] convertImage(Drawable drawable) {
        if (Objects.isNull(drawable)) {
            return null;
        }

        Bitmap bitmap = ((BitmapDrawable) drawable)
                .getBitmap();

        ByteArrayOutputStream streamImageByte = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, streamImageByte);
        return streamImageByte.toByteArray();
    }
}
