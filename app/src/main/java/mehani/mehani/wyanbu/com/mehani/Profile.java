package mehani.mehani.wyanbu.com.mehani;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
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

public class Profile extends AppCompatActivity {
    String id, name;
    private ListView listView;
    private List<ListViewitem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            id = "1";
            name = "Error";
        } else {
            id = extras.getString("id");
            name = extras.getString("name");
        }
        setTitle(name);
        listView = (ListView) findViewById(R.id.listview);

        getdata();


    }

    private void getdata() {

            final ProgressDialog progress = new ProgressDialog(this);
            progress.setTitle("Loading");
            progress.setCancelable(false);
            progress.show();

            items = new ArrayList<>();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network.a2+id, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonObject = response.getJSONArray("AllFields");
                        for (int i = 0; i < jsonObject.length(); i++) {

                            items.add(new ListViewitem(
                                    jsonObject.getJSONObject(i).getString("id"),
                                    jsonObject.getJSONObject(i).getString("name"),
                                    jsonObject.getJSONObject(i).getString("description"),
                                    jsonObject.getJSONObject(i).getString("icon")
                         ));

                        }
                        ListViewAdapter  listViewAdapter = new ListViewAdapter(getApplicationContext(), R.layout.list_item, items);
                        listView.setAdapter(listViewAdapter);

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


         }
    }



