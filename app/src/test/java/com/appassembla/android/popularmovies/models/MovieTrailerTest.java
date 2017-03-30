package com.appassembla.android.popularmovies.models;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by richard.thompson on 22/03/2017.
 */
@SuppressWarnings({"unused", "DefaultFileTemplate"})
@RunWith(JUnit4.class)
public class MovieTrailerTest {
    @Test
    public void verifyTrailerJsonConvertsToModel() throws Exception {
        String sampleTrailerJson = "{\"id\":\"58cfc8c09251415a39034879\",\"iso_639_1\":\"en\",\"iso_3166_1\":\"US\",\"key\":\"G0HRx_0fimc\",\"name\":\"Official Trailer #1 [UK]\",\"site\":\"YouTube\",\"size\":1080,\"type\":\"Trailer\"}";

        MovieTrailer sampleMovieTrailer = MovieTrailer.create("58cfc8c09251415a39034879", "G0HRx_0fimc", "Official Trailer #1 [UK]", "YouTube");

        Moshi moshi = new Moshi.Builder()
                .add(MovieTrailerAdapterFactory.create())
                .build();

        JsonAdapter<MovieTrailer> adapter = moshi.adapter(MovieTrailer.class).lenient();

        assertEquals(adapter.fromJson(sampleTrailerJson), sampleMovieTrailer);
    }

    @Test
    public void verifyTrailersJsonConvertsToModelsList() throws Exception {
        String sampleTrailersJson = "{\"id\":263115,\"results\":[{\"id\":\"58cfc8499251415a61037481\",\"iso_639_1\":\"en\",\"iso_3166_1\":\"US\",\"key\":\"XaE_9pfybL4\",\"name\":\"Official Trailer #2 [UK]\",\"site\":\"YouTube\",\"size\":1080,\"type\":\"Trailer\"},{\"id\":\"58cfc8c09251415a39034879\",\"iso_639_1\":\"en\",\"iso_3166_1\":\"US\",\"key\":\"G0HRx_0fimc\",\"name\":\"Official Trailer #1 [UK]\",\"site\":\"YouTube\",\"size\":1080,\"type\":\"Trailer\"},{\"id\":\"58cfc820c3a36850fb033208\",\"iso_639_1\":\"en\",\"iso_3166_1\":\"US\",\"key\":\"RH3OxVFvTeg\",\"name\":\"Official Trailer #2\",\"site\":\"YouTube\",\"size\":1080,\"type\":\"Trailer\"}]}";

        MovieTrailer sampleMovieTrailer1 = MovieTrailer.create("58cfc8499251415a61037481", "XaE_9pfybL4", "Official Trailer #2 [UK]", "YouTube");
        MovieTrailer sampleMovieTrailer2 = MovieTrailer.create("58cfc8c09251415a39034879", "G0HRx_0fimc", "Official Trailer #1 [UK]", "YouTube");
        MovieTrailer sampleMovieTrailer3 = MovieTrailer.create("58cfc820c3a36850fb033208", "RH3OxVFvTeg", "Official Trailer #2", "YouTube");

        Moshi moshi = new Moshi.Builder()
                .add(MovieTrailerAdapterFactory.create())
                .build();

        JsonAdapter<MovieTrailersListing> adapter = moshi.adapter(MovieTrailersListing.class).lenient();

        MovieTrailersListing movieTrailersListing = adapter.fromJson(sampleTrailersJson);

        List<MovieTrailer> movieTrailers = movieTrailersListing.results();

        assertEquals(movieTrailers.size(), 3);

        assertEquals(movieTrailers.get(0), sampleMovieTrailer1);
        assertEquals(movieTrailers.get(1), sampleMovieTrailer2);
        assertEquals(movieTrailers.get(2), sampleMovieTrailer3);
    }
}