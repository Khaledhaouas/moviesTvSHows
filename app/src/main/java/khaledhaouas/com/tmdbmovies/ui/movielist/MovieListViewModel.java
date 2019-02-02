package khaledhaouas.com.tmdbmovies.ui.movielist;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.models.entities.Movie;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnMoviesListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.repositories.MoviesRepos;

public class MovieListViewModel extends ViewModel {
    private MoviesRepos mMovieRepos;

    private ArrayList<Movie> mPopularMovies;
    private ArrayList<Movie> mUpcomingMovies;
    private ArrayList<Movie> mLatestMovies;
    private ArrayList<Movie> mNowPlayingMovies;
    private ArrayList<Movie> mTopRatedMovies;


    public MovieListViewModel() {
        mMovieRepos = new MoviesRepos();

        mPopularMovies = new ArrayList<>();
        mUpcomingMovies = new ArrayList<>();
        mLatestMovies = new ArrayList<>();
        mNowPlayingMovies = new ArrayList<>();
        mTopRatedMovies = new ArrayList<>();
    }

    public void getPopularMoviesList(final OnMoviesListLoadedCallback callback) {
        mMovieRepos.getMoviesList("popular", new OnMoviesListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> movies) {
                mPopularMovies.clear();
                mPopularMovies.addAll(movies);
                callback.onSuccess(movies);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void getNowPlayingMoviesList(final OnMoviesListLoadedCallback callback) {
        mMovieRepos.getMoviesList("now_playing", new OnMoviesListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> movies) {
                mNowPlayingMovies.clear();
                mNowPlayingMovies.addAll(movies);
                callback.onSuccess(movies);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void getUpcomingMoviesList(final OnMoviesListLoadedCallback callback) {
        mMovieRepos.getMoviesList("upcoming", new OnMoviesListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> movies) {
                mUpcomingMovies.clear();
                mUpcomingMovies.addAll(movies);
                callback.onSuccess(movies);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void getLatestMoviesList(final OnMoviesListLoadedCallback callback) {
        mMovieRepos.getMoviesList("latest", new OnMoviesListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> movies) {
                mLatestMovies.clear();
                mLatestMovies.addAll(movies);
                callback.onSuccess(movies);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void getTopRatedMoviesList(final OnMoviesListLoadedCallback callback) {
        mMovieRepos.getMoviesList("top_rated", new OnMoviesListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> movies) {
                mTopRatedMovies.clear();
                mTopRatedMovies.addAll(movies);
                callback.onSuccess(movies);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public ArrayList<Movie> getmPopularMovies() {
        return mPopularMovies;
    }

    public ArrayList<Movie> getmUpcomingMovies() {
        return mUpcomingMovies;
    }

    public ArrayList<Movie> getmLatestMovies() {
        return mLatestMovies;
    }

    public ArrayList<Movie> getmNowPlayingMovies() {
        return mNowPlayingMovies;
    }

    public ArrayList<Movie> getmTopRatedMovies() {
        return mTopRatedMovies;
    }
}
