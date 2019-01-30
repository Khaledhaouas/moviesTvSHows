package khaledhaouas.com.tmdbmovies.models.interfaces;

import khaledhaouas.com.tmdbmovies.models.entities.Movie;

public interface OnMovieLoadedCallback {
    void onSuccess(Movie movie);

    void onError();
}
