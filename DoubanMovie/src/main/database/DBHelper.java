import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBHelper
{
    public static final String url = "jdbc:mysql://127.0.0.1/student";
    public static final String name = "com.mysql.jdbc.Driver";
    public static final String user = "root";
    public static final String password = "";

    public Connection connection = null;
    public PreparedStatement pst = null;

    public DBHelper() throws ClassNotFoundException, SQLException
    {
        try
        {
            Class.forName(name);//指定连接类型
            connection = DriverManager.getConnection(url, user, password);//获取连接
        }
        catch (Exception e)
        {

        }

    }

    public void excute(PreparedStatement sql)
    {
        try
        {
            sql.execute();
        }
        catch (Exception e)
        {

        }

    }



    public void close()
    {
        try
        {
            this.connection.close();
            this.pst.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
