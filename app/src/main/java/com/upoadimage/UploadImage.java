package com.upoadimage;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.testapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class UploadImage extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "errror";
    private int PICK_IMAGE_REQUEST = 1;
    ImageView mSelectImage;
    Button mUploadImage;
    EditText mText;
    private Bitmap bitmap;
    private String UPLOAD_URL ="http://faizandream21.000webhostapp.com/PhotoUploadWithText/upload.php";

    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        mSelectImage = (ImageView) findViewById(R.id.select_image);
        mUploadImage = (Button) findViewById(R.id.upload_image);
        mText = (EditText)findViewById(R.id.upload_text);
        mSelectImage.setOnClickListener(this);
        mUploadImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.select_image:
                selectImage();
                break;
            
            case R.id.upload_image:
                uploadImage();
                break;
        }
    }

   /* private void uploadImage() {
        StringRequest uploadImage = new StringRequest(Request.Method.POST, UPLOAD_URL, new Response.Listener<String>() {
     //    final ProgressDialog loading = ProgressDialog.show(getApplicationContext(),"Uploding..","Please waih", false, false);
            @Override
            public void onResponse(String s) {
         //        loading.dismiss();
                Toast.makeText(UploadImage.this, s , Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UploadImage.this, error.getMessage() , Toast.LENGTH_LONG).show();
               // Log.e(TAG,error.getMessage());
            }

       })
        {
            protected Map<String,String> getParams() throws AuthFailureError           {
           String image = getStringImage(bitmap);
            String name = mText.getText().toString().trim();
               Map<String,String> params = new Hashtable<>();
                params.put(KEY_IMAGE,image);
                params.put(KEY_NAME,name);
                return params ;
            }


    };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(uploadImage);

    }*/
   private void uploadImage(){
       //Showing the progress dialog
       final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
       StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
               new Response.Listener<String>() {
                   @Override
                   public void onResponse(String s) {
                       //Disimissing the progress dialog
                       loading.dismiss();
                       //Showing toast message of the response
                       Toast.makeText(UploadImage.this, s , Toast.LENGTH_LONG).show();
                   }
               },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError volleyError) {
                       //Dismissing the progress dialog
                       loading.dismiss();

                       //Showing toast
                   //    Toast.makeText(UploadImage.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                   }
               }){
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               //Converting Bitmap to String
               String image = getStringImage(bitmap);

               //Getting Image Name
               String name = mText.getText().toString().trim();

               //Creating parameters
               Map<String,String> params = new Hashtable<String, String>();

               //Adding parameters
               params.put(KEY_IMAGE, image);
               params.put(KEY_NAME, name);

               //returning parameters
               return params;
           }
       };

       //Creating a Request Queue
       RequestQueue requestQueue = Volley.newRequestQueue(this);

       //Adding request to the queue
       requestQueue.add(stringRequest);
   }

    private String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodedImage;
    }


    private void selectImage() {
        Intent chooseimage = new Intent(Intent.ACTION_GET_CONTENT);
        chooseimage.setType("image/*");
       // chooseimage.setType(Intent.ACTION_GET_CONTENT);
      //  startActivityForResult(Intent.createChooser(chooseimage, "Choose Image"), PICK_IMAGE_REQUEST);
        startActivityForResult(chooseimage, PICK_IMAGE_REQUEST);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageData) {
        Uri filePath = imageData.getData();
        super.onActivityResult(requestCode, resultCode, imageData);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && imageData != null && imageData.getData() != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                mSelectImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }
}
