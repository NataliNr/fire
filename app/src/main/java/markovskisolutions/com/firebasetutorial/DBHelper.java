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
import java.util.HashMap;

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


    public  void addDetails(String name, String token){
//        String getUsername="SELECT user_id FROM new_schema.user  where username='"+name+"'";
        String getUsername="SELECT user.id FROM new_schema.user user_id where username='"+name+"'";


        try {
            PreparedStatement preparedStatement=
                    connection.prepareStatement("INSERT INTO loginlog(user_id,pushNotificationToken) VALUES(?,?) ");
            Statement stmt=connection.createStatement();
            String sqlUser_id="(SELECT user.id from new_schema.user where username=\'"+name+"\')";
            PreparedStatement prep=connection.prepareStatement(sqlUser_id);
            ResultSet resultSet=prep.executeQuery();
            resultSet.next();
            long val=resultSet.getLong("user.id");
            preparedStatement.setLong(1,val);
            preparedStatement.setString(2,token);
            preparedStatement.execute();
            preparedStatement.close();

        }
        catch (SQLException s){
            Log.e(TAG, s.getMessage());
        }
    }
}
