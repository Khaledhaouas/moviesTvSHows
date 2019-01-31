package khaledhaouas.com.tmdbmovies.ui.movielist;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.models.entities.Credit;
import khaledhaouas.com.tmdbmovies.models.entities.Movie;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnCreditListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnMovieLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.repositories.CreditsRepos;
import khaledhaouas.com.tmdbmovies.models.repositories.MoviesRepos;

public class MovieDetailsViewModel extends ViewModel {
    private static final String TAG = "MovieDetailsViewModel";
    private MoviesRepos mMovieRepos;
    private CreditsRepos mCreditRepos;

    public MovieDetailsViewModel() {
        mMovieRepos = new MoviesRepos();
        mCreditRepos = new CreditsRepos();
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

    public void getMovieCreditList(int id, final OnCreditListLoadedCallback callback) {
        mCreditRepos.getMovieCreditsList(id, new OnCreditListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Credit> credits) {
                callback.onSuccess(credits);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }


}
