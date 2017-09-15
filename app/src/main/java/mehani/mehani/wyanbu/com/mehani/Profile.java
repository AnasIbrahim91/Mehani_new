package mehani.mehani.wyanbu.com.mehani;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
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

import mehani.mehani.wyanbu.com.mehani.ListView.ListViewAdapter;
import mehani.mehani.wyanbu.com.mehani.ListView.ListViewitem;
import mehani.mehani.wyanbu.com.mehani.Network.network;

public class Profile extends AppCompatActivity {

    private String HomeID;

    private ListView ProfilListView;
    private List<ListViewitem> ProfilItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);


        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
        } else {
            HomeID = extras.getString("id");
        }

        ProfilListView = (ListView) findViewById(R.id.profilelistview);

        GetData();

        ProfilListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView id = view.findViewById(R.id.txid);
                TextView name = view.findViewById(R.id.txtTitle);

                DialogGetRequest(getString(R.string.ProbloemDialog) + name.getText().toString(), id.getText().toString());
            }
        });


    }

    private void DialogGetRequest(String Ordername, final String Orderid) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this, R.style.AlertDialogCustom);


        final EditText input = new EditText(this);
        input.setHint(getString(R.string.EdittextInputHint));

        final FrameLayout container = new FrameLayout(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 25;
        params.rightMargin = 25;
        input.setGravity(Gravity.START);
        input.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);

        input.setLayoutParams(params);
        container.addView(input);

        alert.setMessage(Ordername);
        alert.setView(container);

        alert.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();

                if (value.trim().equals("")) {
                    Snackbar.make(findViewById(R.id.profilecoordin), getString(R.string.No_input), Snackbar.LENGTH_LONG).show();
                } else {
                    PostDataToDb(Orderid, value, "1");

                }

            }
        });

        alert.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        alert.show();

    }

    private void PostDataToDb(final String Orderid, final String Ordervalue, final String myid) {


        StringRequest postRequest = new StringRequest(Request.Method.POST, network.a3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String test = "";
                        try {
                            JSONObject json = new JSONObject(response);
                            test = json.getString("Success");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (test.contains("تم حفظ")) {
                            Toast.makeText(getApplicationContext(), test, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MyRequest.class));

                        } else {
                            Snackbar.make(findViewById(R.id.profilecoordin), getString(R.string.Input_error), Snackbar.LENGTH_LONG).show();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e("Error Profile :", error.toString());


                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("field_id", Orderid);
                params.put("notes", Ordervalue);
                params.put("app_user_id", myid);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(postRequest);


    }

    private void GetData() {

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle(getString(R.string.loading));
        progress.setCancelable(false);
        progress.show();

        ProfilItems = new ArrayList<>();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network.a2 + HomeID, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonObject = response.getJSONArray("AllFields");
                    for (int i = 0; i < jsonObject.length(); i++) {

                        ProfilItems.add(new ListViewitem(
                                jsonObject.getJSONObject(i).getString("id"),
                                jsonObject.getJSONObject(i).getString("name"),
                                jsonObject.getJSONObject(i).getString("description"),
                                jsonObject.getJSONObject(i).getString("icon")
                        ));

                    }
                    ListViewAdapter listViewAdapter = new ListViewAdapter(getApplicationContext(), R.layout.list_item, ProfilItems);
                    ProfilListView.setAdapter(listViewAdapter);

                    progress.dismiss();

                } catch (JSONException e) {
                    progress.dismiss();

                    e.printStackTrace();
                    Snackbar.make(findViewById(R.id.profilecoordin), getString(R.string.no_data), Snackbar.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Profile :", error.toString());
                Snackbar.make(findViewById(R.id.profilecoordin), getString(R.string.data_error), Snackbar.LENGTH_LONG).show();

                progress.dismiss();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);


    }
}



