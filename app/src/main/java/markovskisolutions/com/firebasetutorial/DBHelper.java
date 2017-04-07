package markovskisolutions.com.firebasetutorial;
import android.util.Log;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.RequestBody;
/**
 * Created by msolPC on 4/5/2017.
 */

public class DBHelper {
    private Connection connection;
    private String TAG = DBHelper.class.getSimpleName();

    public DBHelper(){
        connection=null;
        try {
            Class.forName(AppConfig.Driver);
            connection= DriverManager.
                    getConnection(AppConfig.db_connection, AppConfig.db_user, AppConfig.db_pass);
  //          createTable();
        }
        catch (SQLException s){
            Log.e(TAG, s.getMessage());
        }
        catch (ClassNotFoundException ex){
            Log.e(TAG, ex.getMessage());
        }
    }

//    private void createTable() {
//        String query = "CREATE TABLE IF NOT EXISTS"+ AppConfig.TABLE_NAME +"(uid int PRIMARY KEY auto_increment,"
//        +"token varchar(255) not null,";
//        try {
//            Statement statement = connection.createStatement();
//        }
//        catch (SQLException s){
//            Log.e(TAG, s.getMessage());
//        }
//    }

    public  boolean addDetails(String token){
       boolean result=false;
        try {
            PreparedStatement preparedStatement=
                    connection.prepareStatement("INSERT INTO " + AppConfig.TABLE_NAME + " (pushNotificationToken) " +" VALUES(?) ");
            preparedStatement.setString(1,token);
            result=preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return result;
    }
}
