package com.appassembla.android.popularmovies.moviedetail;

import android.support.annotation.NonNull;
import android.util.Log;

import com.appassembla.android.popularmovies.data.Movie;
import com.appassembla.android.popularmovies.data.MovieReview;
import com.appassembla.android.popularmovies.data.MovieReviewsListing;
import com.appassembla.android.popularmovies.data.MovieTrailer;
import com.appassembla.android.popularmovies.data.MovieTrailersListing;
import com.appassembla.android.popularmovies.data.MoviesRepository;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Richard Thompson on 04/02/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
class MovieDetailsPresenter {
    private final MovieDetailsView movieDetailsView;
    private final MoviesRepository moviesRepository;
    private final int selectedMovieId;

    private final static String TAG = "MovieDetailsPresenter";

    private Disposable movieSubscription;
    private Disposable trailersSubscription;

    public MovieDetailsPresenter(@NonNull MovieDetailsView movieDetailsView, @NonNull MoviesRepository moviesRepository, int selectedMovieId) {
        this.movieDetailsView = movieDetailsView;
        this.moviesRepository = moviesRepository;
        this.selectedMovieId = selectedMovieId;
    }

    public void displayMovie() {
        Single<Movie> selectedMovie = moviesRepository.getMovieById(selectedMovieId);

        if (selectedMovie != null) {
            movieSubscription = selectedMovie.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::movieFetched, this::movieFetchFailure);
        }
    }

    private void movieFetched(Movie selectedMovie) {
        movieDetailsView.hideSelectMovieMessage();

        movieDetailsView.displayMovieDetails(selectedMovie);
    }

    private void movieFetchFailure(Throwable throwable) {
        Log.d(TAG, throwable.getMessage());
    }

    public void cancelSubscriptions() {
        if (movieSubscription != null) {
            movieSubscription.dispose();
        }

        if (trailersSubscription != null) {
            trailersSubscription.dispose();
        }
    }

    public void displayReviews() {
        Single<MovieReviewsListing> selectedMoviesReviews = moviesRepository.getMoviesReviews(selectedMovieId);

        if (selectedMoviesReviews != null) {
            trailersSubscription = selectedMoviesReviews.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::reviewsFetched, this::reviewFetchFailure);
        }
    }

    private void reviewsFetched(MovieReviewsListing movieReviewsListing) {
        List<MovieReview> moviesReviews = movieReviewsListing.results();

        if (!moviesReviews.isEmpty()) {
            movieDetailsView.displayReviews(moviesReviews);
        } else {
            movieDetailsView.hideReviews();
        }
    }

    private void reviewFetchFailure(Throwable throwable) {
        Log.d(TAG, throwable.getMessage());

        movieDetailsView.hideReviews();
    }

    public void displayTrailers() {
        Single<MovieTrailersListing> selectedMoviesTrailers = moviesRepository.getMoviesTrailers(selectedMovieId);

        if (selectedMoviesTrailers != null) {
            trailersSubscription = selectedMoviesTrailers.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::trailersFetched, this::trailerFetchFailure);
        }
    }

    private void trailersFetched(MovieTrailersListing movieTrailersListing) {
        List<MovieTrailer> moviesTrailers = movieTrailersListing.results();

        if (!moviesTrailers.isEmpty()) {
            movieDetailsView.displayTrailers(moviesTrailers);
        } else {
            movieDetailsView.hideTrailers();
        }
    }

    private void trailerFetchFailure(Throwable throwable) {
        Log.d(TAG, throwable.getMessage());

        movieDetailsView.hideTrailers();
    }
}
