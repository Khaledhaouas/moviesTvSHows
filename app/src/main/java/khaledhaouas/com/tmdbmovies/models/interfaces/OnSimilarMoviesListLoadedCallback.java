package khaledhaouas.com.tmdbmovies.models.interfaces;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.models.entities.Movie;
import khaledhaouas.com.tmdbmovies.models.entities.Review;

public interface OnSimilarMoviesListLoadedCallback {
    void onSuccess(ArrayList<Movie> movies);

    void onError();
}
