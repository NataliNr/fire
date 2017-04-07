package markovskisolutions.com.firebasetutorial;

import android.app.DownloadManager;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by msolPC on 4/5/2017.
 */

public class AppConfig {


    public static final String db_connection="jdbc:mysql://192.168.1.138:3306/new_schema?useSSL=false";
    public static final String db_user="root";
    public static final String db_pass="root";
    public static final String Driver="com.mysql.jdbc.Driver";
    public static final String TABLE_LOGINLOG="loginlog";
    public static final String TABLE_USER="user";
    public static final String COLUMN_USER="username";

}
