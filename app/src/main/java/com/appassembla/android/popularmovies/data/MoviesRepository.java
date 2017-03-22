package com.appassembla.android.popularmovies.data;

import io.reactivex.Single;

/**
 * Created by Richard Thompson on 04/02/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
public interface MoviesRepository {
    int POPULAR_SORT_TYPE = 1;
    int TOP_RATED_SORT_TYPE = 2;

    Single<MoviesListing> getMovies(int sortType);

    Single<Movie> getMovieById(int movieId);
}
