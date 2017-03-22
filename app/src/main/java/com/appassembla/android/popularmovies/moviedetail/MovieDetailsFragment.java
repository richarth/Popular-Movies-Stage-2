package com.appassembla.android.popularmovies.moviedetail;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.appassembla.android.popularmovies.R;
import com.appassembla.android.popularmovies.data.Movie;
import com.appassembla.android.popularmovies.data.WebMoviesRepository;
import com.appassembla.android.popularmovies.movielist.MovieListActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailsActivity}
 * on handsets.
 */
public class MovieDetailsFragment extends Fragment implements MovieDetailsView {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    private MovieDetailsPresenter movieDetailsPresenter;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.detail_poster_image_view)
    protected ImageView posterImageView;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.release_date_text_view)
    protected TextView releaseDateTextView;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.user_rating_text_view)
    protected TextView averageRatingTextView;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.plot_synopsis_text_view)
    protected TextView synopsisTextView;
    private ImageView heroImage;
    private CollapsingToolbarLayout appBarLayout;

    private Unbinder unbinder;

    public MovieDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            setupPresenter(getArguments().getInt(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_detail, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        Activity activity = this.getActivity();

        appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);

        heroImage = (ImageView) activity.findViewById(R.id.backdrop);

        return rootView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (movieDetailsPresenter != null) {
            movieDetailsPresenter.displayMovie();
        }
    }

    private void setToolbarTitle(String movieName) {
        if (appBarLayout != null) {
            appBarLayout.setTitle(movieName);
        }
    }

    private void setupPresenter(int selectedMovieId) {
        movieDetailsPresenter = new MovieDetailsPresenter(this, new WebMoviesRepository(), selectedMovieId);
    }

    @Override
    public void displayMovieDetails(@NonNull Movie selectedMovie) {
        setToolbarTitle(selectedMovie.name());

        int detailsViewWidth = getActivity().findViewById(R.id.details_scroll_view).getWidth();

        int posterImageWidth = detailsViewWidth / 2;

        Picasso.with(getActivity()).load(selectedMovie.getPosterImgFullUrl(posterImageWidth))
                .placeholder(R.drawable.no_movie_poster)
                .error(R.drawable.no_movie_poster)
                .fit()
                .into(posterImageView);

        posterImageView.setContentDescription(selectedMovie.name());

        // We now have the poster image so can start the activity transition of that image
        posterImageView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        posterImageView.getViewTreeObserver().removeOnPreDrawListener(this);
                        getActivity().supportStartPostponedEnterTransition();
                        return true;
                    }
                }
        );

        // on tablet the hero image won't be present
        if (heroImage != null) {
            Picasso.with(getActivity()).load(selectedMovie.getHeroImgFullUrl(detailsViewWidth)).resize(detailsViewWidth, 0).into(heroImage);

            heroImage.setContentDescription(selectedMovie.name());
        }

        releaseDateTextView.setText(selectedMovie.getMovieYear());

        String averageRating = Double.toString(selectedMovie.averageRating()) + "/10";
        averageRatingTextView.setText(averageRating);

        synopsisTextView.setText(selectedMovie.plotSynopsis());
    }

    @Override
    public void hideSelectMovieMessage() {
        TextView selectMovieMessage = (TextView) getActivity().findViewById(R.id.select_movie_message);

        if (selectMovieMessage != null) {
            selectMovieMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        movieDetailsPresenter.cancelSubscriptions();

        movieDetailsPresenter = null;
    }
}
