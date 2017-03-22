package com.appassembla.android.popularmovies.moviedetail;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.appassembla.android.popularmovies.R;
import com.appassembla.android.popularmovies.movielist.MovieListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a single Movie detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MovieListActivity}.
 */
public class MovieDetailsActivity extends AppCompatActivity {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.detail_toolbar)
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);

        setupToolbar();

        showUpButton();

        if (savedInstanceState == null) {
            addMovieDetailsFragment();
        }

        supportPostponeEnterTransition();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
    }

    private void showUpButton() {
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void addMovieDetailsFragment() {
        // Create the detail fragment and add it to the activity
        // using a fragment transaction.
        Bundle arguments = new Bundle();
        arguments.putInt(MovieDetailsFragment.ARG_ITEM_ID,
                getIntent().getIntExtra(MovieDetailsFragment.ARG_ITEM_ID, 0));
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.movie_detail_container, fragment)
                .commit();
    }
}
