package com.appassembla.android.popularmovies.data;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

/**
 * Created by richardthompson on 10/02/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
@AutoValue
public abstract class MoviesListing {
    public abstract List<Movie> results();

    public static JsonAdapter<MoviesListing> jsonAdapter(Moshi moshi) {
        return new AutoValue_MoviesListing.MoshiJsonAdapter(moshi);
    }
}
