package markovskisolutions.com.firebasetutorial;


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
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    EditText username, password;
    private String response;
    private OkHttpClient client;
    String token = FirebaseInstanceId.getInstance().getToken();
    String url = "https://red.fach-it.de/mobility-platform/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.name);
        username.toString();
        password = (EditText) findViewById(R.id.password);
        client = new OkHttpClient();
        client = new OkHttpClient.Builder().build();

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
//                // Get token
//
//
//                // Log and toast
//                String msg = getString(R.string.msg_token_fmt, token);
                Log.d(TAG, String.valueOf(username));
                Log.d(TAG, url);
                Log.d(TAG, token);
                attemptLogin(url);
//                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void attemptLogin(String url) {
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                try {
                    response = ApiCall.POST(
                            client,
                            params[0],
                            RequestBuilder.LoginBody(username, password, token));
                    Log.d("Response", response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(url);
    }
}
