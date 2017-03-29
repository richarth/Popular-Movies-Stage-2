package com.appassembla.android.popularmovies.models;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by richard.thompson on 23/03/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
@AutoValue
public abstract class MovieReview {
    public abstract String author();
    public abstract String content();
    public abstract String url();

    public static MovieReview create(@NonNull String author, @NonNull String content, @NonNull String url) {
        return new AutoValue_MovieReview(author, content, url);
    }

    public static JsonAdapter<MovieReview> jsonAdapter(Moshi moshi) {
        return new AutoValue_MovieReview.MoshiJsonAdapter(moshi);
    }
}
