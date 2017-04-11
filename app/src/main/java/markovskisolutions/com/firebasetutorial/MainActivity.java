package markovskisolutions.com.firebasetutorial;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.iid.FirebaseInstanceId;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final String TAG = "MainActivity";

    EditText username, password;
    String name,getItem;
    String token = FirebaseInstanceId.getInstance().getToken();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner1);

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }

        Button logTokenButton = (Button) findViewById(R.id.logTokenButton);
        logTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, token);
                name= username.getText().toString();
                getItem = String.valueOf(spinner.getSelectedItem());
                new attemptLogin().execute();
            }
        });

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> servers = new ArrayList<String>();
        servers.add("new_schema");
        servers.add("red_schema");
        servers.add("green_schema");
        servers.add("blue_schema");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, servers);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private void ShowMessage(String msg){
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    private class attemptLogin extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        private boolean result;

        @Override
        protected void onPreExecute(){
            progressDialog= new ProgressDialog(MainActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("add");
            showDialog();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            DBHelper dbHelper=new DBHelper(getItem);

            result=dbHelper.addDetails(name, token);
            return null;
        }
        protected void onPostExecute(Void r) {
            hideDialog();
            if (result == false) {
                //result added
                ShowMessage("added");
            } else {
                //details not added
                ShowMessage("not added");
            }
        }
        private void showDialog(){
            if (!progressDialog.isShowing()){
                progressDialog.show();
            }
        }

        private void hideDialog() {
            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    }
}
