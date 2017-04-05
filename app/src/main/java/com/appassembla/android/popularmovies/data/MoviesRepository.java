package com.appassembla.android.popularmovies.data;

import com.appassembla.android.popularmovies.models.Movie;
import com.appassembla.android.popularmovies.models.MovieReviewsListing;
import com.appassembla.android.popularmovies.models.MovieTrailersListing;
import com.appassembla.android.popularmovies.models.MoviesListing;

import io.reactivex.Single;

/**
 * Created by Richard Thompson on 04/02/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
public interface MoviesRepository {
    int POPULAR_SORT_TYPE = 1;
    int TOP_RATED_SORT_TYPE = 2;
    int FAVOURITES_SORT_TYPE = 3;

    Single<MoviesListing> getMovies(int sortType);

    Single<Movie> getMovieById(int movieId);

    Single<MovieReviewsListing> getMoviesReviews(int movieId);

    Single<MovieTrailersListing> getMoviesTrailers(int movieId);
}
