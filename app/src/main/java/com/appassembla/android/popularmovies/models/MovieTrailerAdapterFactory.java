package com.appassembla.android.popularmovies.models;

import com.ryanharter.auto.value.moshi.MoshiAdapterFactory;
import com.squareup.moshi.JsonAdapter;

/**
 * Created by richard.thompson on 22/03/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
@MoshiAdapterFactory
abstract class MovieTrailerAdapterFactory implements JsonAdapter.Factory {
    public static JsonAdapter.Factory create() {
        return new AutoValueMoshi_MovieTrailerAdapterFactory();
    }
}
