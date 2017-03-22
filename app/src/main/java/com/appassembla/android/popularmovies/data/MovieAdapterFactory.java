package com.appassembla.android.popularmovies.data;

import com.ryanharter.auto.value.moshi.MoshiAdapterFactory;
import com.squareup.moshi.JsonAdapter;

/**
 * Created by Richard Thompson on 09/02/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
@MoshiAdapterFactory
abstract class MovieAdapterFactory implements JsonAdapter.Factory {
    public static JsonAdapter.Factory create() {
        return new AutoValueMoshi_MovieAdapterFactory();
    }
}
