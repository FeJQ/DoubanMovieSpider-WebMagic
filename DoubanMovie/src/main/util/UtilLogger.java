import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilLogger
{
    private static final String loggerPath = "log.txt";

    enum LogLevel
    {
        Info,
        Warn,
        Error,
    }

//    @Test
//    public void dd() throws IOException
//    {
//        try
//        {
//            int a = 1 / 0;
//        }
//        catch (Exception e)
//        {
//            log( e);
//        }
//    }

    /**
     * 打印日志
     *
     * @param level   日志等级
     * @param message 要打印的消息
     * @throws IOException
     */
    public static void log(LogLevel level, String message)
    {
        try
        {
            String strLevel = null;
            String strDatetime = null;
            String strMethod = null;

            // 打开或创建文件
            File file = new File("log.txt");
            if (!file.exists())
                file.createNewFile();

            // 日志等级
            switch (level)
            {
                case Info:
                    strLevel = "[Info]";
                    break;
                case Warn:
                    strLevel = "[Warn]";
                    break;
                case Error:
                    strLevel = "[Error]";
                    break;
                default:
                    strLevel = "Unkown";
                    break;
            }

            // 日期时间
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd : HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            formatter.format(date);
            strDatetime = formatter.format(date);


            // 方法信息
            Method enclosingMethod = new Object()
            {
            }.getClass().getEnclosingMethod();
            strMethod = String.valueOf(enclosingMethod);

            // 写入日志
            FileOutputStream out = new FileOutputStream(file, true);
            StringBuffer sb = new StringBuffer();
            sb.append(strLevel + "\t");
            sb.append(strDatetime + "\n");
            sb.append("Message:" + message + "\n");
            sb.append("\n--------------------------------------------------\n\n");
            out.write(sb.toString().getBytes("utf-8"));
            out.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    /**
     * 将异常打印到日志
     *
     * @param exception 异常对象
     * @throws IOException
     */
    public static void log(Exception exception)
    {
        try
        {
            String strLevel = "[Error]";
            String strDatetime = null;
            String strMethod = null;

            // 打开或创建文件
            File file = new File("log.txt");
            if (!file.exists())
                file.createNewFile();

            // 日期时间
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            formatter.format(date);
            strDatetime = formatter.format(date);

            // 方法信息
            Method enclosingMethod = new Object()
            {
            }.getClass().getEnclosingMethod();
            strMethod = String.valueOf(enclosingMethod);

            // 详细错误信息
            StackTraceElement stackTraceElement = exception.getStackTrace()[exception.getStackTrace().length - 1];
            String fileName = stackTraceElement.getFileName();
            String lineNumber = String.valueOf(stackTraceElement.getLineNumber());
            String stackTrace = getStackTrace(exception);
            String message = exception.getMessage();

            // 写入日志
            FileOutputStream out = new FileOutputStream(file, true);
            StringBuffer sb = new StringBuffer();
            sb.append(strLevel + "\t");
            sb.append(strDatetime + "\n");
            sb.append("File:" + fileName + "\n");
            sb.append("Line:" + lineNumber + "\n");
            sb.append("Method:" + enclosingMethod + "\n");
            sb.append("Message:" + message + "\n");
            sb.append("Stack:" + stackTrace);
            sb.append("\n--------------------------------------------------\n\n");
            out.write(sb.toString().getBytes("utf-8"));
            out.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    /**
     * 获取栈跟踪信息
     *
     * @param throwable 抛出的异常实例
     * @return 栈跟踪信息
     */
    public static String getStackTrace(Throwable throwable)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try
        {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
        finally
        {
            pw.close();
        }
    }
}
