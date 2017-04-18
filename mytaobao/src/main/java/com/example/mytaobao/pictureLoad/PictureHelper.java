package com.example.mytaobao.pictureLoad;

import android.widget.ImageView;

import com.example.mytaobao.widget.MyApplication;
import com.squareup.picasso.Picasso;


/**
 * Created by lwb on 2017/4/17.图片加载框架的封装
 */

public class PictureHelper implements ImageLoader {

    @Override
    public void displayImage(String imageUrl, ImageView imageView) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.setConnectTimeout(3, TimeUnit.SECONDS);
//
//        okHttpClient.setReadTimeout(5, TimeUnit.SECONDS);
//        okHttpClient.setWriteTimeout(5, TimeUnit.SECONDS);
//        Picasso picasso = new Picasso.Builder(MyApplication.getContext())
//                .downloader(new OkHttpDownloader(okHttpClient))
//                .build();

        Picasso.with(MyApplication.getContext()).load(imageUrl).into(imageView);
    }
}
