package com.appassembla.android.popularmovies.data;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by richard.thompson on 23/03/2017.
 */
@SuppressWarnings({"unused", "DefaultFileTemplate"})
@RunWith(JUnit4.class)
public class MovieReviewTest {
    @Test
    public void verifyReviewJsonConvertsToModel() throws Exception {
        String sampleReviewJson = "{\"id\":\"58bbf107c3a368666b032be5\",\"author\":\"anythingbutfifi\",\"content\":\"**LOGAN REVIEW: THE WOLVERINE GETS A SUPER SEND-OFF**\\r\\n\\r\\n\\\"Owing to its agitated hero’s misfortunes through the ages, this is a film that’s acutely aware of the dangers of emotional exploitation, and it spares its audience a similar fate. With Logan, Mangold and co-writer Scott Frank tell the definitive story of the Wolverine, in an involving and deeply satisfying series finale. It shows the fate of mutants when age starts to weary them, with stakes that feel real, and empathy that’s earned.\\\"\\r\\n\\r\\nREAD THE FULL REVIEW AT SBS MOVIES: http://www.sbs.com.au/movies/review/logan-review-wolverine-gets-super-send\",\"url\":\"https://www.themoviedb.org/review/58bbf107c3a368666b032be5\"}";

        MovieReview sampleMovieReview = MovieReview.create("anythingbutfifi", "**LOGAN REVIEW: THE WOLVERINE GETS A SUPER SEND-OFF**\\r\\n\\r\\n\"Owing to its agitated hero’s misfortunes through the ages, this is a film that’s acutely aware of the dangers of emotional exploitation, and it spares its audience a similar fate. With Logan, Mangold and co-writer Scott Frank tell the definitive story of the Wolverine, in an involving and deeply satisfying series finale. It shows the fate of mutants when age starts to weary them, with stakes that feel real, and empathy that’s earned.\"\\r\\n\\r\\nREAD THE FULL REVIEW AT SBS MOVIES: http://www.sbs.com.au/movies/review/logan-review-wolverine-gets-super-send", "https://www.themoviedb.org/review/58bbf107c3a368666b032be5");

        Moshi moshi = new Moshi.Builder()
                .add(MovieReviewAdapterFactory.create())
                .build();

        JsonAdapter<MovieReview> adapter = moshi.adapter(MovieReview.class).lenient();

        assertEquals(sampleMovieReview, adapter.fromJson(sampleReviewJson));
    }

    @Test
    public void verifyReviewsJsonConvertsToModelsList() throws Exception {
        String sampleReviewsJson = "{\"id\":263115,\"page\":1,\"results\":[{\"id\":\"58bbf107c3a368666b032be5\",\"author\":\"anythingbutfifi\",\"content\":\"**LOGAN REVIEW: THE WOLVERINE GETS A SUPER SEND-OFF**\\r\\n\\r\\n\\\"Owing to its agitated hero’s misfortunes through the ages, this is a film that’s acutely aware of the dangers of emotional exploitation, and it spares its audience a similar fate. With Logan, Mangold and co-writer Scott Frank tell the definitive story of the Wolverine, in an involving and deeply satisfying series finale. It shows the fate of mutants when age starts to weary them, with stakes that feel real, and empathy that’s earned.\\\"\\r\\n\\r\\nREAD THE FULL REVIEW AT SBS MOVIES: http://www.sbs.com.au/movies/review/logan-review-wolverine-gets-super-send\",\"url\":\"https://www.themoviedb.org/review/58bbf107c3a368666b032be5\"},{\"id\":\"58c34453925141239c000e72\",\"author\":\"Movie Queen41\",\"content\":\"There may be some fine performances in this movie, but I honestly think the critics overrated this latest entry in the X-Men saga. The performances of Wolverine and Prof. X by Hugh Jackman and Patrick Stewart are extraordinary. They create a believable and loving father-son bond between their characters, with Logan caring for the ninety year old leader of the X-Men after a horrible event occurs at the Xavier school the year before. Stephen Merchant takes over the role of Caliban from Tómas Lemarquis, who played the character in X-Men Apocalypse, and transforms him into an ally to Logan and Charlies. Merchant is quite good in the role. But what really dragged down the movie is its nihilism. The other X-Men are completely missing from the film and mutants have been wiped out almost completely. A sense of doom and hopelessness looms over the film. This movie completely upends the warm and hopeful epilogue of Days of Future Past, where the X-Men were restored to life and glory and mutants weren't extinct after all. Death seems to stalk Logan and Charles wherever they go. There is also a conspicuous lack of significant female characters in this movie, too. The only female of note is Laura/ X-23, and she spends most of the film mute. There is also a complete lack of any strong or memorable villains. No one ever reaches the level of greatness like other X-Men villains such as Magneto or William Stryker. So, despite some good performances, this film is a bit overrated and also a little too bleak and depressing for my taste.\",\"url\":\"https://www.themoviedb.org/review/58c34453925141239c000e72\"}],\"total_pages\":1,\"total_results\":2}";

        MovieReview sampleMovieReview1 = MovieReview.create("anythingbutfifi", "**LOGAN REVIEW: THE WOLVERINE GETS A SUPER SEND-OFF**\\n\\n\"Owing to its agitated hero’s misfortunes through the ages, this is a film that’s acutely aware of the dangers of emotional exploitation, and it spares its audience a similar fate. With Logan, Mangold and co-writer Scott Frank tell the definitive story of the Wolverine, in an involving and deeply satisfying series finale. It shows the fate of mutants when age starts to weary them, with stakes that feel real, and empathy that’s earned.\"\\n\\nREAD THE FULL REVIEW AT SBS MOVIES: http://www.sbs.com.au/movies/review/logan-review-wolverine-gets-super-send", "https://www.themoviedb.org/review/58bbf107c3a368666b032be5");
        MovieReview sampleMovieReview2 = MovieReview.create("Movie Queen41", "There may be some fine performances in this movie, but I honestly think the critics overrated this latest entry in the X-Men saga. The performances of Wolverine and Prof. X by Hugh Jackman and Patrick Stewart are extraordinary. They create a believable and loving father-son bond between their characters, with Logan caring for the ninety year old leader of the X-Men after a horrible event occurs at the Xavier school the year before. Stephen Merchant takes over the role of Caliban from Tómas Lemarquis, who played the character in X-Men Apocalypse, and transforms him into an ally to Logan and Charlies. Merchant is quite good in the role. But what really dragged down the movie is its nihilism. The other X-Men are completely missing from the film and mutants have been wiped out almost completely. A sense of doom and hopelessness looms over the film. This movie completely upends the warm and hopeful epilogue of Days of Future Past, where the X-Men were restored to life and glory and mutants weren't extinct after all. Death seems to stalk Logan and Charles wherever they go. There is also a conspicuous lack of significant female characters in this movie, too. The only female of note is Laura/ X-23, and she spends most of the film mute. There is also a complete lack of any strong or memorable villains. No one ever reaches the level of greatness like other X-Men villains such as Magneto or William Stryker. So, despite some good performances, this film is a bit overrated and also a little too bleak and depressing for my taste.", "https://www.themoviedb.org/review/58c34453925141239c000e72");

        Moshi moshi = new Moshi.Builder()
                .add(MovieReviewAdapterFactory.create())
                .build();

        JsonAdapter<MovieReviewsListing> adapter = moshi.adapter(MovieReviewsListing.class).lenient();

        MovieReviewsListing movieReviewsListing = adapter.fromJson(sampleReviewsJson);

        List<MovieReview> movieReviews = movieReviewsListing.results();

        assertEquals(movieReviews.size(), 2);

        assertEquals(sampleMovieReview1, movieReviews.get(0));
        assertEquals(sampleMovieReview2, movieReviews.get(1));
    }
}