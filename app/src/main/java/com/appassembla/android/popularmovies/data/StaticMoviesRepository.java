package com.appassembla.android.popularmovies.data;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.appassembla.android.popularmovies.models.Movie;
import com.appassembla.android.popularmovies.models.MovieReview;
import com.appassembla.android.popularmovies.models.MovieReviewsListing;
import com.appassembla.android.popularmovies.models.MovieTrailer;
import com.appassembla.android.popularmovies.models.MovieTrailersListing;
import com.appassembla.android.popularmovies.models.MoviesListing;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import io.reactivex.Single;

/**
 * Created by Richard Thompson on 04/02/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
public class StaticMoviesRepository implements MoviesRepository {

    private final MoviesListing moviesListing;
    private final List<Movie> movies;
    private final MoviesListing emptyMoviesListing;

    private final MovieReviewsListing moviesReviewsListing;
    private final List<MovieReview> moviesReviews;
    private final MovieReviewsListing emptyReviewsListing;

    private final MovieTrailersListing moviesTrailersListing;
    private final List<MovieTrailer> moviesTrailers;
    private final MovieTrailersListing emptyTrailersListing;

    public StaticMoviesRepository() {
        Movie movie7 = Movie.create(7, "Movie 7", "http://i.imgur.com/DvpvklR.png", "Movie 7 is about Jedi", 0, "1979-05-04", "http://i.imgur.com/DvpvklR.png");

        Movie movie4 = Movie.create(4, "Movie 4", "http://i.imgur.com/DvpvklR.png", "Movie 4 is about an archaeologist", 0, "1983-07-04", "http://i.imgur.com/DvpvklR.png");

        Movie movie8 = Movie.create(8, "Movie 8", "http://i.imgur.com/DvpvklR.png", "Movie 8 is about an alien", 0, "1988-12-25", "http://i.imgur.com/DvpvklR.png");

        movies = Arrays.asList(movie7, movie4, movie8);

        moviesListing = new MoviesListing() {
            @Override
            public List<Movie> results() {
                return movies;
            }
        };

        emptyMoviesListing = new MoviesListing() {
            @Override
            public List<Movie> results() {
                return Collections.emptyList();
            }
        };

        MovieReview sampleMovieReview1 = MovieReview.create("anythingbutfifi", "**LOGAN REVIEW: THE WOLVERINE GETS A SUPER SEND-OFF**\\n\\n\"Owing to its agitated hero’s misfortunes through the ages, this is a film that’s acutely aware of the dangers of emotional exploitation, and it spares its audience a similar fate. With Logan, Mangold and co-writer Scott Frank tell the definitive story of the Wolverine, in an involving and deeply satisfying series finale. It shows the fate of mutants when age starts to weary them, with stakes that feel real, and empathy that’s earned.\"\\n\\nREAD THE FULL REVIEW AT SBS MOVIES: http://www.sbs.com.au/movies/review/logan-review-wolverine-gets-super-send", "https://www.themoviedb.org/review/58bbf107c3a368666b032be5");
        MovieReview sampleMovieReview2 = MovieReview.create("Movie Queen41", "There may be some fine performances in this movie, but I honestly think the critics overrated this latest entry in the X-Men saga. The performances of Wolverine and Prof. X by Hugh Jackman and Patrick Stewart are extraordinary. They create a believable and loving father-son bond between their characters, with Logan caring for the ninety year old leader of the X-Men after a horrible event occurs at the Xavier school the year before. Stephen Merchant takes over the role of Caliban from Tómas Lemarquis, who played the character in X-Men Apocalypse, and transforms him into an ally to Logan and Charlies. Merchant is quite good in the role. But what really dragged down the movie is its nihilism. The other X-Men are completely missing from the film and mutants have been wiped out almost completely. A sense of doom and hopelessness looms over the film. This movie completely upends the warm and hopeful epilogue of Days of Future Past, where the X-Men were restored to life and glory and mutants weren't extinct after all. Death seems to stalk Logan and Charles wherever they go. There is also a conspicuous lack of significant female characters in this movie, too. The only female of note is Laura/ X-23, and she spends most of the film mute. There is also a complete lack of any strong or memorable villains. No one ever reaches the level of greatness like other X-Men villains such as Magneto or William Stryker. So, despite some good performances, this film is a bit overrated and also a little too bleak and depressing for my taste.", "https://www.themoviedb.org/review/58c34453925141239c000e72");

        moviesReviews = Arrays.asList(sampleMovieReview1, sampleMovieReview2);

        moviesReviewsListing = new MovieReviewsListing() {
            @Override
            public List<MovieReview> results() {
                return moviesReviews;
            }
        };

        emptyReviewsListing = new MovieReviewsListing() {
            @Override
            public List<MovieReview> results() {
                return Collections.emptyList();
            }
        };

        MovieTrailer sampleMovieTrailer1 = MovieTrailer.create("XaE_9pfybL4", "Official Trailer #2 [UK]", "YouTube");
        MovieTrailer sampleMovieTrailer2 = MovieTrailer.create("G0HRx_0fimc", "Official Trailer #1 [UK]", "YouTube");
        MovieTrailer sampleMovieTrailer3 = MovieTrailer.create("RH3OxVFvTeg", "Official Trailer #2", "YouTube");

        moviesTrailers = Arrays.asList(sampleMovieTrailer1, sampleMovieTrailer2, sampleMovieTrailer3);

        moviesTrailersListing = new MovieTrailersListing() {
            @Override
            public List<MovieTrailer> results() {
                return moviesTrailers;
            }
        };

        emptyTrailersListing = new MovieTrailersListing() {
            @Override
            public List<MovieTrailer> results() {
                return Collections.emptyList();
            }
        };
    }

    @Override
    @NonNull
    public Single<MoviesListing> getMovies(int sortType) {
        return Single.just(moviesListing);
    }

    @NonNull
    public Single<MoviesListing> getNoMovies() {
        return Single.just(emptyMoviesListing);
    }

    @SuppressLint("NewApi")
    @Override
    @NonNull
    public Single<Movie> getMovieById(final int movieId) {

        Movie selectedMovie = null;

        Optional<Movie> firstMovieInList = movies.stream().filter(m -> m.id() == movieId).findFirst();

        if (firstMovieInList.isPresent()) {
            selectedMovie = firstMovieInList.get();
        }

        if (selectedMovie != null) {
            return Single.just(selectedMovie);
        } else {
            return Single.error(new NullPointerException());
        }
    }

    @Override
    @NonNull
    public Single<MovieReviewsListing> getMoviesReviews(int movieId) {
        return Single.just(moviesReviewsListing);
    }

    @NonNull
    public Single<MovieReviewsListing> getNoReviews() {
        return Single.just(emptyReviewsListing);
    }

    @Override
    @NonNull
    public Single<MovieTrailersListing> getMoviesTrailers(int movieId) {
        return Single.just(moviesTrailersListing);
    }

    @NonNull
    public Single<MovieTrailersListing> getNoTrailers() {
        return Single.just(emptyTrailersListing);
    }
}
