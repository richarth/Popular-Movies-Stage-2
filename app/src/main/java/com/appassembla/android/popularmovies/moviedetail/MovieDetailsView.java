package com.appassembla.android.popularmovies.moviedetail;

import com.appassembla.android.popularmovies.data.Movie;

/**
 * Created by Richard Thompson on 04/02/2017.
 */
@SuppressWarnings("DefaultFileTemplate")
interface MovieDetailsView {
    void displayMovieDetails(Movie selectedMovie);
    void hideSelectMovieMessage();
}
