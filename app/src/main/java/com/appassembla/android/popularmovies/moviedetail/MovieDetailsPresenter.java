package com.appassembla.android.popularmovies.moviedetail;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.appassembla.android.popularmovies.data.DBMoviesRepository;
import com.appassembla.android.popularmovies.data.MoviesRepository;
import com.appassembla.android.popularmovies.models.Movie;
import com.appassembla.android.popularmovies.models.MovieReview;
import com.appassembla.android.popularmovies.models.MovieReviewsListing;
import com.appassembla.android.popularmovies.models.MovieTrailer;
import com.appassembla.android.popularmovies.models.MovieTrailersListing;

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
    private Disposable addMovieSubscription;
    private Disposable removeMovieSubscription;
    private Disposable getMovieSubscription;

    private Context context;

    private DBMoviesRepository moviesDBRepository;

    private Movie selectedMovie;

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
                    .subscribe(this::movieFetched, this::subscriptionFailure);
        }
    }

    private void movieFetched(Movie selectedMovie) {
        this.selectedMovie = selectedMovie;

        movieDetailsView.hideSelectMovieMessage();

        movieDetailsView.displayMovieDetails(selectedMovie);
    }

    public void cancelSubscriptions() {
        if (movieSubscription != null) {
            movieSubscription.dispose();
        }

        if (trailersSubscription != null) {
            trailersSubscription.dispose();
        }

        if (addMovieSubscription != null) {
            addMovieSubscription.dispose();
        }

        if (removeMovieSubscription != null) {
            removeMovieSubscription.dispose();
        }

        if (getMovieSubscription != null) {
            getMovieSubscription.dispose();
        }
    }

    public void displayReviews() {
        Single<MovieReviewsListing> selectedMoviesReviews = moviesRepository.getMoviesReviews(selectedMovieId);

        if (selectedMoviesReviews != null) {
            trailersSubscription = selectedMoviesReviews.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::reviewsFetched, this::subscriptionFailure);
        }
    }

    private void reviewsFetched(MovieReviewsListing movieReviewsListing) {
        List<MovieReview> moviesReviews = movieReviewsListing.results();

        if (!moviesReviews.isEmpty()) {
            movieDetailsView.displayReviews(moviesReviews);
        }
    }

    public void displayTrailers() {
        Single<MovieTrailersListing> selectedMoviesTrailers = moviesRepository.getMoviesTrailers(selectedMovieId);

        if (selectedMoviesTrailers != null) {
            trailersSubscription = selectedMoviesTrailers.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::trailersFetched, this::subscriptionFailure);
        }
    }

    private void trailersFetched(MovieTrailersListing movieTrailersListing) {
        List<MovieTrailer> moviesTrailers = movieTrailersListing.results();

        if (!moviesTrailers.isEmpty()) {
            movieDetailsView.displayTrailers(moviesTrailers);
        }
    }

    public void trailerClicked(Uri trailerUri) {
        movieDetailsView.displayTrailer(trailerUri);
    }

    public void addMovieToFavourites(Context context) {
        moviesDBRepository = new DBMoviesRepository(context);
        Single<Movie> movieObservable = moviesDBRepository.getMovieById(selectedMovieId);

        if (movieObservable != null) {
            getMovieSubscription = movieObservable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::toggleFavouriteState, this::subscriptionFailure);
        } else {
            toggleFavouriteState(null);
        }
    }

    public void isMovieFavourite(Context context) {
        this.context = context;

        moviesDBRepository = new DBMoviesRepository(context);
        Single<Movie> getMovieObservable = moviesDBRepository.getMovieById(selectedMovieId);

        if (getMovieObservable != null) {
            addMovieSubscription = getMovieObservable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::movieFetchedFromDB, this::subscriptionFailure);
        }
    }

    private void movieFetchedFromDB(Movie movie) {
        if (movie != null) {
            movieDetailsView.displayMovieAsFavourite();
        }

        context = null;

        moviesDBRepository = null;
    }

    private void toggleFavouriteState(Movie favourite) {
        // if the movie isn't in the DB it hasn't been favourited so add it
        if (favourite == null) {
            Single<Uri> movieAddObservable = moviesDBRepository.addMovieToDB(selectedMovie);

            if (movieAddObservable != null) {
                addMovieSubscription = movieAddObservable.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::movieAdded, this::subscriptionFailure);
            }
        } else {
            // the user has pressed the favourite button after it's favourited so unfavourite it
            Single<Integer> movieRemoveObservable = moviesDBRepository.removeMovieFromDB(favourite);

            if (movieRemoveObservable != null) {
                removeMovieSubscription = movieRemoveObservable.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::movieRemoved, this::subscriptionFailure);
            }
        }
    }

    private void movieAdded(Uri uri) {
        movieDetailsView.displayMovieAsFavourite();

        context = null;

        moviesDBRepository = null;
    }

    private void movieRemoved(int numRemovals) {
        movieDetailsView.removeMovieAsFavourite();

        context = null;

        moviesDBRepository = null;
    }

    private void subscriptionFailure(Throwable throwable) {
        Log.d(TAG, throwable.getMessage());

        context = null;

        moviesDBRepository = null;
    }
}
