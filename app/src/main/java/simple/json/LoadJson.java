package simple.json;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.testapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import searchmodule.SearchAdapter;

public class LoadJson extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter adapter;

    String son = "[{\"searchname\":\"faizan\",\"searchlocation\":\"noida\",\"searchfavourite\":\"pizza\"},{\"searchname\":\"naim khan\",\"searchlocation\":\"bangalore\",\"searchfavourite\":\"tv\"},{\"searchname\":\"hasim\",\"searchlocation\":\"bhopal\",\"searchfavourite\":\"mobile\"},{\"searchname\":\"hasim\",\"searchlocation\":\"bhopal\",\"searchfavourite\":\"bike\"}]";
    String mUrl = "http://kworld.16mb.com/my41life/searchdetail.php";
    TextView mSName, mSLocation, mSFavporite;
    String TAG;
    List storeList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_json);


        recyclerView = (RecyclerView) findViewById(R.id.load_json_recycle);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


//        loadJsonData();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, mUrl, new Response.Listener<String>() {
            ProgressDialog pd = new ProgressDialog(getApplicationContext());

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.print(jsonObject);
                    JSONArray jsonaArray = jsonObject.getJSONArray("markers");
                    System.out.print(jsonaArray);

                    Log.d(TAG, "onResponse: "+jsonaArray);

                    for (int i = 0; i <= jsonObject.length(); i++) {
                        JSONObject jsonObject1 = jsonaArray.getJSONObject(i);
                        System.out.print(jsonObject1);
                        //JSONArray jsonArray = jsonObject1.getJSONArray()
                  /*       mSName.setText(jsonObject1.getString("searchname"));
                         mSLocation.setText(jsonObject1.getString("searchlocation"));
                         mSFavporite.setText(jsonObject1.getString("searchfavourite"));
               */          //String js = jsonObject1.toString();
                        // String jsonOn = jsonObject1.getJSONArray(jsonaArray);
                        //    JSONArray jsonArray = new JSONArray(jsonObject1);
                        SeachModel seachModel = new SeachModel();
                        seachModel.setSearchMName(jsonObject1.getString("searchname"));
                        seachModel.setSearchMName(jsonObject1.getString("searchlocation"));
                        seachModel.setSearchMName(jsonObject1.getString("searchfavourite"));
                        System.out.print(seachModel);
                        storeList.add(seachModel);
                        pd.dismiss();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onResponse: " + storeList);


            }

        }, new Response.ErrorListener() {
            @Override

            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        adapter = new SearchAdapter(this, storeList);
        recyclerView.setAdapter(adapter);

        //return storeList ;
    }

        //   recyclerView.setAdapter(adapter);

        // makeJsonObjectRequest();
        //  makeJsonArrayRequest();


    private void makeJsonObjectRequest() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, mUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jo = response.getJSONObject("markers");

                    String sn = jo.getString("searchname");
                    String sl = jo.getString("searchname");
                    String sf = jo.getString("searchname");

                    mSName.setText(sn);
                    mSLocation.setText(sl);
                    mSFavporite.setText(sf);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }


    private void loadJsonData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, mUrl, new Response.Listener<String>() {
            ProgressDialog pd = new ProgressDialog(getApplicationContext());

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.print(jsonObject);
                    JSONArray jsonaArray = jsonObject.getJSONArray("markers");
                    System.out.print(jsonaArray);

                    Log.d(TAG, "onResponse: "+jsonaArray);

                    for (int i = 0; i <= jsonObject.length(); i++) {
                        JSONObject jsonObject1 = jsonaArray.getJSONObject(i);
                        System.out.print(jsonObject1);
                        //JSONArray jsonArray = jsonObject1.getJSONArray()
                  /*       mSName.setText(jsonObject1.getString("searchname"));
                         mSLocation.setText(jsonObject1.getString("searchlocation"));
                         mSFavporite.setText(jsonObject1.getString("searchfavourite"));
               */          //String js = jsonObject1.toString();
                        // String jsonOn = jsonObject1.getJSONArray(jsonaArray);
                        //    JSONArray jsonArray = new JSONArray(jsonObject1);
                        SeachModel seachModel = new SeachModel();
                        seachModel.setSearchMName(jsonObject1.getString("searchname"));
                        seachModel.setSearchMName(jsonObject1.getString("searchlocation"));
                        seachModel.setSearchMName(jsonObject1.getString("searchfavourite"));
                       System.out.print(seachModel);
                        storeList.add(seachModel);
                     pd.dismiss();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onResponse: " + storeList);


            }

        }, new Response.ErrorListener() {
            @Override

            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        adapter = new SearchAdapter(this, storeList);
        recyclerView.setAdapter(adapter);

        //return storeList ;
    }

    private void makeJsonArrayRequest() {
        JsonArrayRequest request = new JsonArrayRequest(mUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                try {
                    for (int i = 0; i < response.length(); i++) {


                        JSONObject jo2 = (JSONObject) response.get(i);
                        JSONObject jo1 = jo2.getJSONObject("markers");

                        String sn = jo1.getString("searchname");
                        String sl = jo1.getString("searchname");
                        String sf = jo1.getString("searchname");

                        mSName.setText(sn);
                        mSLocation.setText(sl);
                        mSFavporite.setText(sf);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}
