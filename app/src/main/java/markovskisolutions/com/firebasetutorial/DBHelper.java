package markovskisolutions.com.firebasetutorial;
import android.database.Cursor;
import android.util.Log;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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


    public  boolean addDetails(String token){
       boolean result=false;
        try {
            PreparedStatement preparedStatement=
                    connection.prepareStatement("INSERT INTO " + AppConfig.TABLE_LOGINLOG + "(pushNotificationToken) " + " VALUES(?) ");
            preparedStatement.setString(1,token);
            result=preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return result;
    }

    public long checkUser(String user) throws SQLException {
        int id=-1;
        String query = "Select * FROM " +  AppConfig.TABLE_USER + " WHERE " + AppConfig.COLUMN_USER
                + " LIKE  " + user + "";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        Cursor cursor= (Cursor) rs;
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            id=cursor.getInt(0);
            cursor.close();
        }
        return id;
    }
}
