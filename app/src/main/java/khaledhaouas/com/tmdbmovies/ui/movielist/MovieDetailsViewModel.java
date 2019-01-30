package khaledhaouas.com.tmdbmovies.ui.movielist;

import android.arch.lifecycle.ViewModel;

import khaledhaouas.com.tmdbmovies.models.entities.Movie;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnMovieLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.repositories.MoviesRepos;

public class MovieDetailsViewModel extends ViewModel {
    private static final String TAG = "MovieDetailsViewModel";
    private MoviesRepos mMovieRepos;

    public MovieDetailsViewModel() {
        mMovieRepos = new MoviesRepos();
    }

    public void getMovieDetails(int id, final OnMovieLoadedCallback callback) {
        mMovieRepos.getMovieDetails(id, new OnMovieLoadedCallback() {
            @Override
            public void onSuccess(Movie movie) {
                callback.onSuccess(movie);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });

    }

}
