package mehani.mehani.wyanbu.com.mehani;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profile extends AppCompatActivity {
    private String id, name;
    private ListView listView;
    private List<ListViewitem> items;
    private boolean checktext = false;

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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView id = (TextView) view.findViewById(R.id.txid);
                TextView name = (TextView) view.findViewById(R.id.txtTitle);
                Intent intent = new Intent(getApplicationContext(), Profile.class);
                //   intent.putExtra("id", id.getText().toString());
                // intent.putExtra("name", name.getText().toString());
                dialoggetrequest("Whats your problem of " + name.getText().toString(), id.getText().toString());


            }
        });


    }

    private void dialoggetrequest(String name, final String id) {


        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setHint("What is your prob");
        final FrameLayout container = new FrameLayout(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 25;
        params.rightMargin = 25;

        input.setLayoutParams(params);
        container.addView(input);
        alert.setTitle("put ypur problem");
        alert.setMessage(name);
        alert.setView(container);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();

                if (value.trim().equals("")) {
                    Snackbar.make(findViewById(R.id.profilecoordin), "No input <-->", Snackbar.LENGTH_LONG).show();
                } else {
                    postdatatodp(id, value, "1");
                    if (checktext) {
                        startActivity(new Intent(getApplicationContext(), MyRequest.class));
                        checktext = false;
                    } else {
                        Snackbar.make(findViewById(R.id.profilecoordin), "Input Error <-->", Snackbar.LENGTH_LONG).show();

                    }
                }

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alert.show();

    }

    private void postdatatodp(final String id, final String value, final String myid) {


        StringRequest postRequest = new StringRequest(Request.Method.POST, network.a3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),response,1).show();
                        if (response.equals("")) {
                            checktext = true;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("field_id", id);
                params.put("notes", value);
                params.put("app_user_id", myid);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(postRequest);


    }

    private void getdata() {

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setCancelable(false);
        progress.show();

        items = new ArrayList<>();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network.a2 + id, null, new Response.Listener<JSONObject>() {
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
                    ListViewAdapter listViewAdapter = new ListViewAdapter(getApplicationContext(), R.layout.list_item, items);
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



