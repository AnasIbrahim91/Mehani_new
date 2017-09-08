package mehani.mehani.wyanbu.com.mehani;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        GridViewAdapter gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item, items);

        gridView.setAdapter(gridViewAdapter);


    }


    public List<GridViewitems> getgridview() {

        items = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network.a1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonObject = response.getJSONArray("");
                    for(int i = 0; i <jsonObject.length();i++){

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"No Data ",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);


        items.add(new GridViewitems(R.drawable.homeicon, "anas", 0));
        items.add(new GridViewitems(R.drawable.homeicon, "anas", 0));
        items.add(new GridViewitems(R.drawable.homeicon, "anas", 0));
        items.add(new GridViewitems(R.drawable.homeicon, "anas", 0));
        items.add(new GridViewitems(R.drawable.homeicon, "anas", 0));
        return items;
    }
}
