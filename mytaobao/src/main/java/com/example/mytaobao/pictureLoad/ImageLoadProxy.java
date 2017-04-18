package com.example.mytaobao.pictureLoad;

import android.widget.ImageView;

/**
 * Created by lwb on 2017/4/17.
 */

public class ImageLoadProxy implements ImageLoader {
    public PictureHelper helper;
    public static ImageLoadProxy imageLoadProxy;

    public ImageLoadProxy() {
        helper = new PictureHelper();
    }

    public static ImageLoadProxy getInstance() {
        if (imageLoadProxy == null) {
            imageLoadProxy = new ImageLoadProxy();
        }
        return imageLoadProxy;
    }

    @Override
    public void displayImage(String imageUrl, ImageView imageView) {
        helper.displayImage(imageUrl, imageView);
    }
}
