package com.appassembla.android.popularmovies.moviedetail;

import android.support.annotation.NonNull;
import android.util.Log;

import com.appassembla.android.popularmovies.data.Movie;
import com.appassembla.android.popularmovies.data.MoviesRepository;

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
    }
}
