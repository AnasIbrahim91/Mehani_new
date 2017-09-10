package mehani.mehani.wyanbu.com.mehani;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
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
import com.github.clans.fab.FloatingActionButton;

public class Home extends AppCompatActivity {
    private GridView gridView;
    private List<GridViewitems> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gridView = (GridView) findViewById(R.id.gridview);
         FloatingActionButton mFab=(FloatingActionButton) findViewById(R.id.requestfab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MyRequest.class));

            }
        });

        getgridview();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView id = (TextView) view.findViewById(R.id.id_grid);
                TextView name = (TextView) view.findViewById(R.id.txtname_grid);

                Intent intent = new Intent(getApplicationContext(), Profile.class);
                intent.putExtra("id", id.getText().toString());
                intent.putExtra("name", name.getText().toString());

                startActivity(intent);

            }
        });

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
                        if (jsonObject.getJSONObject(i).getInt("contain_fields") == 1) {
                            items.add(new GridViewitems(jsonObject.getJSONObject(i).getString("icon"), jsonObject.getJSONObject(i).getString("name"), jsonObject.getJSONObject(i).getInt("id")));
                        }
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
