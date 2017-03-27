package com.appassembla.android.popularmovies.data;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

/**
 * Created by richard.thompson on 23/03/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
@AutoValue
public abstract class MovieReviewsListing {
    public abstract List<MovieReview> results();

    public static JsonAdapter<MovieReviewsListing> jsonAdapter(Moshi moshi) {
        return new AutoValue_MovieReviewsListing.MoshiJsonAdapter(moshi);
    }
}
