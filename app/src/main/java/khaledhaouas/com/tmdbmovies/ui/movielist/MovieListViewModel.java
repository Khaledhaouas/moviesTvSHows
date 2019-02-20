package khaledhaouas.com.tmdbmovies.ui.movielist;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.models.entities.Movie;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnMoviesListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.repositories.MoviesRepos;

public class MovieListViewModel extends ViewModel {
    private static final String TAG = "TvShowListViewModel";
    private MoviesRepos mMovieRepos;

    private int mCurrentPopularMoviesPage;
    private int mTotalPopularMoviesPage;
    private ArrayList<Movie> mPopularMovies;
    private int mCurrentUpcomingMoviesPage;
    private int mTotalUpcomingMoviesPage;
    private ArrayList<Movie> mUpcomingMovies;
    private int mCurrentLatestMoviesPage;
    private int mTotalLatestMoviesPage;
    private ArrayList<Movie> mLatestMovies;
    private int mCurrentNowPlayingMoviesPage;
    private int mTotalNowPlayingMoviesPage;
    private ArrayList<Movie> mNowPlayingMovies;
    private int mCurrentTopRatedMoviesPage;
    private int mTotalTopRatedMoviesPage;
    private ArrayList<Movie> mTopRatedMovies;
    private int mCurrentSearchedMoviesPage;
    private int mTotalSearchedMoviesPage;
    private ArrayList<Movie> mSearchedMovies;

    private boolean isNextPageLoading = false;

    public MovieListViewModel() {
        mMovieRepos = new MoviesRepos();

        mPopularMovies = new ArrayList<>();
        mUpcomingMovies = new ArrayList<>();
        mLatestMovies = new ArrayList<>();
        mNowPlayingMovies = new ArrayList<>();
        mTopRatedMovies = new ArrayList<>();
        mSearchedMovies = new ArrayList<>();
    }

    public void getPopularMoviesList(final OnMoviesListLoadedCallback callback) {
        mMovieRepos.getMoviesList("popular", new OnMoviesListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> movies, int totalPages) {
                mPopularMovies.clear();
                mPopularMovies.addAll(movies);
                mTotalPopularMoviesPage = totalPages;
                mCurrentPopularMoviesPage++;
                Log.e(TAG, "POPULAR " + totalPages);
                callback.onSuccess(movies, totalPages);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void getNextPagePopularMoviesList(final OnMoviesListLoadedCallback callback) {
        isNextPageLoading = true;
        mMovieRepos.getMoviesList("popular", mCurrentPopularMoviesPage + 1, new OnMoviesListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> movies, int totalPages) {
                mPopularMovies.addAll(movies);
                mCurrentPopularMoviesPage++;
                Log.e(TAG, "POPULAR " + totalPages);
                isNextPageLoading = false;
                callback.onSuccess(movies, totalPages);
            }

            @Override
            public void onError() {
                isNextPageLoading = false;
                callback.onError();
            }
        });
    }

    public void getNowPlayingMoviesList(final OnMoviesListLoadedCallback callback) {
        mMovieRepos.getMoviesList("now_playing", new OnMoviesListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> movies, int totalPages) {
                mNowPlayingMovies.clear();
                mNowPlayingMovies.addAll(movies);
                mCurrentNowPlayingMoviesPage++;
                mTotalNowPlayingMoviesPage = totalPages;
                Log.e(TAG, "NOW PLAYING " + totalPages);
                callback.onSuccess(movies, totalPages);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void getNextPageNowPlayingMoviesList(final OnMoviesListLoadedCallback callback) {
        isNextPageLoading = true;
        mMovieRepos.getMoviesList("now_playing", mCurrentNowPlayingMoviesPage + 1, new OnMoviesListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> movies, int totalPages) {
                mNowPlayingMovies.addAll(movies);
                mCurrentNowPlayingMoviesPage++;
                Log.e(TAG, "now_playing " + totalPages);
                isNextPageLoading = false;
                callback.onSuccess(movies, totalPages);
            }

            @Override
            public void onError() {
                isNextPageLoading = false;
                callback.onError();
            }
        });
    }

    public void getUpcomingMoviesList(final OnMoviesListLoadedCallback callback) {
        mMovieRepos.getMoviesList("upcoming", new OnMoviesListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> movies, int totalPages) {
                mUpcomingMovies.clear();
                mUpcomingMovies.addAll(movies);
                mTotalUpcomingMoviesPage = totalPages;
                mCurrentUpcomingMoviesPage++;
                Log.e(TAG, "UPCOMING " + totalPages);
                callback.onSuccess(movies, totalPages);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void getNextPageUpcomingMoviesList(final OnMoviesListLoadedCallback callback) {
        isNextPageLoading = true;
        mMovieRepos.getMoviesList("upcoming", mCurrentUpcomingMoviesPage + 1, new OnMoviesListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> movies, int totalPages) {
                mUpcomingMovies.addAll(movies);
                mCurrentUpcomingMoviesPage++;
                Log.e(TAG, "Upcoming " + totalPages);
                isNextPageLoading = false;
                callback.onSuccess(movies, totalPages);
            }

            @Override
            public void onError() {
                isNextPageLoading = false;
                callback.onError();
            }
        });
    }

    public void getLatestMoviesList(final OnMoviesListLoadedCallback callback) {
        mMovieRepos.getMoviesList("latest", new OnMoviesListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> movies, int totalPages) {
                mLatestMovies.clear();
                mLatestMovies.addAll(movies);
                mTotalLatestMoviesPage = totalPages;
                mCurrentLatestMoviesPage++;
                callback.onSuccess(movies, totalPages);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void getNextPageLatestMoviesList(final OnMoviesListLoadedCallback callback) {
        isNextPageLoading = true;
        mMovieRepos.getMoviesList("latest", mCurrentLatestMoviesPage + 1, new OnMoviesListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> movies, int totalPages) {
                mLatestMovies.addAll(movies);
                mCurrentLatestMoviesPage++;
                Log.e(TAG, "Latest " + totalPages);
                isNextPageLoading = false;
                callback.onSuccess(movies, totalPages);
            }

            @Override
            public void onError() {
                isNextPageLoading = false;
                callback.onError();
            }
        });
    }

    public void getTopRatedMoviesList(final OnMoviesListLoadedCallback callback) {
        mMovieRepos.getMoviesList("top_rated", new OnMoviesListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> movies, int totalPages) {
                mTopRatedMovies.clear();
                mTopRatedMovies.addAll(movies);
                mTotalTopRatedMoviesPage = totalPages;
                mCurrentTopRatedMoviesPage++;
                callback.onSuccess(movies, totalPages);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void getNextPageTopRatedMoviesList(final OnMoviesListLoadedCallback callback) {
        isNextPageLoading = true;
        mMovieRepos.getMoviesList("top_rated", mCurrentTopRatedMoviesPage + 1, new OnMoviesListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> movies, int totalPages) {
                mTopRatedMovies.addAll(movies);
                mCurrentTopRatedMoviesPage++;
                Log.e(TAG, "TopRated " + totalPages);
                isNextPageLoading = false;
                callback.onSuccess(movies, totalPages);
            }

            @Override
            public void onError() {
                isNextPageLoading = false;
                callback.onError();
            }
        });
    }

    public void getSearchResultMoviesList(String searchText, final OnMoviesListLoadedCallback callback) {
        mMovieRepos.getSearchResultMoviesList(searchText, new OnMoviesListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> movies, int totalPages) {
//                Log.e(TAG, movies.toString());
                mSearchedMovies.clear();
                mSearchedMovies.addAll(movies);
                mCurrentSearchedMoviesPage++;
                mTotalSearchedMoviesPage = totalPages;
                callback.onSuccess(movies, totalPages);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void getNextPageSearchedMoviesList(String searchText, final OnMoviesListLoadedCallback callback) {
        isNextPageLoading = true;
        mMovieRepos.getNextPageSearchResultMoviesList(searchText, mCurrentSearchedMoviesPage + 1, new OnMoviesListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> movies, int totalPages) {
                mSearchedMovies.addAll(movies);
                mCurrentSearchedMoviesPage++;
                Log.e(TAG, "Searched " + totalPages);
                isNextPageLoading = false;
                callback.onSuccess(movies, totalPages);
            }

            @Override
            public void onError() {
                isNextPageLoading = false;
                callback.onError();
            }
        });
    }

    public ArrayList<Movie> getPopularMovies() {
        return mPopularMovies;
    }

    public ArrayList<Movie> getUpcomingMovies() {
        return mUpcomingMovies;
    }

    public ArrayList<Movie> getLatestMovies() {
        return mLatestMovies;
    }

    public ArrayList<Movie> getNowPlayingMovies() {
        return mNowPlayingMovies;
    }

    public ArrayList<Movie> getTopRatedMovies() {
        return mTopRatedMovies;
    }

    public ArrayList<Movie> getSearchedMovies() {
        return mSearchedMovies;
    }

    public int getCurrentPopularMoviesPage() {
        return mCurrentPopularMoviesPage;
    }

    public int getTotalPopularMoviesPage() {
        return mTotalPopularMoviesPage;
    }

    public int getCurrentUpcomingMoviesPage() {
        return mCurrentUpcomingMoviesPage;
    }

    public int getTotalUpcomingMoviesPage() {
        return mTotalUpcomingMoviesPage;
    }

    public int getCurrentLatestMoviesPage() {
        return mCurrentLatestMoviesPage;
    }

    public int getTotalLatestMoviesPage() {
        return mTotalLatestMoviesPage;
    }

    public int getCurrentNowPlayingMoviesPage() {
        return mCurrentNowPlayingMoviesPage;
    }

    public int getTotalNowPlayingMoviesPage() {
        return mTotalNowPlayingMoviesPage;
    }

    public int getCurrentTopRatedMoviesPage() {
        return mCurrentTopRatedMoviesPage;
    }

    public int getTotalTopRatedMoviesPage() {
        return mTotalTopRatedMoviesPage;
    }

    public int getCurrentSearchedMoviesPage() {
        return mCurrentSearchedMoviesPage;
    }

    public int getTotalSearchedMoviesPage() {
        return mTotalSearchedMoviesPage;
    }

    public boolean isNextPageLoading() {
        return isNextPageLoading;
    }


}
