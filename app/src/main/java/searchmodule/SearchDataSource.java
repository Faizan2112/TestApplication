package searchmodule;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by faizan on 28/02/2017.
 */

public class SearchDataSource {
    String Url = "http://kworld.16mb.com/my41life/searchdetail.php";
    Context dataContext;
    List returnData = new ArrayList<>();
    public SearchDataSource(Context dataContext) {
        this.dataContext = dataContext;
    }
// and ajson array name

    public String Volleydata() {
        final List<SeachModel> setModalData;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.optJSONArray("markers");
                    // let us put this data into a string

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject o = jsonArray.getJSONObject(i);
                        List list = new ArrayList();


                        // LauncherActivity.ListItem listItem = new LauncherActivity.ListItem(o.getString("searchname"),o.getString("searchlocation"),o.getString("searchfavorite"));
                        list.add(o.getString("searchname"));
                        list.add(o.getString("searchLocation"));
                        list.add(o.getString("searchfavourite"));
                        returnData.add(list);
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
        // to execure this reqest queue we need reuest queue
        RequestQueue requestQueue = Volley.newRequestQueue(dataContext);
        requestQueue.add(stringRequest);


        return Volleydata();
    }

}
