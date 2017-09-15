package mehani.mehani.wyanbu.com.mehani;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mehani.mehani.wyanbu.com.mehani.Network.Language;
import mehani.mehani.wyanbu.com.mehani.Network.Variable;
import mehani.mehani.wyanbu.com.mehani.Network.network;

public class MainActivity extends AppCompatActivity {


    String number, name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        Language l = new Language();
        l.change(getBaseContext(),"ar_");
        SharedPreferences prefs = getSharedPreferences(Variable.SharedPreferences, MODE_PRIVATE);
        name = prefs.getString(Variable.SharedPreferencesNumber, "0");


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        if (name.equals("0")) {

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
            phone.setError(getString(R.string.phone_number_error));
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


        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle(getString(R.string.loading));
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

                            if (test.equals("1")) {

                                test = json.getString("Exists");

                                if (test.equals("1")) {
                                    test = json.getString("Errors");
                                    Toast.makeText(getApplicationContext(), getString(R.string.data_error) + " " + test, Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), getString(R.string.exists) + " " + test, Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), test, Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Error Main catch :", e.toString());

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e("Error main :", error.toString());


                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", number + "");

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(postRequest);


        if (check.length() != 6) {
            check.setError("Check Code Error");
            progress.cancel();

        } else {
            progress.cancel();
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

        }
    }

    public void done(View view) {
        done_fun();
    }

    private void done_fun() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner_city);
        EditText name = (EditText) findViewById(R.id.input_name);
        EditText email = (EditText) findViewById(R.id.input_email);

        SharedPreferences.Editor editor = getSharedPreferences(Variable.SharedPreferences, MODE_PRIVATE).edit();
        editor.putString(Variable.SharedPreferencesNumber, number);
        editor.apply();
        startActivity(new Intent(this, Home.class));
        finish();
    }
}
