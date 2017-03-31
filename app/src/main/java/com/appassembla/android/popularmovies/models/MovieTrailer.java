package com.appassembla.android.popularmovies.models;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by richard.thompson on 22/03/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
@AutoValue
public abstract class MovieTrailer {
    public abstract String id();
    public abstract String key();
    public abstract String name();
    public abstract String site();

    public static final String YOUTUBE_VIDEO_BASE_URL = "https://www.youtube.com/watch";

    public static MovieTrailer create(@NonNull String id, @NonNull String key, @NonNull String name, @NonNull String site) {
        return new AutoValue_MovieTrailer(id, key, name, site);
    }

    public static JsonAdapter<MovieTrailer> jsonAdapter(Moshi moshi) {
        return new AutoValue_MovieTrailer.MoshiJsonAdapter(moshi);
    }

    public Uri generateTrailerUrl() {
        Uri trailerUri = null;

        switch (site()) {
            case "YouTube":
                trailerUri = Uri.parse(YOUTUBE_VIDEO_BASE_URL).buildUpon().appendQueryParameter("v", key()).build();
                break;
        }

        return trailerUri;
    }
}
