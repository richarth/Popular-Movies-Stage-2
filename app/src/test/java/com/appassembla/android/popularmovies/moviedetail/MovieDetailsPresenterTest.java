package com.appassembla.android.popularmovies.moviedetail;

import com.appassembla.android.popularmovies.data.MoviesRepository;
import com.appassembla.android.popularmovies.data.StaticMoviesRepository;
import com.appassembla.android.popularmovies.models.Movie;
import com.appassembla.android.popularmovies.models.MovieReviewsListing;
import com.appassembla.android.popularmovies.models.MovieTrailersListing;
import com.appassembla.android.popularmovies.models.MoviesListing;

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
    @SuppressWarnings("CanBeFinal")
    @Mock
    private MovieDetailsView movieDetailsView;
    @SuppressWarnings("CanBeFinal")
    @Mock
    private MoviesRepository moviesRepository;
    private MovieDetailsPresenter movieDetailsPresenter;

    private static final int SELECTED_MOVIE_POSITION = 0;
    private static final int MOVIE_SORT_TYPE = 0;
    private static final int SELECTED_MOVIE_ID = 7;

    private static final Single<MoviesListing> SOME_MOVIES = new StaticMoviesRepository().getMovies(MOVIE_SORT_TYPE);

    private static final Single<Movie> SELECTED_MOVIE_OBSERVABLE = just(SOME_MOVIES.blockingGet().results().get(SELECTED_MOVIE_POSITION));

    private static final Single<MovieReviewsListing> SOME_REVIEWS = new StaticMoviesRepository().getMoviesReviews(SELECTED_MOVIE_ID);

    private static final Single<MovieReviewsListing> NO_REVIEWS_OBSERVABLE = new StaticMoviesRepository().getNoReviews();

    private static final Single<MovieTrailersListing> SOME_TRAILERS = new StaticMoviesRepository().getMoviesTrailers(SELECTED_MOVIE_ID);

    private static final Single<MovieTrailersListing> NO_TRAILERS_OBSERVABLE = new StaticMoviesRepository().getNoTrailers();

    @BeforeClass
    public static void setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                __ -> Schedulers.trampoline());
        RxJavaPlugins.setIoSchedulerHandler(
                __ -> Schedulers.trampoline());
    }

    @Before
    public void setUp() {
        movieDetailsPresenter = new MovieDetailsPresenter(movieDetailsView, moviesRepository, SELECTED_MOVIE_ID);
    }

    @Test
    public void shouldShowMovieDetail() {
        when(moviesRepository.getMovieById(SELECTED_MOVIE_ID)).thenReturn(SELECTED_MOVIE_OBSERVABLE);

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

        verify(movieDetailsView).displayReviews(SOME_REVIEWS.blockingGet().results());
    }

    @Test
    public void shouldShowNoReviews() {
        when(moviesRepository.getMoviesReviews(SELECTED_MOVIE_ID)).thenReturn(NO_REVIEWS_OBSERVABLE);

        movieDetailsPresenter.displayReviews();

        verify(movieDetailsView, never()).displayReviews(null);
    }

    @Test
    public void shouldShowTrailers() {
        when(moviesRepository.getMoviesTrailers(SELECTED_MOVIE_ID)).thenReturn(SOME_TRAILERS);

        movieDetailsPresenter.displayTrailers();

        verify(movieDetailsView).displayTrailers(SOME_TRAILERS.blockingGet().results());
    }

    @Test
    public void shouldShowNoTrailers() {
        when(moviesRepository.getMoviesTrailers(SELECTED_MOVIE_ID)).thenReturn(NO_TRAILERS_OBSERVABLE);

        movieDetailsPresenter.displayTrailers();

        verify(movieDetailsView, never()).displayTrailers(null);
    }
}