package com.upoadimage;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URL;

import static com.upoadimage.Config.urls;

/**
 * Created by faizan on 05/06/2017.
 */

public class GetBitmap extends AsyncTask<Void,Void,Void> {


    private Context mContext;
    private String[] urls;
    private ProgressDialog mLoading;
    private LoadData mloadData;
    private Bitmap image ;


    public GetBitmap(Context context , LoadData loadData , String[] urls)
    {
        this.mContext = context ;
        this.urls = urls ;
        this.mloadData = loadData;

    }

    protected  void onPreExecute()
    {
        super.onPreExecute();
        mLoading = ProgressDialog.show(mContext,"Downloading Image","Please wait..",false ,false );

    }
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mLoading.dismiss();
        mloadData.showData();
    }
    protected Void doInBackground(Void... params) {
        for(int i = 0 ;i<urls.length ; i++)
        {
            Config.bitmaps[i]=getImage(urls[i]);

        }
        return null ;
    }

    private Bitmap getImage(String bitmapUrl) {
        URL url ;

        try
        {
            url = new URL(bitmapUrl);
       //     image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
       image = BitmapFactory.decodeStream((InputStream)new URL("http://www.planwallpaper.com/#static/images/303836.jpg").getContent());
            System.out.print(image);
        }catch (Exception e){e.printStackTrace();}
        return image ;
    }
}
