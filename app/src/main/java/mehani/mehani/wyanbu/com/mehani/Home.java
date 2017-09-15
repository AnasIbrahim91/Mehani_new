package mehani.mehani.wyanbu.com.mehani;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mehani.mehani.wyanbu.com.mehani.GridView.GridViewAdapter;
import mehani.mehani.wyanbu.com.mehani.GridView.GridViewitems;
import mehani.mehani.wyanbu.com.mehani.Network.Language;
import mehani.mehani.wyanbu.com.mehani.Network.network;

public class Home extends AppCompatActivity {

    private GridView HomegridView;
    private List<GridViewitems> Homeitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        HomegridView = (GridView) findViewById(R.id.gridview);

        FloatingActionButton Myrequest = (FloatingActionButton) findViewById(R.id.requestfab);
        FloatingActionButton Settingfab = (FloatingActionButton) findViewById(R.id.settindfab);

        final FloatingActionMenu menufab = (FloatingActionMenu) findViewById(R.id.menufab);

        Myrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menufab.close(true);
                startActivity(new Intent(getApplicationContext(), MyRequest.class));

            }
        });
        Settingfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menufab.close(true);
                LanguageDialog();
            }
        });

        GetGridViewData();

        HomegridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView id = view.findViewById(R.id.id_grid);

                Intent intent = new Intent(getApplicationContext(), Profile.class);
                intent.putExtra("id", id.getText().toString());
                menufab.close(true);
                startActivity(intent);

            }
        });

    }

    private void LanguageDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        builderSingle.setTitle(R.string.select_language);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add(getString(R.string.english));
        arrayAdapter.add(getString(R.string.arabic));


        builderSingle.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String strName = arrayAdapter.getItem(which);

                final AlertDialog.Builder builderInner = new AlertDialog.Builder(Home.this, R.style.AlertDialogCustom);
                builderInner.setMessage(strName);
                builderInner.setTitle(R.string.language_selected);
                builderInner.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Language l = new Language();

                        if (strName.equals(getString(R.string.english))) {
                            l.change(getBaseContext(), "en");
                        } else {
                            l.change(getBaseContext(), "ar_");

                        }
                        finish();
                        startActivity(getIntent());
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();



    }


    public void GetGridViewData() {
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle(getString(R.string.loading));
        progress.setCancelable(false);
        progress.show();

        Homeitems = new ArrayList<>();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network.a1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonObject = response.getJSONArray("AllCareers");

                    for (int i = 0; i < jsonObject.length(); i++) {
                        if (jsonObject.getJSONObject(i).getInt("contain_fields") == 1) {
                            Homeitems.add(new GridViewitems(jsonObject.getJSONObject(i).getString("icon"), jsonObject.getJSONObject(i).getString("name"), jsonObject.getJSONObject(i).getInt("id")));
                        }
                    }

                    GridViewAdapter gridViewAdapter = new GridViewAdapter(getApplicationContext(), R.layout.grid_item, Homeitems);
                    HomegridView.setAdapter(gridViewAdapter);

                    progress.dismiss();


                } catch (JSONException e) {
                    progress.dismiss();
                    e.printStackTrace();
                    Snackbar.make(findViewById(R.id.homecoordin), getString(R.string.no_data), Snackbar.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Home :", error.toString());
                 Snackbar.make(findViewById(R.id.homecoordin), getString(R.string.data_error), Snackbar.LENGTH_LONG).show();
                progress.dismiss();

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);


    }
}
