package com.appassembla.android.popularmovies.moviedetail;

import com.appassembla.android.popularmovies.data.Movie;
import com.appassembla.android.popularmovies.data.MovieReview;
import com.appassembla.android.popularmovies.data.MovieReviewsListing;
import com.appassembla.android.popularmovies.data.MovieTrailer;
import com.appassembla.android.popularmovies.data.MovieTrailersListing;

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
    void hideTrailers();
    void hideReviews();
}
