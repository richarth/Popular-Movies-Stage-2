package com.appassembla.android.popularmovies.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * Created by richardthompson on 19/02/2017.
 */
@SuppressWarnings({"unused", "DefaultFileTemplate"})
@RunWith(JUnit4.class)
public class MovieTest {

    @Test
    public void shouldSortByHighestRated() {
        int selectedSpinnerPosition = 1;

        int sortOrderSelected = Movie.determineDesiredSortOrder(selectedSpinnerPosition);

        assertEquals(MoviesRepository.TOP_RATED_SORT_TYPE, sortOrderSelected);
    }

    @Test
    public void shouldSortByMostPopular() {
        int selectedSpinnerPosition = 0;

        int sortOrderSelected = Movie.determineDesiredSortOrder(selectedSpinnerPosition);

        assertEquals(MoviesRepository.POPULAR_SORT_TYPE, sortOrderSelected);
    }

    @Test
    public void shouldReturnW780ListImage() {
        int recyclerViewWidth = 2000;

        String listImageWidth = Movie.determineListImageWidth(recyclerViewWidth);

        assertEquals("w780", listImageWidth);
    }

    @Test
    public void shouldReturnW500ListImage() {
        int recyclerViewWidth = 900;

        String listImageWidth = Movie.determineListImageWidth(recyclerViewWidth);

        assertEquals("w500", listImageWidth);
    }

    @Test
    public void shouldReturnW342ListImage() {
        int recyclerViewWidth = 650;

        String listImageWidth = Movie.determineListImageWidth(recyclerViewWidth);

        assertEquals("w342", listImageWidth);
    }

    @Test
    public void shouldReturnW185ListImage() {
        int recyclerViewWidth = 360;

        String listImageWidth = Movie.determineListImageWidth(recyclerViewWidth);

        assertEquals("w185", listImageWidth);
    }

    @Test
    public void shouldReturnW154ListImage() {
        int recyclerViewWidth = 300;

        String listImageWidth = Movie.determineListImageWidth(recyclerViewWidth);

        assertEquals("w154", listImageWidth);
    }

    @Test
    public void shouldReturnW92ListImage() {
        int recyclerViewWidth = 180;

        String listImageWidth = Movie.determineListImageWidth(recyclerViewWidth);

        assertEquals("w92", listImageWidth);
    }

    @Test
    public void shouldReturnW780HeroImage() {
        int detailsViewWidth = 800;

        String heroImageWidth = Movie.determineHeroImageWidth(detailsViewWidth);

        assertEquals("w780", heroImageWidth);
    }

    @Test
    public void shouldReturnW500HeroImage() {
        int detailsViewWidth = 400;

        String heroImageWidth = Movie.determineHeroImageWidth(detailsViewWidth);

        assertEquals("w500", heroImageWidth);
    }

    @Test
    public void shouldReturnW342HeroImage() {
        int detailsViewWidth = 320;

        String heroImageWidth = Movie.determineHeroImageWidth(detailsViewWidth);

        assertEquals("w342", heroImageWidth);
    }

    @Test
    public void shouldReturnW185HeroImage() {
        int detailsViewWidth = 180;

        String heroImageWidth = Movie.determineHeroImageWidth(detailsViewWidth);

        assertEquals("w185", heroImageWidth);
    }

    @Test
    public void shouldReturnW154HeroImage() {
        int detailsViewWidth = 120;

        String heroImageWidth = Movie.determineHeroImageWidth(detailsViewWidth);

        assertEquals("w154", heroImageWidth);
    }

    @Test
    public void shouldReturnW92HeroImage() {
        int detailsViewWidth = 80;

        String heroImageWidth = Movie.determineHeroImageWidth(detailsViewWidth);

        assertEquals("w92", heroImageWidth);
    }
}