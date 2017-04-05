package markovskisolutions.com.firebasetutorial;
import android.widget.EditText;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.RequestBody;
/**
 * Created by msolPC on 4/5/2017.
 */

public class RequestBuilder {
    //Login request body
    public static RequestBody LoginBody(EditText username, EditText password, String token) {
        return new FormBody.Builder()
                .add("action", "login")
                .add("format", "json")
                .add("username", String.valueOf(username))
                .add("password", String.valueOf(password))
                .add("logintoken", token)
                .build();
    }
}
