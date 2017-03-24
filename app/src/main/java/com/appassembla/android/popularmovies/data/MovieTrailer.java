package com.appassembla.android.popularmovies.data;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by richard.thompson on 22/03/2017.
 */

@AutoValue
public abstract class MovieTrailer {
    public abstract String key();
    public abstract String name();
    public abstract String site();

    public static MovieTrailer create(@NonNull String key, @NonNull String name, @NonNull String site) {
        return new AutoValue_MovieTrailer(key, name, site);
    }

    public static JsonAdapter<MovieTrailer> jsonAdapter(Moshi moshi) {
        return new AutoValue_MovieTrailer.MoshiJsonAdapter(moshi);
    }
}
