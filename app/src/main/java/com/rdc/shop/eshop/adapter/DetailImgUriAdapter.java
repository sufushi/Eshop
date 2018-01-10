package com.rdc.shop.eshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.rdc.shop.eshop.ui.DetailsPhotoActivity;

import java.util.ArrayList;
import java.util.List;

public class DetailImgUriAdapter extends NineGridImageViewAdapter<Uri> {

    @Override
    protected void onDisplayImage(Context context, ImageView imageView, Uri s) {
        Glide.with(context).load(s).into(imageView);
    }



    @Override
    protected ImageView generateImageView(Context context) {
        return super.generateImageView(context);
    }

    @Override
    protected void onItemImageClick(Context context, int index, List<Uri> list) {
//        Intent intent = new Intent(context, DetailsPhotoActivity.class);
//        intent.putExtra("photo_index", index);
//        intent.putStringArrayListExtra("photo_list",(ArrayList<String>) list);
        Intent intent1 = DetailsPhotoActivity.newIntent(context, index, (ArrayList<Uri>) list);
        context.startActivity(intent1);
    }



}