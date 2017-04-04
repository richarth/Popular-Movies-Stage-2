package com.appassembla.android.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by richard.thompson on 03/04/2017.
 */

public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.appassembla.android.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";

    /* Inner class that defines the table contents of the movies table */
    public static final class MovieEntry implements BaseColumns {

        /* The base CONTENT_URI used to query the movies table from the content provider */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIES)
                .build();

        /* Used internally as the name of our movies table. */
        public static final String TABLE_NAME = "movies";

        /* Movie ID as returned by API */
        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static final String COLUMN_NAME = "name";

        /**
         * Builds a URI that adds the movie ID to the end of the movie content URI path.
         * This is used to query details about a single movie entry by ID. This is what we
         * use for the detail view query.
         *
         * @param movieDbId the movie's ID in the DB
         * @return Uri to query details about a single movie entry
         */
        public static Uri buildMovieUriWithId(int movieDbId) {
            return CONTENT_URI.buildUpon()
                    .appendPath(Integer.toString(movieDbId))
                    .build();
        }
    }
}
