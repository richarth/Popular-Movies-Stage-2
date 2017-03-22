package com.appassembla.android.popularmovies.data;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * Created by richard.thompson on 08/02/2017.
 */
@SuppressWarnings({"unused", "DefaultFileTemplate"})
@RunWith(JUnit4.class)
public class WebMoviesRepositoryTest {
    @Test
    public void verifyJsonConvertsToModel() throws Exception {
        String sampleMovieJson = "{\"adult\":false,\"backdrop_path\":\"/lubzBMQLLmG88CLQ4F3TxZr2Q7N.jpg\",\"belongs_to_collection\":{\"id\":427084,\"name\":\"The Secret Life of Pets Collection\",\"poster_path\":\"/aDNbXvuRiuYxk8qCwXNQQ7UEHau.jpg\",\"backdrop_path\":null},\"budget\":75000000,\"genres\":[{\"id\":12,\"name\":\"Adventure\"},{\"id\":16,\"name\":\"Animation\"},{\"id\":35,\"name\":\"Comedy\"},{\"id\":10751,\"name\":\"Family\"}],\"homepage\":\"http://www.thesecretlifeofpets.com/\",\"id\":328111,\"imdb_id\":\"tt2709768\",\"original_language\":\"en\",\"original_title\":\"The Secret Life of Pets\",\"overview\":\"The quiet life of a terrier named Max is upended when his owner takes in Duke, a stray whom Max instantly dislikes.\",\"popularity\":129.924005,\"poster_path\":\"/WLQN5aiQG8wc9SeKwixW7pAR8K.jpg\",\"production_companies\":[{\"name\":\"Universal Pictures\",\"id\":33},{\"name\":\"Dentsu\",\"id\":6452},{\"name\":\"Illumination Entertainment\",\"id\":6704}],\"production_countries\":[{\"iso_3166_1\":\"US\",\"name\":\"United States of America\"}],\"release_date\":\"2016-06-18\",\"revenue\":874333497,\"runtime\":87,\"spoken_languages\":[{\"iso_639_1\":\"en\",\"name\":\"English\"}],\"status\":\"Released\",\"tagline\":\"Think this is what they do all day?\",\"title\":\"The Secret Life of Pets\",\"video\":false,\"vote_average\":5.8,\"vote_count\":2078}";

        Movie sampleMovie = Movie.create(328111, "The Secret Life of Pets", "/WLQN5aiQG8wc9SeKwixW7pAR8K.jpg", "The quiet life of a terrier named Max is upended when his owner takes in Duke, a stray whom Max instantly dislikes.", 5.8, "2016-06-18", "/lubzBMQLLmG88CLQ4F3TxZr2Q7N.jpg");

        Moshi moshi = new Moshi.Builder()
                .add(MovieAdapterFactory.create())
                .build();

        JsonAdapter<Movie> adapter = moshi.adapter(Movie.class).lenient();

        assertEquals(adapter.fromJson(sampleMovieJson), sampleMovie);
    }
}