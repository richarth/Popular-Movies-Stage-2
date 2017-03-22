package com.appassembla.android.popularmovies.data;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by Richard Thompson on 04/02/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
@AutoValue
public abstract class Movie {
    public abstract int id();
    @Json(name = "original_title")
    public abstract String name();
    @Json(name = "poster_path")
    public abstract String posterUrl();
    @Json(name = "overview")
    public abstract String plotSynopsis();
    @Json(name = "vote_average")
    public abstract double averageRating();
    @Json(name = "release_date")
    public abstract String releaseDate();
    @Json(name = "backdrop_path")
    public abstract String backdropUrl();

    private static final String IMG_BASE_URL = "http://image.tmdb.org/t/p/";

    public static Movie create(int id, @NonNull String name, @NonNull String posterUrl, @NonNull String plotSynopsis, double averageRating, @NonNull String releaseDate, @NonNull String backdropUrl) {
        return new AutoValue_Movie(id, name, posterUrl, plotSynopsis, averageRating, releaseDate, backdropUrl);
    }

    public static JsonAdapter<Movie> jsonAdapter(Moshi moshi) {
        return new AutoValue_Movie.MoshiJsonAdapter(moshi);
    }

    public String getMovieYear() {
        if (releaseDate() != null  && !releaseDate().trim().isEmpty()) {
            return releaseDate().substring(0, 4);
        }

        return "";
    }

    public String getPosterImgFullUrl(int listWidth) {
        return IMG_BASE_URL + determineListImageWidth(listWidth) + posterUrl();
    }

    public String getHeroImgFullUrl(int detailsViewWidth) {
        return IMG_BASE_URL + determineHeroImageWidth(detailsViewWidth) + backdropUrl();
    }

    public static int determineDesiredSortOrder(int sortSpinnerSelectedPosition) {
        if (sortSpinnerSelectedPosition == 1) {
            return MoviesRepository.TOP_RATED_SORT_TYPE;
        } else {
            return MoviesRepository.POPULAR_SORT_TYPE;
        }
    }

    static String determineListImageWidth(int listWidth) {

        int columnWidth = listWidth / 2;

        if (columnWidth >= 500) {
            return "w780";
        } else if (columnWidth >= 342) {
            return "w500";
        } else if (columnWidth >= 185) {
            return "w342";
        } else if (columnWidth >= 154) {
            return "w185";
        } else if (columnWidth >= 92) {
            return "w154";
        } else {
            return "w92";
        }
    }

    static String determineHeroImageWidth(int detailsViewWidth) {
        if (detailsViewWidth >= 500) {
            return "w780";
        } else if (detailsViewWidth >= 342) {
            return "w500";
        } else if (detailsViewWidth >= 185) {
            return "w342";
        } else if (detailsViewWidth >= 154) {
            return "w185";
        } else if (detailsViewWidth >= 92) {
            return "w154";
        } else {
            return "w92";
        }
    }
}
