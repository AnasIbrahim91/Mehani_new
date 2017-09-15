package mehani.mehani.wyanbu.com.mehani;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

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

import mehani.mehani.wyanbu.com.mehani.ListView.ListViewAdapter;
import mehani.mehani.wyanbu.com.mehani.ListView.ListViewitem;
import mehani.mehani.wyanbu.com.mehani.Network.network;

public class MyRequest extends AppCompatActivity {
    private ListView RequestlistView;
    private List<ListViewitem> Requestitems_req;
    private SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request);

        RequestlistView = (ListView) findViewById(R.id.listview_req);

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);


        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetRequestData("");
            }
        });

        GetRequestData("");
    }

    private void GetRequestData(String id) {

        swipeLayout.setRefreshing(true);

        Requestitems_req = new ArrayList<>();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network.a4, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonObject = response.getJSONArray("Orders");

                    for (int i = 0; i < jsonObject.length(); i++) {

                        Requestitems_req.add(new ListViewitem(
                                jsonObject.getJSONObject(i).getString("id"),
                                jsonObject.getJSONObject(i).getString("field_name"),
                                jsonObject.getJSONObject(i).getString("notes") + " - " + jsonObject.getJSONObject(i).getString("Created_date"),
                                jsonObject.getJSONObject(i).getString("field_icon")
                        ));

                    }
                    ListViewAdapter RequestlistViewAdapter = new ListViewAdapter(getApplicationContext(), R.layout.list_item, Requestitems_req);
                    RequestlistView.setAdapter(RequestlistViewAdapter);
                    swipeLayout.setRefreshing(false);


                } catch (JSONException e) {
                    swipeLayout.setRefreshing(false);

                    e.printStackTrace();
                    Snackbar.make(findViewById(R.id.requestcoordin), getString(R.string.no_data), Snackbar.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(findViewById(R.id.requestcoordin), getString(R.string.data_error), Snackbar.LENGTH_LONG).show();
                Log.e("Error MyRequest :", error.toString());
                swipeLayout.setRefreshing(false);
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);


    }
}
