package com.appassembla.android.popularmovies.movielist;

import android.support.annotation.NonNull;
import android.util.Log;

import com.appassembla.android.popularmovies.data.Movie;
import com.appassembla.android.popularmovies.data.MoviesListing;
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
public class MovieListPresenter implements MovieListEvents {

    private final MovieListView movieListView;
    private final MoviesRepository moviesRepository;

    private static final String TAG = "MovieListPresenter";

    private Disposable moviesSubscription;

    public MovieListPresenter(@NonNull MovieListView movieListView, @NonNull MoviesRepository moviesRepository) {
        this.movieListView = movieListView;
        this.moviesRepository = moviesRepository;
    }

    public void displayMovies(int movieListSortType) {
        movieListView.showProgressBar();

        Single<MoviesListing> movies = moviesRepository.getMovies(movieListSortType);

        if (movies != null) {
            moviesSubscription = movies.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::moviesFetched, this::moviesFetchFailure);
        }
    }

    private void moviesFetched(MoviesListing moviesListing) {
        List<Movie> moviesFound = moviesListing.results();

        if (moviesFound.isEmpty()) {
            movieListView.displayNoMoviesMessage();
        } else {
            movieListView.displayMoviesList(moviesFound);
        }

        movieListView.hideProgressBar();
    }

    private void moviesFetchFailure(Throwable throwable) {
        Log.d(TAG, throwable.getMessage());

        movieListView.displayNoMoviesMessage();

        movieListView.hideProgressBar();
    }

    public void movieClicked(int movieId, int adapterPosition) {
         movieListView.displayMovieDetail(movieId, adapterPosition);
    }

    public void cancelSubscriptions() {
        if (moviesSubscription != null) {
            moviesSubscription.dispose();
        }
    }
}
