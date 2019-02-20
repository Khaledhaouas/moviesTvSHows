package khaledhaouas.com.tmdbmovies.ui.tvshowlist;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.models.entities.TvShow;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnTvShowListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.repositories.TvShowsRepos;

public class TvShowListViewModel extends ViewModel {
    private static final String TAG = "TvShowListViewModel";
    private TvShowsRepos mTvShowRepos;

    private int mCurrentPopularTvShowsPage;
    private int mTotalPopularTvShowsPage;
    private ArrayList<TvShow> mPopularTvShows;
    private int mCurrentUpcomingTvShowsPage;
    private int mTotalUpcomingTvShowsPage;
    private ArrayList<TvShow> mUpcomingTvShows;
    private int mCurrentLatestTvShowsPage;
    private int mTotalLatestTvShowsPage;
    private ArrayList<TvShow> mLatestTvShows;
    private int mCurrentNowPlayingTvShowsPage;
    private int mTotalNowPlayingTvShowsPage;
    private ArrayList<TvShow> mNowPlayingTvShows;
    private int mCurrentTopRatedTvShowsPage;
    private int mTotalTopRatedTvShowsPage;
    private ArrayList<TvShow> mTopRatedTvShows;
    private int mCurrentSearchedTvShowsPage;
    private int mTotalSearchedTvShowsPage;
    private ArrayList<TvShow> mSearchedTvShows;

    private boolean isNextPageLoading = false;

    public TvShowListViewModel() {
        mTvShowRepos = new TvShowsRepos();

        mPopularTvShows = new ArrayList<>();
        mUpcomingTvShows = new ArrayList<>();
        mLatestTvShows = new ArrayList<>();
        mNowPlayingTvShows = new ArrayList<>();
        mTopRatedTvShows = new ArrayList<>();
        mSearchedTvShows = new ArrayList<>();
    }

    public void getPopularTvShowsList(final OnTvShowListLoadedCallback callback) {
        mTvShowRepos.getTvShowsList("popular", new OnTvShowListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<TvShow> TvShows, int totalPages) {
                mPopularTvShows.clear();
                mPopularTvShows.addAll(TvShows);
                mTotalPopularTvShowsPage = totalPages;
                mCurrentPopularTvShowsPage++;
                Log.e(TAG, "POPULAR " + totalPages);
                callback.onSuccess(TvShows, totalPages);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void getNextPagePopularTvShowsList(final OnTvShowListLoadedCallback callback) {
        isNextPageLoading = true;
        mTvShowRepos.getTvShowsList("popular", mCurrentPopularTvShowsPage + 1, new OnTvShowListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<TvShow> TvShows, int totalPages) {
                mPopularTvShows.addAll(TvShows);
                mCurrentPopularTvShowsPage++;
                Log.e(TAG, "POPULAR " + totalPages);
                isNextPageLoading = false;
                callback.onSuccess(TvShows, totalPages);
            }

            @Override
            public void onError() {
                isNextPageLoading = false;
                callback.onError();
            }
        });
    }

    public void getNowPlayingTvShowsList(final OnTvShowListLoadedCallback callback) {
        mTvShowRepos.getTvShowsList("now_playing", new OnTvShowListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<TvShow> TvShows, int totalPages) {
                mNowPlayingTvShows.clear();
                mNowPlayingTvShows.addAll(TvShows);
                mCurrentNowPlayingTvShowsPage++;
                mTotalNowPlayingTvShowsPage = totalPages;
                Log.e(TAG, "NOW PLAYING " + totalPages);
                callback.onSuccess(TvShows, totalPages);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void getNextPageNowPlayingTvShowsList(final OnTvShowListLoadedCallback callback) {
        isNextPageLoading = true;
        mTvShowRepos.getTvShowsList("now_playing", mCurrentNowPlayingTvShowsPage + 1, new OnTvShowListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<TvShow> TvShows, int totalPages) {
                mNowPlayingTvShows.addAll(TvShows);
                mCurrentNowPlayingTvShowsPage++;
                Log.e(TAG, "now_playing " + totalPages);
                isNextPageLoading = false;
                callback.onSuccess(TvShows, totalPages);
            }

            @Override
            public void onError() {
                isNextPageLoading = false;
                callback.onError();
            }
        });
    }

    public void getUpcomingTvShowsList(final OnTvShowListLoadedCallback callback) {
        mTvShowRepos.getTvShowsList("upcoming", new OnTvShowListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<TvShow> TvShows, int totalPages) {
                mUpcomingTvShows.clear();
                mUpcomingTvShows.addAll(TvShows);
                mTotalUpcomingTvShowsPage = totalPages;
                mCurrentUpcomingTvShowsPage++;
                Log.e(TAG, "UPCOMING " + totalPages);
                callback.onSuccess(TvShows, totalPages);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void getNextPageUpcomingTvShowsList(final OnTvShowListLoadedCallback callback) {
        isNextPageLoading = true;
        mTvShowRepos.getTvShowsList("upcoming", mCurrentUpcomingTvShowsPage + 1, new OnTvShowListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<TvShow> TvShows, int totalPages) {
                mUpcomingTvShows.addAll(TvShows);
                mCurrentUpcomingTvShowsPage++;
                Log.e(TAG, "Upcoming " + totalPages);
                isNextPageLoading = false;
                callback.onSuccess(TvShows, totalPages);
            }

            @Override
            public void onError() {
                isNextPageLoading = false;
                callback.onError();
            }
        });
    }

    public void getLatestTvShowsList(final OnTvShowListLoadedCallback callback) {
        mTvShowRepos.getTvShowsList("latest", new OnTvShowListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<TvShow> TvShows, int totalPages) {
                mLatestTvShows.clear();
                mLatestTvShows.addAll(TvShows);
                mTotalLatestTvShowsPage = totalPages;
                mCurrentLatestTvShowsPage++;
                callback.onSuccess(TvShows, totalPages);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void getNextPageLatestTvShowsList(final OnTvShowListLoadedCallback callback) {
        isNextPageLoading = true;
        mTvShowRepos.getTvShowsList("latest", mCurrentLatestTvShowsPage + 1, new OnTvShowListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<TvShow> TvShows, int totalPages) {
                mLatestTvShows.addAll(TvShows);
                mCurrentLatestTvShowsPage++;
                Log.e(TAG, "Latest " + totalPages);
                isNextPageLoading = false;
                callback.onSuccess(TvShows, totalPages);
            }

            @Override
            public void onError() {
                isNextPageLoading = false;
                callback.onError();
            }
        });
    }

    public void getTopRatedTvShowsList(final OnTvShowListLoadedCallback callback) {
        mTvShowRepos.getTvShowsList("top_rated", new OnTvShowListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<TvShow> TvShows, int totalPages) {
                mTopRatedTvShows.clear();
                mTopRatedTvShows.addAll(TvShows);
                mTotalTopRatedTvShowsPage = totalPages;
                mCurrentTopRatedTvShowsPage++;
                callback.onSuccess(TvShows, totalPages);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void getNextPageTopRatedTvShowsList(final OnTvShowListLoadedCallback callback) {
        isNextPageLoading = true;
        mTvShowRepos.getTvShowsList("top_rated", mCurrentTopRatedTvShowsPage + 1, new OnTvShowListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<TvShow> TvShows, int totalPages) {
                mTopRatedTvShows.addAll(TvShows);
                mCurrentTopRatedTvShowsPage++;
                Log.e(TAG, "TopRated " + totalPages);
                isNextPageLoading = false;
                callback.onSuccess(TvShows, totalPages);
            }

            @Override
            public void onError() {
                isNextPageLoading = false;
                callback.onError();
            }
        });
    }

    public void getSearchResultTvShowsList(String searchText, final OnTvShowListLoadedCallback callback) {
        mTvShowRepos.getSearchResultTvShowsList(searchText, new OnTvShowListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<TvShow> TvShows, int totalPages) {
//                Log.e(TAG, TvShows.toString());
                mSearchedTvShows.clear();
                mSearchedTvShows.addAll(TvShows);
                mCurrentSearchedTvShowsPage++;
                mTotalSearchedTvShowsPage = totalPages;
                callback.onSuccess(TvShows, totalPages);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void getNextPageSearchedTvShowsList(String searchText, final OnTvShowListLoadedCallback callback) {
        isNextPageLoading = true;
        mTvShowRepos.getNextPageSearchResultTvShowsList(searchText, mCurrentSearchedTvShowsPage + 1, new OnTvShowListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<TvShow> TvShows, int totalPages) {
                mSearchedTvShows.addAll(TvShows);
                mCurrentSearchedTvShowsPage++;
                Log.e(TAG, "Searched " + totalPages);
                isNextPageLoading = false;
                callback.onSuccess(TvShows, totalPages);
            }

            @Override
            public void onError() {
                isNextPageLoading = false;
                callback.onError();
            }
        });
    }

    public ArrayList<TvShow> getPopularTvShows() {
        return mPopularTvShows;
    }

    public ArrayList<TvShow> getUpcomingTvShows() {
        return mUpcomingTvShows;
    }

    public ArrayList<TvShow> getLatestTvShows() {
        return mLatestTvShows;
    }

    public ArrayList<TvShow> getNowPlayingTvShows() {
        return mNowPlayingTvShows;
    }

    public ArrayList<TvShow> getTopRatedTvShows() {
        return mTopRatedTvShows;
    }

    public ArrayList<TvShow> getSearchedTvShows() {
        return mSearchedTvShows;
    }

    public int getCurrentPopularTvShowsPage() {
        return mCurrentPopularTvShowsPage;
    }

    public int getTotalPopularTvShowsPage() {
        return mTotalPopularTvShowsPage;
    }

    public int getCurrentUpcomingTvShowsPage() {
        return mCurrentUpcomingTvShowsPage;
    }

    public int getTotalUpcomingTvShowsPage() {
        return mTotalUpcomingTvShowsPage;
    }

    public int getCurrentLatestTvShowsPage() {
        return mCurrentLatestTvShowsPage;
    }

    public int getTotalLatestTvShowsPage() {
        return mTotalLatestTvShowsPage;
    }

    public int getCurrentNowPlayingTvShowsPage() {
        return mCurrentNowPlayingTvShowsPage;
    }

    public int getTotalNowPlayingTvShowsPage() {
        return mTotalNowPlayingTvShowsPage;
    }

    public int getCurrentTopRatedTvShowsPage() {
        return mCurrentTopRatedTvShowsPage;
    }

    public int getTotalTopRatedTvShowsPage() {
        return mTotalTopRatedTvShowsPage;
    }

    public int getCurrentSearchedTvShowsPage() {
        return mCurrentSearchedTvShowsPage;
    }

    public int getTotalSearchedTvShowsPage() {
        return mTotalSearchedTvShowsPage;
    }

    public boolean isNextPageLoading() {
        return isNextPageLoading;
    }


}
