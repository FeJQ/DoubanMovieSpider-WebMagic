import java.util.*;

public class Movie
{
    public long getNumber()
    {
        return number;
    }

    public void setNumber(long number)
    {
        this.number = number;
    }
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public List<String> getDirectors()
    {
        return directors;
    }

    public void setDirectors(List<String> directors)
    {
        this.directors = directors;
    }

    public List<String> getWriters()
    {
        return writers;
    }

    public void setWriters(List<String> writers)
    {
        this.writers = writers;
    }

    public List<String> getCasts()
    {
        return casts;
    }

    public void setCasts(List<String> casts)
    {
        this.casts = casts;
    }

    public List<String> getLanguages()
    {
        return languages;
    }

    public void setLanguages(List<String> languages)
    {
        this.languages = languages;
    }

    public List<String> getCategories()
    {
        return categories;
    }

    public void setCategories(List<String> categories)
    {
        this.categories = categories;
    }

    public List<String> getCountries()
    {
        return countries;
    }

    public void setCountries(List<String> countries)
    {
        this.countries = countries;
    }

    public List<String> getReleaseDate()
    {
        return releaseDate;
    }

    public void setReleaseDate(List<String> releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    public String getMins()
    {
        return mins;
    }

    public void setMins(String mins)
    {
        this.mins = mins;
    }

    public String getIMDbUrl()
    {
        return IMDbUrl;
    }

    public void setIMDbUrl(String IMDbUrl)
    {
        this.IMDbUrl = IMDbUrl;
    }

    public float getRate()
    {
        return rate;
    }

    public void setRate(float rate)
    {
        this.rate = rate;
    }

    public int getRateCount()
    {
        return rateCount;
    }

    public void setRateCount(int rateCount)
    {
        this.rateCount = rateCount;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getNumberOfEpisode()
    {
        return numberOfEpisode;
    }

    public void setNumberOfEpisode(int numberOfEpisode)
    {
        this.numberOfEpisode = numberOfEpisode;
    }

    public String getSigleMins()
    {
        return sigleMins;
    }

    public void setSigleMins(String sigleMins)
    {
        this.sigleMins = sigleMins;
    }



    // 编号
    private long number;
    // 名字
    private String name;
    // 海报地址
    private String imageUrl;
    // 年份
    private int year;
    // 导演
    private List<String> directors;
    // 编剧
    private List<String> writers;
    // 主演
    private List<String> casts;
    // 语言
    private List<String> languages;
    // 类别
    private List<String> categories;
    // 国家
    private List<String> countries;
    // 上映日期
    private List<String> releaseDate;
    // 片长
    private String mins;
    // IMDb链接
    private String IMDbUrl;
    // 评分
    private float rate;
    // 参与评分的人数
    private int rateCount;
    // 剧情简介
    private String description;
    // 集数
    private int numberOfEpisode;
    // 单集时长
    private String sigleMins;


}
