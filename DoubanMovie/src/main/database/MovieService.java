import org.slf4j.impl.StaticMarkerBinder;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Formatter;

public class MovieService
{
    public boolean addMovie(Movie movie)
    {
        String sql = "";
        StringBuilder sb = null;
        Formatter fmt = null;
        UtilString utilString = new UtilString();

        sb = new StringBuilder();
        fmt = new Formatter(sb);
        fmt.format("insert into movie(name,imageUrl,year,mins,IMDbUrl,rate,rateCount,description,numberOfEpisode,sigleMins) values('%s','%s',%d,'%s','%s',%f,%d,'%s',%d,'%s');\n",
                utilString.transferredMeaning(movie.getName()),
                utilString.transferredMeaning(movie.getImageUrl()),
                movie.getYear(),
                utilString.transferredMeaning(movie.getMins()),
                utilString.transferredMeaning(movie.getIMDbUrl()),
                movie.getRate(),
                movie.getRateCount(),
                utilString.transferredMeaning(movie.getDescription()),
                movie.getNumberOfEpisode(),
                utilString.transferredMeaning(movie.getSigleMins())
        );
        sql += sb.toString();
        sql += "set @movieID=LAST_INSERT_ID();\n";

        for (int i = 0; i < movie.getDirectors().size(); i++)
            sql += ("insert into director(name,movieID) values('" + utilString.transferredMeaning(movie.getDirectors().get(i)) + "',@movieID);\n");
        for (int i = 0; i < movie.getWriters().size(); i++)
            sql += ("insert into writer(name,movieID) values('" + utilString.transferredMeaning(movie.getWriters().get(i)) + "',@movieID);\n");
        for (int i = 0; i < movie.getCasts().size(); i++)
            sql += ("insert into casts(name,movieID) values('" + utilString.transferredMeaning(movie.getCasts().get(i)) + "',@movieID);\n");
        for (int i = 0; i < movie.getLanguages().size(); i++)
            sql += ("insert into language(name,movieID) values('" + utilString.transferredMeaning(movie.getLanguages().get(i)) + "',@movieID);\n");
        for (int i = 0; i < movie.getCategories().size(); i++)
            sql += ("insert into category(name,movieID) values('" + utilString.transferredMeaning(movie.getCategories().get(i)) + "',@movieID);\n");
        for (int i = 0; i < movie.getCountries().size(); i++)
            sql += ("insert into country(name,movieID) values('" + utilString.transferredMeaning(movie.getCountries().get(i)) + "',@movieID);\n");
        for (int i = 0; i < movie.getReleaseDate().size(); i++)
            sql += ("insert into releasedate(date_region,movieID) values('" + utilString.transferredMeaning(movie.getReleaseDate().get(i)) + "',@movieID);\n");

        //boolean result = DBHelper.updateBatch(sql);
        int result=DBHelper.update(sql);
        return result>0 ;
    }
}
