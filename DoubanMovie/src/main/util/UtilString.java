import org.apache.commons.lang3.StringEscapeUtils;

public class UtilString
{
    public String transferredMeaning(String str)
    {
        if (str == null)
        {
            return null;
        }
        str = str.replace("\\", "\\\\")      // 斜杠
                .replace("'", "\\\'")       // 单引号
                .replace("\"", "\\\"")       //双引号
                .replace("%", "\\%")          //百分号
        //.replace("_","\\_");     //下划线
        ;
        return str;
    }
}
