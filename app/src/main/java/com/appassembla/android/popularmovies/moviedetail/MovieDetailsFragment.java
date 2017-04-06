package com.appassembla.android.popularmovies.moviedetail;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.appassembla.android.popularmovies.R;
import com.appassembla.android.popularmovies.data.WebMoviesRepository;
import com.appassembla.android.popularmovies.models.Movie;
import com.appassembla.android.popularmovies.models.MovieReview;
import com.appassembla.android.popularmovies.models.MovieTrailer;
import com.appassembla.android.popularmovies.movielist.MovieListActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.R.drawable.star_off;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailsActivity}
 * on handsets.
 */
public class MovieDetailsFragment extends Fragment implements MovieDetailsView, View.OnClickListener {
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
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.trailersHeading)
    protected TextView trailersHeadingTextView;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.trailersList)
    protected RecyclerView trailersRecyclerView;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.reviewsHeading)
    protected TextView reviewsHeadingTextView;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.reviewsList)
    protected RecyclerView reviewsRecyclerView;
    protected FloatingActionButton favouriteFab;
    private ImageView heroImage;
    private CollapsingToolbarLayout appBarLayout;

    private Unbinder unbinder;

    private int movieId;

    public MovieDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            movieId = getArguments().getInt(ARG_ITEM_ID);
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

        favouriteFab = (FloatingActionButton) activity.findViewById(R.id.fab);

        favouriteFab.setOnClickListener(this);

        return rootView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();

        setupPresenter(movieId);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (movieDetailsPresenter != null) {
            movieDetailsPresenter.displayMovie();

            movieDetailsPresenter.displayTrailers();

            movieDetailsPresenter.displayReviews();
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
            selectMovieMessage.setVisibility(GONE);
        }
    }

    @Override
    public void displayTrailers(List<MovieTrailer> moviesTrailers) {
        trailersHeadingTextView.setVisibility(VISIBLE);
        trailersRecyclerView.setVisibility(VISIBLE);

        setupTrailersRecyclerView(trailersRecyclerView, moviesTrailers);
    }

    @Override
    public void displayReviews(List<MovieReview> moviesReviews) {
        reviewsHeadingTextView.setVisibility(VISIBLE);
        reviewsRecyclerView.setVisibility(VISIBLE);

        setupReviewsRecyclerView(reviewsRecyclerView, moviesReviews);
    }

    @Override
    public void displayTrailer(Uri trailerUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(trailerUri);

        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void displayMovieAsFavourite() {
        favouriteFab.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.star_on));
    }

    @Override
    public void onStop() {
        super.onStop();

        movieDetailsPresenter.cancelSubscriptions();

        movieDetailsPresenter = null;
    }

    private void setupTrailersRecyclerView(@NonNull RecyclerView recyclerView, @NonNull List<MovieTrailer> trailers) {
        recyclerView.setAdapter(new TrailerItemRecyclerViewAdapter(trailers));

        LinearLayoutManager listLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(listLayoutManager);

        recyclerView.setHasFixedSize(true);
    }

    private void setupReviewsRecyclerView(@NonNull RecyclerView recyclerView, @NonNull List<MovieReview> reviews) {
        recyclerView.setAdapter(new ReviewItemRecyclerViewAdapter(reviews));

        LinearLayoutManager listLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(listLayoutManager);

        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            movieDetailsPresenter.addMovieToFavourites(getContext());
        }
    }

    public class TrailerItemRecyclerViewAdapter
            extends RecyclerView.Adapter<TrailerItemRecyclerViewAdapter.ViewHolder> {

        private final List<MovieTrailer> mTrailers;

        public TrailerItemRecyclerViewAdapter(List<MovieTrailer> trailers) {
            mTrailers = trailers;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.trailers_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            holder.trailerName.setText(mTrailers.get(position).name());

            int clickedPosition = holder.getAdapterPosition();

            holder.mView.setOnClickListener(v -> movieDetailsPresenter.trailerClicked(mTrailers.get(clickedPosition).generateTrailerUrl()));
        }

        @Override
        public int getItemCount() {
            return mTrailers.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            @BindView(R.id.trailerName)
            TextView trailerName;

            public ViewHolder(View view) {
                super(view);

                ButterKnife.bind(this, view);

                mView = view;
            }
        }
    }

    public class ReviewItemRecyclerViewAdapter
            extends RecyclerView.Adapter<ReviewItemRecyclerViewAdapter.ViewHolder> {

        private final List<MovieReview> mReviews;

        public ReviewItemRecyclerViewAdapter(List<MovieReview> reviews) {
            mReviews = reviews;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.reviews_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.reviewsContent.setText(mReviews.get(position).content());
            holder.reviewsAuthor.setText(mReviews.get(position).author());
        }

        @Override
        public int getItemCount() {
            return mReviews.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            @BindView(R.id.reviewContent)
            TextView reviewsContent;
            @BindView(R.id.reviewAuthor)
            TextView reviewsAuthor;

            public ViewHolder(View view) {
                super(view);

                ButterKnife.bind(this, view);

                mView = view;
            }
        }
    }
}
