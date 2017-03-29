package com.appassembla.android.popularmovies.data;

import com.appassembla.android.popularmovies.models.Movie;
import com.appassembla.android.popularmovies.models.MovieReviewsListing;
import com.appassembla.android.popularmovies.models.MovieTrailersListing;
import com.appassembla.android.popularmovies.models.MoviesListing;

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

    @GET("3/movie/{id}/reviews")
    Single<MovieReviewsListing> getMoviesReviews(@Path("id") int movieId);

    @GET("3/movie/{id}/videos")
    Single<MovieTrailersListing> getMoviesTrailers(@Path("id") int movieId);
}
