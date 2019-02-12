package khaledhaouas.com.tmdbmovies.models.interfaces;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.models.entities.Movie;

public interface OnMoviesListLoadedCallback {
    void onSuccess(ArrayList<Movie> movies, int totalPages);

    void onError();
}
