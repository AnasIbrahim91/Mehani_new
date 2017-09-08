package mehani.mehani.wyanbu.com.mehani;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    private GridView gridView;
    private List<GridViewitems> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gridView = (GridView) findViewById(R.id.gridview);


        getgridview();


    }


    public List<GridViewitems> getgridview() {
       final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setCancelable(false);
        progress.show();

        items = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network.a1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonObject = response.getJSONArray("AllCareers");
                    for (int i = 0; i < jsonObject.length(); i++) {

                        items.add(new GridViewitems(jsonObject.getJSONObject(i).getString("icon"), jsonObject.getJSONObject(i).getString("name"), jsonObject.getJSONObject(i).getInt("id")));
                    }
                    GridViewAdapter gridViewAdapter = new GridViewAdapter(getApplicationContext(), R.layout.grid_item, items);

                    gridView.setAdapter(gridViewAdapter);
                    progress.dismiss();



                } catch (JSONException e) {
                    progress.dismiss();

                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "No Data ", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();


            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);


        return items;
    }
}
