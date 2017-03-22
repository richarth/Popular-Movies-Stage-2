package com.appassembla.android.popularmovies.data;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import io.reactivex.Single;

/**
 * Created by Richard Thompson on 04/02/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
public class StaticMoviesRepository implements MoviesRepository {

    private final MoviesListing moviesListing;
    private final List<Movie> movies;
    private final MoviesListing emptyMoviesListing;

    public StaticMoviesRepository() {
        Movie movie7 = Movie.create(7, "Movie 7", "http://i.imgur.com/DvpvklR.png", "Movie 7 is about Jedi", 0, "1979-05-04", "http://i.imgur.com/DvpvklR.png");

        Movie movie4 = Movie.create(4, "Movie 4", "http://i.imgur.com/DvpvklR.png", "Movie 4 is about an archaeologist", 0, "1983-07-04", "http://i.imgur.com/DvpvklR.png");

        Movie movie8 = Movie.create(8, "Movie 8", "http://i.imgur.com/DvpvklR.png", "Movie 8 is about an alien", 0, "1988-12-25", "http://i.imgur.com/DvpvklR.png");

        movies = Arrays.asList(movie7, movie4, movie8);

        moviesListing = new MoviesListing() {
            @Override
            public List<Movie> results() {
                return movies;
            }
        };

        emptyMoviesListing = new MoviesListing() {
            @Override
            public List<Movie> results() {
                return Collections.emptyList();
            }
        };
    }

    @Override
    @NonNull
    public Single<MoviesListing> getMovies(int sortType) {
        return Single.just(moviesListing);
    }

    @NonNull
    public Single<MoviesListing> getNoMovies() {
        return Single.just(emptyMoviesListing);
    }

    @SuppressLint("NewApi")
    @Override
    public Single<Movie> getMovieById(final int movieId) {

        Movie selectedMovie = null;

        Optional<Movie> firstMovieInList = movies.stream().filter(m -> m.id() == movieId).findFirst();

        if (firstMovieInList.isPresent()) {
            selectedMovie = firstMovieInList.get();
        }

        if (selectedMovie != null) {
            return Single.just(selectedMovie);
        } else {
            return Single.error(new NullPointerException());
        }
    }
}
