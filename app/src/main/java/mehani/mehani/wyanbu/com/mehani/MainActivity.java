package mehani.mehani.wyanbu.com.mehani;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

                        if (false) {
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

            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,items);
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
