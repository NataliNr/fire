package markovskisolutions.com.firebasetutorial;
import android.database.Cursor;
import android.util.Log;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by msolPC on 4/5/2017.
 */

public class DBHelper {
    private Connection connection;
    private String new_schema= "new_schema";
    private String red_schema= "red_schema";
    private String green_schema= "green_schema";
    private String blue_schema= "blue_schema";
    private String TAG = DBHelper.class.getSimpleName();

    public DBHelper(String getItem){
        connection=null;
        try {
            if(getItem.equals(new_schema)){
                Class.forName(AppConfig.Driver);
                connection= DriverManager.
                        getConnection(AppConfig.db_connection_new_schema, AppConfig.db_user_new_schema, AppConfig.db_pass_new_schema);
            }else if(getItem.equals(red_schema)){
                Class.forName(AppConfig.Driver);
                connection= DriverManager.
                        getConnection(AppConfig.db_connection_red_schema, AppConfig.db_user_red_schema, AppConfig.db_pass_red_schema);
            }else if(getItem.equals(green_schema)){
                Class.forName(AppConfig.Driver);
                connection= DriverManager.
                        getConnection(AppConfig.db_connection_green_schema, AppConfig.db_user_green_schema, AppConfig.db_pass_green_schema);
            }else if (getItem.equals(blue_schema)){
                Class.forName(AppConfig.Driver);
                connection= DriverManager.
                        getConnection(AppConfig.db_connection_blue_schema, AppConfig.db_user_blue_schema, AppConfig.db_pass_blue_schema);
            }
        }
        catch (SQLException s){
            Log.e(TAG, s.getMessage());
        }
        catch (ClassNotFoundException ex){
            Log.e(TAG, ex.getMessage());
        }
    }


    public  boolean addDetails(String username, String token){
        boolean result=false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
            String currentDateandTime = sdf.format(new Date());
            String myVersion = android.os.Build.VERSION.RELEASE;
            PreparedStatement preparedStatement=
                    connection.prepareStatement("INSERT INTO loginlog(user_id,pushNotificationToken, logedon, osname,osversion,created_by_aud) VALUES(?,?,?,?,?,?) ");
            String sqlUser_id="(SELECT user.id from user where username=\'"+username+"\')";
            PreparedStatement prep=connection.prepareStatement(sqlUser_id);
            ResultSet resultSet=prep.executeQuery();
            resultSet.next();
            long val=resultSet.getLong("user.id");
            preparedStatement.setLong(1,val);
            preparedStatement.setString(2,token);
            preparedStatement.setString(3,currentDateandTime);
            preparedStatement.setString(4,"Android");
            preparedStatement.setString(5,myVersion);
            preparedStatement.setString(6,username);
            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException s){
            Log.e(TAG, s.getMessage());
        }
        return result;
    }
}
