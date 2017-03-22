package com.appassembla.android.popularmovies.movielist;

import com.appassembla.android.popularmovies.data.Movie;

import java.util.List;

/**
 * Created by Richard Thompson on 04/02/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
interface MovieListView {
    void displayMoviesList(List<Movie> moviesList);
    void displayNoMoviesMessage();
    void displayMovieDetail(int moviePosition, int adapterPosition);
    void showProgressBar();
    void hideProgressBar();
}
