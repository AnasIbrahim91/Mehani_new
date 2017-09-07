package mehani.mehani.wyanbu.com.mehani;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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
                        setContentView(R.layout.activity_main);
                    }
                }, 3000);

    }

    public void login(View view) {
        EditText phone = (EditText) findViewById(R.id.input_phone);
        if (phone.length() != 10) {
            phone.setError("Phone num error");
        } else {
            number = phone.getText().toString();
            setContentView(R.layout.activity_main_2);
        }
    }

    public void check(View view) {
        EditText check = (EditText) findViewById(R.id.input_check);

        if (check.length() != 6) {
            check.setError("Check Code Error");
        } else {
            setContentView(R.layout.activity_main_3);


            Spinner spinner = (Spinner) findViewById(R.id.spinner_city);

            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                    android.R.layout.simple_spinner_dropdown_item,
                    items);
            spinner.setAdapter(spinnerArrayAdapter);
        }
    }

    public void done(View view) {
        Spinner spinner = (Spinner) findViewById(R.id.spinner_city);
        EditText name = (EditText) findViewById(R.id.input_name);
        EditText email = (EditText) findViewById(R.id.input_email);

        //items[spinner.getSelectedItemPosition()]

            startActivity(new Intent(this,Home.class));
    }
}
