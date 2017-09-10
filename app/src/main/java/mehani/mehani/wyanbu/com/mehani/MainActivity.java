package mehani.mehani.wyanbu.com.mehani;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String[] items = new String[]{"a1", "a2", "a3"};
    String number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        if (true) {
                            setContentView(R.layout.activity_main);
                            EditText phone = (EditText) findViewById(R.id.input_phone);
                            phone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                                        login_fun();
                                    }
                                    return false;
                                }
                            });
                        } else {
                            //home
                            startActivity(new Intent(getApplicationContext(), Home.class));
                            finish();

                        }
                    }
                }, 1000);


    }

    public void login(View view) {
        login_fun();
    }

    private void login_fun() {
        EditText phone = (EditText) findViewById(R.id.input_phone);
        if (phone.length() != 10) {
            phone.setError("Phone num error");
        } else {
            number = phone.getText().toString();
            setContentView(R.layout.activity_main_2);
            EditText check = (EditText) findViewById(R.id.input_check);
            check.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        check_fun();
                    }
                    return false;
                }
            });

        }
    }

    public void check(View view) {
        check_fun();
    }

    private void check_fun() {
        EditText check = (EditText) findViewById(R.id.input_check);

        //test


        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setCancelable(false);
        progress.show();


        StringRequest postRequest = new StringRequest(Request.Method.POST, network.a5,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String test = "1";
                        try {
                            JSONObject json = new JSONObject(response);
                            test = json.getString("Success");
                            if(test.equals("1")){
                                test = json.getString("Exists");
                                if(test.equals("1")){
                                    test = json.getString("Errors");
                                    Toast.makeText(getApplicationContext(),"error "+test,Toast.LENGTH_LONG).show();
                            }else {
                                    Toast.makeText(getApplicationContext(),"exists "+test,Toast.LENGTH_LONG).show();}
                            }else {
                            Toast.makeText(getApplicationContext(),test,Toast.LENGTH_LONG).show();}

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", number+"");

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(postRequest);


        startActivity(new Intent(this, Home.class));
        finish();


        if (check.length() != 6) {
            check.setError("Check Code Error");
        } else {
            setContentView(R.layout.activity_main_3);

            EditText email = (EditText) findViewById(R.id.input_email);
            email.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        done_fun();
                    }
                    return false;
                }
            });
            Spinner spinner = (Spinner) findViewById(R.id.spinner_city);

            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items);
            spinner.setAdapter(spinnerArrayAdapter);
        }
    }

    public void done(View view) {
        done_fun();
    }

    private void done_fun() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner_city);
        EditText name = (EditText) findViewById(R.id.input_name);
        EditText email = (EditText) findViewById(R.id.input_email);

        //items[spinner.getSelectedItemPosition()]

        startActivity(new Intent(this, Home.class));
        finish();
    }
}
