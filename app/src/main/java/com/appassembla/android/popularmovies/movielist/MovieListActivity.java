package com.appassembla.android.popularmovies.movielist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;


import com.appassembla.android.popularmovies.data.Movie;
import com.appassembla.android.popularmovies.data.WebMoviesRepository;
import com.appassembla.android.popularmovies.moviedetail.MovieDetailsActivity;
import com.appassembla.android.popularmovies.moviedetail.MovieDetailsFragment;
import com.appassembla.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.*;

/**
 * An activity representing a list of Movies. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MovieDetailsActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MovieListActivity extends AppCompatActivity implements MovieListView, AdapterView.OnItemSelectedListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean isTwoPane;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.movie_list)
    protected RecyclerView recyclerView;

    @SuppressWarnings("WeakerAccess")
    protected MovieListPresenter movieListPresenter;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.no_movies_message)
    protected TextView noMoviesTextView;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.progressBar)
    protected ProgressBar progressBar;

    private Spinner sortSpinner;

    @SuppressWarnings("WeakerAccess")
    @Nullable
    @BindView(R.id.movie_detail_container)
    protected FrameLayout movieDetailContainer;

    private final static String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle bundleRecyclerViewState;

    private static final int NUM_COLUMNS_IN_LIST = 2;

    private final static String KEY_SPINNER_POSITION = "spinner_position";

    private int lastSelectedSpinnerPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        if (savedInstanceState != null) {
            lastSelectedSpinnerPosition = savedInstanceState.getInt(KEY_SPINNER_POSITION, 0);
        }

        ButterKnife.bind(this);

        checkIfInTwoPaneMode();

        setupToolbar();

        setupPresenter();

        int desiredMoviesSortOrder = Movie.determineDesiredSortOrder(lastSelectedSpinnerPosition);

        movieListPresenter.displayMovies(desiredMoviesSortOrder);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        restoreRecyclerViewState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        setupSortSpinner(menu);

        return true;
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
    }

    private void setupSortSpinner(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_order_spinner_menu, menu);

        MenuItem item = menu.findItem(R.id.sort_order_spinner);
        sortSpinner = (Spinner) MenuItemCompat.getActionView(item);

        if (getSupportActionBar() != null) {
            Context themedContext = getSupportActionBar().getThemedContext();

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(themedContext,
                    R.array.sort_order_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            sortSpinner.setAdapter(adapter);

            sortSpinner.setSelection(lastSelectedSpinnerPosition);

            sortSpinner.setOnItemSelectedListener(this);
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, @NonNull List<Movie> movies) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(movies));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MovieListActivity.this, NUM_COLUMNS_IN_LIST);

        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setHasFixedSize(true);
    }

    private void setupPresenter() {
        movieListPresenter = new MovieListPresenter(this, new WebMoviesRepository());
    }

    private void checkIfInTwoPaneMode() {
        if (movieDetailContainer != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            isTwoPane = true;
        }
    }

    @Override
    public void displayMoviesList(@NonNull List<Movie> movies) {
        recyclerView.setVisibility(VISIBLE);
        noMoviesTextView.setVisibility(INVISIBLE);

        setupRecyclerView(recyclerView, movies);

        restoreRecyclerViewState();
    }

    @Override
    public void displayNoMoviesMessage() {
        recyclerView.setVisibility(INVISIBLE);
        noMoviesTextView.setVisibility(VISIBLE);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(GONE);
    }


    @Override
    public void displayMovieDetail(int moviePositionInRepository, int moviePositionInAdapter) {
        if (isTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putInt(MovieDetailsFragment.ARG_ITEM_ID, moviePositionInRepository);
            MovieDetailsFragment fragment = new MovieDetailsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, MovieDetailsActivity.class);
            intent.putExtra(MovieDetailsFragment.ARG_ITEM_ID, moviePositionInRepository);

            String transName = getString(R.string.poster_transition);

            SimpleItemRecyclerViewAdapter.ViewHolder currentPositionViewHolder = (SimpleItemRecyclerViewAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(moviePositionInAdapter);

            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MovieListActivity.this, currentPositionViewHolder.posterView, transName);

            ActivityCompat.startActivity(MovieListActivity.this, intent, optionsCompat.toBundle());

        }
    }

    private void restoreRecyclerViewState() {
        // restore RecyclerView state
        if (bundleRecyclerViewState != null) {
            Parcelable listState = bundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

    private void saveRecyclerViewState() {
        // save RecyclerView state
        bundleRecyclerViewState = new Bundle();
        Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
        bundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
    }

    @Override
    protected void onPause() {
        super.onPause();

        saveRecyclerViewState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        movieListPresenter.cancelSubscriptions();

        movieListPresenter = null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (sortSpinner != null) {
            outState.putInt(KEY_SPINNER_POSITION, sortSpinner.getSelectedItemPosition());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        movieListPresenter.displayMovies(position + 1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Movie> mValues;

        public SimpleItemRecyclerViewAdapter(List<Movie> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            int listWidth = recyclerView.getWidth();

            int columnWidth = listWidth / NUM_COLUMNS_IN_LIST;

            Picasso.with(getApplication())
                    .load(mValues.get(position).getPosterImgFullUrl(listWidth))
                    .placeholder(R.drawable.no_movie_poster)
                    .error(R.drawable.no_movie_poster)
                    .fit()
                    .into(holder.posterView);
            holder.posterView.setContentDescription(mValues.get(position).name());

            int clickedPosition = holder.getAdapterPosition();

            holder.mView.setOnClickListener(v -> movieListPresenter.movieClicked(mValues.get(clickedPosition).id(), clickedPosition));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            @BindView(R.id.movie_poster)
            ImageView posterView;

            public ViewHolder(View view) {
                super(view);

                ButterKnife.bind(this, view);

                mView = view;
            }
        }
    }
}
