package com.appassembla.android.popularmovies.data;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by richard.thompson on 08/02/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
interface MovieDBService {
    @GET("3/movie/popular")
    Single<MoviesListing> getPopularMovies();

    @GET("3/movie/top_rated")
    Single<MoviesListing> getTopRatedMovies();

    @GET("3/movie/{id}")
    Single<Movie> getMovieDetails(@Path("id") int movieId);
}
