import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilDate
{
    public static Date strToDate(String strDate)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }
    public static Date strToDateTime(String strDateTime,String pattern)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDateTime, pos);
        return strtodate;
    }
}
