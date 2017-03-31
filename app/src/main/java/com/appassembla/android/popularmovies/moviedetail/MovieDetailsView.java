package com.appassembla.android.popularmovies.moviedetail;

import android.net.Uri;

import com.appassembla.android.popularmovies.models.Movie;
import com.appassembla.android.popularmovies.models.MovieReview;
import com.appassembla.android.popularmovies.models.MovieTrailer;

import java.util.List;

/**
 * Created by Richard Thompson on 04/02/2017.
 */
@SuppressWarnings("DefaultFileTemplate")
interface MovieDetailsView {
    void displayMovieDetails(Movie selectedMovie);
    void hideSelectMovieMessage();
    void displayTrailers(List<MovieTrailer> moviesTrailersList);
    void displayReviews(List<MovieReview> moviesReviewsList);
    void displayTrailer(Uri trailerUri);
}
