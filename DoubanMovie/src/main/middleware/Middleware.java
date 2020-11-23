import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

public interface Middleware
{
    public void process(Page page, Site site);
}
