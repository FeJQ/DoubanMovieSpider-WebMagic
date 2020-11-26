import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DBHelper
{

    //数据库连接地址
    private final static String url = "jdbc:mysql://localhost:3306/doubanmovie?characterEncoding=utf-8&serverTimezone=UTC&allowMultiQueries=true";
    //用户名
    private final static String userName = "root";
    //密码
    private final static String password = "08111001";
    //驱动类
    private final static String driver = "com.mysql.cj.jdbc.Driver";

    private static Connection connection = null;

    private DBHelper()
    {
        // 不准实例化
    }

    /**
     * 连接数据库
     *
     * @return conn
     */
    public static Connection getConnection()
    {
        try
        {
            if (connection == null || connection.isClosed())
            {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, userName, password);
            }
        }
        catch (Exception e)
        {
            UtilLogger.log(e);
        }
        return connection;
    }

    /**
     * 关闭连接对象
     *
     * @param conn  连接对象
     * @param pstmt 预编译对象
     */
    public static void close(Connection conn, PreparedStatement pstmt)
    {
        try
        {
            if (pstmt != null)
            {
                pstmt.close();
            }
            if (conn != null)
            {
                conn.close();
            }
        }
        catch (Exception e)
        {
            UtilLogger.log(e);
        }
    }

    public static int update(String sql)
    {
        int result=0;
        Statement state = null;
        try
        {
            state = getConnection().createStatement();
            result = state.executeUpdate(sql);
            return result;
        }
        catch (SQLException e)
        {
            UtilLogger.log(e);
            return 0;
        }
    }

    public static int update(String sql, Object... args)
    {
        int result = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (args != null)
            {
                for (int i = 1; i <= args.length; i++)
                {
                    preparedStatement.setObject(i, args[i]);
                }
            }
            result = preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            UtilLogger.log(e);
        }
        finally
        {
            close(connection, preparedStatement);
        }
        return result;
    }

    public static boolean updateBatch(String sql, Object... args)
    {
        boolean flag = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            connection = getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 1; i <= args.length; i++)
            {
                preparedStatement.setObject(i, args[i]);
            }
            preparedStatement.addBatch();


            int [] results=preparedStatement.executeBatch(); //批量执行
            connection.commit();//提交事务
            preparedStatement.clearBatch();
            flag = true;
        }
        catch (SQLException e)
        {
            try
            {
                connection.rollback(); //进行事务回滚
            }
            catch (SQLException ex)
            {
                UtilLogger.log(e);
            }
        }
        finally
        {
            close(connection, preparedStatement);
        }
        return flag;
    }
}

