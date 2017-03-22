package com.appassembla.android.popularmovies.data;

import com.appassembla.android.popularmovies.BuildConfig;
import com.squareup.moshi.Moshi;

import io.reactivex.Single;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by richard.thompson on 08/02/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
public class WebMoviesRepository implements MoviesRepository {

    private final MovieDBService movieDBService;

    public WebMoviesRepository() {
        Moshi moshi = new Moshi.Builder()
                .add(MovieAdapterFactory.create())
                .build();

        Interceptor clientInterceptor = chain -> {
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder().addQueryParameter("api_key", BuildConfig.MOVIE_DB_API_KEY).build();
            request = request.newBuilder().url(url).build();
            return chain.proceed(request);
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(clientInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();

        movieDBService = retrofit.create(MovieDBService.class);
    }

    @Override
    public Single<MoviesListing> getMovies(int sortType) {
        Single<MoviesListing> moviesData;

        if (sortType == MoviesRepository.TOP_RATED_SORT_TYPE) {
            moviesData = movieDBService.getTopRatedMovies();
        } else {
            moviesData = movieDBService.getPopularMovies();
        }

        return moviesData;
    }

    @Override
    public Single<Movie> getMovieById(int movieId) {
        return movieDBService.getMovieDetails(movieId);
    }
}
