package com.appassembla.android.popularmovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.appassembla.android.popularmovies.models.Movie;
import com.appassembla.android.popularmovies.models.MovieReviewsListing;
import com.appassembla.android.popularmovies.models.MovieTrailer;
import com.appassembla.android.popularmovies.models.MovieTrailersListing;
import com.appassembla.android.popularmovies.models.MoviesListing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by richard.thompson on 05/04/2017.
 */

public class DBMoviesRepository implements MoviesRepository {

    private Context context;

    public static final String[] MOVIE_DATA_PROJECTION = {
            MovieContract.MovieEntry.COLUMN_MOVIE_ID,
            MovieContract.MovieEntry.COLUMN_NAME,
    };

    public DBMoviesRepository(Context context)
    {
        this.context = context;
    }

    @Override
    public Single<MoviesListing> getMovies(int sortType) {
        if (sortType != FAVOURITES_SORT_TYPE) {
            throw new UnsupportedOperationException("Only favourites are stored in the DB");
        }

        Cursor mCursor = context.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                MOVIE_DATA_PROJECTION,
                null,
                null,
                null);

        // if we didn't find any favourited movies then return an empty movie list
        if (mCursor == null || (mCursor.getCount() < 1)) {
            MoviesListing emptyMoviesListing = new MoviesListing() {
                @Override
                public List<Movie> results() {
                    return Collections.emptyList();
                }
            };

            return Single.just(emptyMoviesListing);
        }

        List<Movie> moviesList = new ArrayList<>(mCursor.getCount());

        //mCursor.moveToFirst();

        while (mCursor.moveToNext()) {
            Movie movie = Movie.create(mCursor.getInt(MovieContract.MovieEntry.INDEX_MOVIE_ID), mCursor.getString(MovieContract.MovieEntry.INDEX_MOVIE_NAME), "", "", 0, "", "");

            moviesList.add(movie);
        }

        MoviesListing moviesListing = new MoviesListing() {
            @Override
            public List<Movie> results() {
                return moviesList;
            }
        };

        return Single.just(moviesListing);
    }

    @Override
    public Single<Movie> getMovieById(int movieId) {

        Cursor mCursor = context.getContentResolver().query(
                MovieContract.MovieEntry.buildMovieUriWithId(movieId),
                MOVIE_DATA_PROJECTION,
                null,
                null,
                null);

        // if we didn't find the movie in the user's favourites return null
        if (mCursor == null || (mCursor.getCount() < 1)) {
            return null;
        }

        mCursor.moveToFirst();

        Movie movie = Movie.create(mCursor.getInt(MovieContract.MovieEntry.INDEX_MOVIE_ID), mCursor.getString(MovieContract.MovieEntry.INDEX_MOVIE_NAME), "", "", 0, "", "");

        return Single.just(movie);
    }

    @Override
    public Single<MovieReviewsListing> getMoviesReviews(int movieId) {
        throw new UnsupportedOperationException("getMoviesReviews should not be used");
    }

    @Override
    public Single<MovieTrailersListing> getMoviesTrailers(int movieId) {
        throw new UnsupportedOperationException("getMoviesTrailers should not be used");
    }

    public Single<Uri> addMovieToDB(Movie movie) {
        ContentValues mNewValues = new ContentValues();

        mNewValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movie.id());
        mNewValues.put(MovieContract.MovieEntry.COLUMN_NAME, movie.name());

        Uri movieUri = context.getContentResolver().insert(
                MovieContract.MovieEntry.CONTENT_URI,
                mNewValues
        );

        return Single.just(movieUri);
    }
}
