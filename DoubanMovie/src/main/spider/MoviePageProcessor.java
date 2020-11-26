import com.sun.deploy.panel.CacheSettingsDialog;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import javax.management.JMException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;


public class MoviePageProcessor implements PageProcessor
{
    private Site site;

    @Override
    public Site getSite()
    {
        return this.site;
    }

    private static int thisPage=0;
    private static final int pageLimit=20;

    public MoviePageProcessor()
    {
        site = Site.me();
        site.setRetryTimes(3);
        site.setSleepTime(100);
        site.setUserAgent(UserAgent.instance().choice());
        site.addHeader("Referer","https://movie.douban.com/tag/");
        site.addHeader("Host","movie.douban.com");
        site.addHeader("Cookie","bid=7DJyWQExKFU; douban-fav-remind=1; ll=\"128465\"; __yadk_uid=KXUMzLmUmIUGRfygevv6TkhtVQVGSyE9; _vwo_uuid_v2=D16AA287C076429304E27918B1F0896B4|17bdebd3a5bca0d8fc83df05199d4936; __utmv=30149280.15571; dbcl2=\"155713388:eK6wtiiEdWs\"; push_doumail_num=0; push_noty_num=0; douban-profile-remind=1; __gads=ID=7749d4e68719af26-224c0efdd7c400f1:T=1605882200:RT=1605882200:S=ALNI_MaERVVphBHnb2eJyboFgqWcj20fdQ; __utmz=30149280.1606305083.48.34.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; __utmz=223695111.1606305083.41.36.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; ck=ZXVz; _pk_ref.100001.4cf6=%5B%22%22%2C%22%22%2C1606384904%2C%22https%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DWkbl4nD80XaICDkf7uO9w9Yh35AMBned0tVuRnCGFc04RDEpXyNi3sLNYJKMxcY9%26wd%3D%26eqid%3Dff2b63e800061d69000000065fbe4539%22%5D; _pk_ses.100001.4cf6=*; __utma=30149280.1408296567.1586792464.1606305083.1606384904.49; __utmb=30149280.0.10.1606384904; __utmc=30149280; __utma=223695111.1141843357.1588255087.1606305083.1606384904.42; __utmb=223695111.0.10.1606384904; __utmc=223695111; _pk_id.100001.4cf6=949b377e13e49552.1588255086.41.1606385293.1606305088.");
    }

    /**
     * 页面处理
     * 实现PageProcessor接口的主要方法
     * 当完成一次请求后,会将response封装成Page对象传到此处
     * 这里主要完成爬虫的数据抽取,以及爬取逻辑的实现
     *
     * @param page 响应页面对象
     */
    @Override
    public void process(Page page)
    {
        // 进入中间件处理方法
        UserAgent.instance().process(page, site);
        Html html = page.getHtml();

        // 通过预览页和详情页链接地址格式的差异,来区分到底是哪个页面
        if (page.getRequest().getUrl().contains("movie.douban.com/subject"))
        {
            // 此次响应为详情页面
            Movie movie = new Movie();

            try
            {
                movie.setName(html.xpath("//div[@id=\"content\"]/h1[1]/span[1]/text()").get());
                movie.setImageUrl(html.xpath("//a[@class=\"nbgnbg\"]/img/@src").get());
                try
                {
                    movie.setYear(Integer.parseInt(html.xpath("//div[@id=\"content\"]/h1[1]/span[2]/text()").get().replace("(", "").replace(")", "")));
                }
                catch (Exception e)
                {
                    movie.setYear(0);
                }
                movie.setDirectors(html.xpath("//div[@id=\"info\"]//span[1]/span[@class=\"attrs\"]/a/text()").all());
                movie.setWriters(html.xpath("//div[@id=\"info\"]//span[1]/span[@class=\"attrs\"]/a/text()").all());
                movie.setCasts(html.xpath("//*[@class=\"actor\"]//*[@class=\"attrs\"]//a/text()").all());
                movie.setLanguages(html.regex("<span class=\"pl\">语言:</span>([\\s\\S]*?)<br>").all());
                movie.getLanguages().replaceAll(s -> s.replace("\n", "").replace(" ", ""));
                movie.setCategories(html.xpath("//span[@property=\"v:genre\"]/text()").all());
                movie.setCountries(Arrays.asList(html.regex("<span class=\"pl\">制片国家/地区:</span>([\\s\\S]*?)<br>").get().replace(" ", "").replace("\n", "").split("/")));
                movie.setReleaseDate(html.xpath("//span[@property=\"v:initialReleaseDate\"]/text()").all());

                movie.setMins(html.xpath("//span[@property=\"v:runtime\"]/text()").get());

                movie.setIMDbUrl(html.xpath("//a[contains(@href,\"www.imdb.com\")][1]/@href").get());
                try
                {
                    movie.setRate(Float.parseFloat(html.xpath("//*[@class=\"ll rating_num\"]/text()").get()));;
                }
                catch (Exception e)
                {
                    movie.setRate(0);
                }
                try
                {
                    movie.setRateCount(Integer.parseInt(html.xpath("//*[@property=\"v:votes\"]/text()").get()));
                }
                catch (Exception e)
                {
                    movie.setRateCount(0);
                }

                List<String> descriptions = html.xpath("//span[@property=\"v:summary\"]/text()").all();
                StringBuilder description = new StringBuilder();
                descriptions.forEach(s -> {
                    description.append(s);
                });
                movie.setDescription(description.toString());

                try
                {
                    movie.setNumberOfEpisode(Integer.parseInt(html.regex("<span class=\"pl\">集数:</span>([\\s\\S]*?)<br>").get().replace("\n", "").replace(" ", "")));
                }
                catch (Exception e)
                {
                    movie.setNumberOfEpisode(0);
                }
                movie.setSigleMins(html.regex("<span class=\"pl\">单集片长:</span>([\\s\\S]*?)<br>").get().replace(" ", "").replace("\n", ""));
            }
            catch (Exception e)
            {
                //page.setSkip(true);
                //UtilLogger.log(e);
            }
            finally
            {
                String requestUrl=page.getRequest().getUrl();
                long movieNumber=Long.parseLong(requestUrl.substring(requestUrl.indexOf("subject")+7).replace("/",""));
                movie.setNumber(movieNumber);
                page.putField(Long.toString(movieNumber),movie);
            }
            return;
        }
        // 此次响应为预览页面
        int movieCount = Integer.parseInt(page.getJson().jsonPath("$.data.length()").get());
        if(movieCount==0)
        {
            // 爬取结束
            UtilLogger.log(UtilLogger.LogLevel.Info,"爬取结束!");
            return;
        }
        for (int i = 0; i < movieCount; i++)
        {
            String url = page.getJson().jsonPath("$.data[" + i + "].url").get();
            page.addTargetRequest(url);
        }
        thisPage++;

        //// 翻页
        //if(!html.get().contains("<a data-v-3e982be2=\"\" href=\"javascript:;\" class=\"more\">加载更多</a>"))
        //{
        //    // 爬取结束
        //    UtilLogger.log(UtilLogger.LogLevel.Info,"爬取结束!");
        //    return;
        //}
        String requestUrl="https://movie.douban.com/j/new_search_subjects?sort=U&range=0,10&tags=%E7%94%B5%E5%BD%B1&start="+thisPage*pageLimit;
        page.addTargetRequest(requestUrl);
        // ...
    }

    /**
     * 主函数
     *
     * @param args 命令行参数
     * @throws JMException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws JMException, InterruptedException
    {
        try
        {
            Spider spider = Spider.create(new MoviePageProcessor());
            spider.addUrl("https://movie.douban.com/j/new_search_subjects?sort=U&range=0,10&tags=%E7%94%B5%E5%BD%B1&start=0");
            spider.addPipeline(new MoviePipline());
            SpiderMonitor.instance().register(spider);
            spider.thread(4);
            spider.run();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            UtilLogger.log(UtilLogger.LogLevel.Info,"爬取完毕!");
        }
        catch (Exception e)
        {
            int a=0;
        }

        while (true)
        {
            Thread.sleep(1000);
        }
    }
}
