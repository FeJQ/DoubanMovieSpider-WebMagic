import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MovieService
{
    public void addMovie(Movie movie) throws SQLException, ClassNotFoundException
    {
        DBHelper db=new DBHelper();
        String sql="inert into movie(name,imageUrl,year,mins,IMDbUrl,rate,rateCount,description,numberOfEpisode,sigleMins) values(?);";
        //PreparedStatement statement = connection.prepareStatement(sql);
        //statement.setString(1,ip);//拼接IP
    }


}
