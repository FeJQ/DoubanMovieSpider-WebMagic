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
import java.util.Date;
import java.util.List;

public class MoviePageProcessor implements PageProcessor
{
    private Site site;

    @Override
    public Site getSite()
    {
        return this.site;
    }

    public MoviePageProcessor()
    {
        site = Site.me();
        site.setRetryTimes(3);
        site.setSleepTime(100);
        site.setUserAgent(UserAgent.instance().choice());
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

        // 通过预览页和详情页链接地址格式的差异,来区分到底是哪个页面
        if (page.getRequest().getUrl().contains("movie.douban.com/subject"))
        {
            Html html = page.getHtml();
            // 此次响应为详情页面
            Movie movie = new Movie();

            movie.setName(html.xpath("//div[@id=\"content\"]/h1[1]/span[1]").get());
            movie.setImageUrl(html.xpath("//a[@class=\"nbgnbg\"]/img/@src").get());
            movie.setYear(Integer.parseInt(html.xpath("//div[@id=\"content\"]/h1[1]/span[2]").get()));
            movie.setDirectors(html.xpath("//div[@id=\"info\"]//span[1]/span[@class=\"attrs\"]/a/text()").all());
            movie.setWriters(html.xpath("//div[@id=\"info\"]//span[1]/span[@class=\"attrs\"]/a/text()").all());
            movie.setCasts(html.xpath("//span[@class=\"actor\"]/span[2]/span/a/text()").all());
            movie.setLanguages(html.xpath("").all());

            return;
        }
        // 此次响应为预览页面
        int movieCount = Integer.parseInt(page.getJson().jsonPath("$.data.length()").get());
        for (int i = 0; i < movieCount; i++)
        {
            String url = page.getJson().jsonPath("$.data[" + i + "].url").get();
            page.addTargetRequest(url);
        }
        // 翻页
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
        Spider spider = Spider.create(new MoviePageProcessor());
        spider.addUrl("https://movie.douban.com/j/new_search_subjects?sort=U&range=0,10&tags=&start=0");
        spider.addPipeline(new MoviePipline());
        SpiderMonitor.instance().register(spider);
        spider.run();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df.format(new Date()) + "爬取完毕!");
        while (true)
        {
            Thread.sleep(1000);
        }
    }
}
