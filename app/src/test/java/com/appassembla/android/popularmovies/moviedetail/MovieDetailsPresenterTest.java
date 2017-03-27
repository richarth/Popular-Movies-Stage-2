package com.appassembla.android.popularmovies.moviedetail;

import com.appassembla.android.popularmovies.data.Movie;
import com.appassembla.android.popularmovies.data.MovieReviewsListing;
import com.appassembla.android.popularmovies.data.MovieTrailersListing;
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

import static io.reactivex.Single.just;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Richard Thompson on 04/02/2017.
 */
@SuppressWarnings({"unused", "DefaultFileTemplate"})
@RunWith(MockitoJUnitRunner.class)
public class MovieDetailsPresenterTest {
    @Mock
    private MovieDetailsView movieDetailsView;
    @Mock
    private MoviesRepository moviesRepository;
    private MovieDetailsPresenter movieDetailsPresenter;
    private int movieId;

    private static final int SELECTED_MOVIE_POSITION = 0;
    private static final int MOVIE_SORT_TYPE = 0;
    private static final int SELECTED_MOVIE_ID = 123;

    private static final Single<MoviesListing> SOME_MOVIES = new StaticMoviesRepository().getMovies(MOVIE_SORT_TYPE);

    private static final Single<Movie> SELECTED_MOVIE_OBSERVABLE = just(SOME_MOVIES.blockingGet().results().get(SELECTED_MOVIE_POSITION));

    private static final Single<MovieReviewsListing> SOME_REVIEWS = new StaticMoviesRepository().getMoviesReviews(SELECTED_MOVIE_ID);

    private static final Single<MovieTrailersListing> SOME_TRAILERS = new StaticMoviesRepository().getMoviesTrailers(SELECTED_MOVIE_ID);

    @BeforeClass
    public static void setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                __ -> Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(
                __ -> Schedulers.trampoline());
    }

    @Before
    public void setUp() {
        movieId = 7;

        movieDetailsPresenter = new MovieDetailsPresenter(movieDetailsView, moviesRepository, movieId);
    }

    @Test
    public void shouldShowMovieDetail() {
        when(moviesRepository.getMovieById(movieId)).thenReturn(SELECTED_MOVIE_OBSERVABLE);

        movieDetailsPresenter.displayMovie();

        verify(movieDetailsView).displayMovieDetails(SELECTED_MOVIE_OBSERVABLE.blockingGet());
    }

    @Test
    public void shouldShowNoMovieDetails() {
        movieDetailsPresenter.displayMovie();

        verify(movieDetailsView, never()).displayMovieDetails(null);
    }

    @Test
    public void shouldShowReviews() {
        when(moviesRepository.getMoviesReviews(SELECTED_MOVIE_ID)).thenReturn(SOME_REVIEWS);

        movieDetailsPresenter.displayReviews();

        verify(movieDetailsView).displayReviews(SOME_REVIEWS.blockingGet());
    }

    @Test
    public void shouldShowNoReviews() {
        movieDetailsPresenter.displayReviews();

        verify(movieDetailsView, never()).displayReviews(null);
    }

    @Test
    public void shouldShowTrailers() {
        when(moviesRepository.getMoviesTrailers(SELECTED_MOVIE_ID)).thenReturn(SOME_TRAILERS);

        movieDetailsPresenter.displayTrailers();

        verify(movieDetailsView).displayTrailers(SOME_TRAILERS.blockingGet());
    }

    @Test
    public void shouldShowNoTrailers() {
        movieDetailsPresenter.displayTrailers();

        verify(movieDetailsView, never()).displayTrailers(null);
    }
}