package com.appassembla.android.popularmovies.models;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

/**
 * Created by richard.thompson on 23/03/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
@AutoValue
public abstract class MovieTrailersListing {
    public abstract List<MovieTrailer> results();

    public static JsonAdapter<MovieTrailersListing> jsonAdapter(Moshi moshi) {
        return new AutoValue_MovieTrailersListing.MoshiJsonAdapter(moshi);
    }
}
