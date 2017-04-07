package markovskisolutions.com.firebasetutorial;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.sql.SQLException;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    EditText username, password;
    String name;
    String token = FirebaseInstanceId.getInstance().getToken();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
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
                new attemptLogin().execute();
//                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private class attemptLogin extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        private boolean result;

        @Override
        protected void onPreExecute(){
            //           result = false;
//            progressDialog= new ProgressDialog(MainActivity.this);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("add");

        }
        @Override
        protected Void doInBackground(Void... voids) {
            DBHelper dbHelper=new DBHelper();
            try {
                long id = dbHelper.checkUser(name);
                if (id == -1) {
                    result = dbHelper.addDetails(token);
                    Toast.makeText(MainActivity.this,
                            "Daaaaa",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void result){
            hideDialog();
        }

        private void hideDialog() {
        }
    }

}
