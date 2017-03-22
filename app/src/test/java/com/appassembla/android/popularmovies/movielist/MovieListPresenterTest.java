package com.appassembla.android.popularmovies.movielist;

import com.appassembla.android.popularmovies.data.MoviesListing;
import com.appassembla.android.popularmovies.data.MoviesRepository;
import com.appassembla.android.popularmovies.data.StaticMoviesRepository;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Richard Thompson on 04/02/2017.
 */
@SuppressWarnings({"unused", "DefaultFileTemplate"})
@RunWith(MockitoJUnitRunner.class)
public class MovieListPresenterTest {

    @Mock
    private MovieListView movieListView;
    @Mock
    private MoviesRepository moviesRepository;
    private MovieListPresenter movieListPresenter;

    private static final int INVALID_SORT_TYPE = 0;

    private static final Single<MoviesListing> SOME_MOVIES_OBSERVABLE = new StaticMoviesRepository().getMovies(INVALID_SORT_TYPE);
    private static final Single<MoviesListing> NO_MOVIES_OBSERVABLE = new StaticMoviesRepository().getNoMovies();

    @BeforeClass
    public static void setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                __ -> Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(
                __ -> Schedulers.trampoline());
    }

    @Before
    public void setUp() {
        movieListPresenter = new MovieListPresenter(movieListView, moviesRepository);
    }

    @Test
    public void shouldShowMoviesList() {
        when(moviesRepository.getMovies(MoviesRepository.POPULAR_SORT_TYPE)).thenReturn(SOME_MOVIES_OBSERVABLE);

        movieListPresenter.displayMovies(MoviesRepository.POPULAR_SORT_TYPE);

        verify(movieListView).displayMoviesList(SOME_MOVIES_OBSERVABLE.blockingGet().results());
    }

    @Test
    public void shouldShowNoMoviesList() {
        when(moviesRepository.getMovies(MoviesRepository.POPULAR_SORT_TYPE)).thenReturn(NO_MOVIES_OBSERVABLE);

        movieListPresenter.displayMovies(MoviesRepository.POPULAR_SORT_TYPE);

        verify(movieListView).displayNoMoviesMessage();
    }

    @Test
    public void shouldShowNoMoviesForInvalidSortType() {
        when(moviesRepository.getMovies(INVALID_SORT_TYPE)).thenReturn(NO_MOVIES_OBSERVABLE);

        movieListPresenter.displayMovies(INVALID_SORT_TYPE);

        verify(movieListView).displayNoMoviesMessage();
    }

    @Test
    public void shouldToggleProgressBar() {
        when(moviesRepository.getMovies(MoviesRepository.POPULAR_SORT_TYPE)).thenReturn(SOME_MOVIES_OBSERVABLE);

        movieListPresenter.displayMovies(MoviesRepository.POPULAR_SORT_TYPE);

        verify(movieListView).showProgressBar();

        verify(movieListView).hideProgressBar();
    }

    @Test
    public void shouldDisplaySelectedMovie() {
        when(moviesRepository.getMovies(MoviesRepository.POPULAR_SORT_TYPE)).thenReturn(SOME_MOVIES_OBSERVABLE);

        int clickedPosition = 1;

        int adapterPosition = 2;

        movieListPresenter.displayMovies(MoviesRepository.POPULAR_SORT_TYPE);

        movieListPresenter.movieClicked(clickedPosition, adapterPosition);

        verify(movieListView).displayMovieDetail(clickedPosition, adapterPosition);
    }
}