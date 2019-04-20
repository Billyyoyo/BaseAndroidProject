package fun.flyee.sunshine4u.android.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.Locale;

import fun.flyee.sunshine4u.android.ApplicationContext;
import fun.flyee.sunshine4u.android.utils.glide.BlurTransformation;
import fun.flyee.sunshine4u.android.utils.glide.RoundedCornersTransformation;
import okhttp3.Call;

public class ImageLoader {

    public static void setBlur(ImageView imageView, Object url) {
        if (imageView == null) {
            return;
        }
        Glide.with(ApplicationContext.getContext())
                .load(url)
                .apply(new RequestOptions().transform(new BlurTransformation()))
                .into(imageView);
    }

    public static void setImage(ImageView imageView, Object url) {
        if (imageView == null) {
            return;
        }
        Glide.with(ApplicationContext.getContext())
                .load(url)
                .into(imageView);
    }

    public static void setImage(ImageView imageView, Object url, int id) {
        if (imageView == null) {
            return;
        }
        Glide.with(ApplicationContext.getContext())
                .load(url)
                .apply(new RequestOptions().placeholder(id))
                .into(imageView);
    }

    public static void setImageDisHardConfig(ImageView imageView, Object url) {
        if (imageView == null) {
            return;
        }
        Glide.with(ApplicationContext.getContext())
                .load(url)
                .apply(new RequestOptions().disallowHardwareConfig())
                .into(imageView);
    }

    public static void centerImage(ImageView imageView, Object url, int id) {
        if (imageView == null) {
            return;
        }
        Glide.with(ApplicationContext.getContext())
                .load(url)
                .apply(new RequestOptions().centerCrop().placeholder(id))
                .into(imageView);
    }

    public static void circleImage(ImageView imageView, Object url, int id) {
        if (imageView == null) {
            return;
        }
        Glide.with(ApplicationContext.getContext())
                .load(url)
                .apply(new RequestOptions().circleCrop().placeholder(id))
                .into(imageView);
    }

    public static void squareImage(ImageView imageView, Object url, int id) {
        if (imageView == null) {
            return;
        }
        Glide.with(ApplicationContext.getContext())
                .load(url)
                .apply(new RequestOptions().override(300).placeholder(id))
                .into(imageView);
    }

    public static void roundedCornersImage(ImageView imageView, Object url, int radius, int id) {
        if (imageView == null) {
            return;
        }
        Glide.with(ApplicationContext.getContext())
                .load(url)
                .apply(new RequestOptions().transform(new RoundedCornersTransformation(radius, 0)).placeholder(id))
                .into(imageView);
    }

    public static void roundedCornersImage1(ImageView imageView, Object url, int radius) {
        if (imageView == null) {
            return;
        }
        Glide.with(ApplicationContext.getContext())
                .load(url)
                .apply(new RequestOptions().centerCrop().transform(new RoundedCorners(radius)))
                .into(imageView);
    }
}
