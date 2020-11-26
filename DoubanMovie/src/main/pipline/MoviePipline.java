import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

public class MoviePipline implements Pipeline
{
    @Override
    public void process(ResultItems resultItems, Task task)
    {
        for(Map.Entry<String,Object> entry :resultItems.getAll().entrySet())
        {
            if(entry.getValue() instanceof Movie )
            {
                Movie movie = (Movie) entry.getValue();
                MovieService movieService=new MovieService();
                movieService.addMovie(movie);
            }

        }
    }
}
