import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilLogger
{
    enum LogLevel
    {
        Info,
        Warn,
        Error,
    }

    @Test
    public void dd() throws IOException
    {
        log(LogLevel.Error,"这是错误信息");
    }
    public void log(LogLevel level, String message) throws IOException
    {
        String loggerPath = "log.txt";
        String strLevel=null;
        String datetime=null;
        String method=null;

        // 打开或创建文件
        File file = new File("log.txt");
        if (!file.exists())
            file.createNewFile();

        // 日志等级
        switch (level)
        {
            case Info:strLevel="[Info]";break;
            case Warn:strLevel="[Warn]";break;
            case Error:strLevel="[Error]";break;
            default:
                strLevel="Unkown";
                break;
        }

        // 日期时间
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd : HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        formatter.format(date);
        datetime=formatter.format(date);


        // 方法信息
        Method enclosingMethod = new Object().getClass().getEnclosingMethod().;
        method=enclosingMethod;

        // 写入日志
        FileOutputStream out = new FileOutputStream(file, true);
        StringBuffer sb = new StringBuffer();
        sb.append(System.currentTimeMillis());
        sb.append(enclosingMethod);
        sb.append(message+"\n");
        out.write(sb.toString().getBytes("utf-8"));
        out.close();
    }

}
