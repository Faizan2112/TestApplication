package com.upoadimage;

import android.graphics.Bitmap;

/**
 * Created by faizan on 05/06/2017.
 */
public class Config {

    public static String[] names;
    public static String[] urls;
    public static Bitmap[] bitmaps;

    public static final String GET_URL = "https://faizandream21.000webhostapp.com/PhotoUploadWithText/getImage.php";
    public static final String TAG_IMAGE_URL = "url";
    public static final String TAG_IMAGE_NAME = "name";
    public static final String TAG_JSON_ARRAY="result";

    public Config(int i){
        names = new String[i];
        urls = new String[i];
        bitmaps = new Bitmap[i];
    }
}
