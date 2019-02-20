package khaledhaouas.com.tmdbmovies.ui.tvshowdetails;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.models.entities.Credit;
import khaledhaouas.com.tmdbmovies.models.entities.Review;
import khaledhaouas.com.tmdbmovies.models.entities.TvShow;
import khaledhaouas.com.tmdbmovies.models.entities.Video;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnCreditListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnReviewListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnTvShowListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnTvShowLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnVideoListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.repositories.CreditsRepos;
import khaledhaouas.com.tmdbmovies.models.repositories.ReviewsRepos;
import khaledhaouas.com.tmdbmovies.models.repositories.TvShowsRepos;


public class TvShowDetailsViewModel extends ViewModel {
    private static final String TAG = "TvShowDetailsViewModel";
    private TvShowsRepos mTvShowRepos;
    private CreditsRepos mCreditRepos;
    private ReviewsRepos mReviewRepos;

    private int mTvShowId;
    private TvShow mTvShow;
    private ArrayList<Credit> mCredits;
    private ArrayList<Review> mReviews;
    private ArrayList<TvShow> mSimilarTvShows;

    private int mCurrentSimilarTvShowsPage;
    private int mTotalSimilarTvShowsPage;

    private boolean isNextPageLoading = false;

    public TvShowDetailsViewModel() {
        mTvShowRepos = new TvShowsRepos();
        mCreditRepos = new CreditsRepos();
        mReviewRepos = new ReviewsRepos();

        mReviews = new ArrayList<>();
        mCredits = new ArrayList<>();
        mSimilarTvShows = new ArrayList<>();
    }

    public void getTvShowDetails(final OnTvShowLoadedCallback callback) {
        mTvShowRepos.getTvShowDetails(mTvShowId, new OnTvShowLoadedCallback() {
            @Override
            public void onSuccess(TvShow tvShow) {
                mTvShow = tvShow;
                callback.onSuccess(tvShow);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });

    }

    public void getTvShowCreditList(final OnCreditListLoadedCallback callback) {
        mCreditRepos.getTvCreditsList(mTvShowId, new OnCreditListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Credit> credits) {
                mCredits.clear();
                mCredits.addAll(credits);
                callback.onSuccess(credits);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void getTvShowReviewList(final OnReviewListLoadedCallback callback) {
        mReviewRepos.getTvReviewsList(mTvShowId, new OnReviewListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Review> reviews) {
                mReviews.clear();
                mReviews.addAll(reviews);
                callback.onSuccess(reviews);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void getSimilarTvShowsList(final OnTvShowListLoadedCallback callback) {
        mTvShowRepos.getSimilarTvShowsList(mTvShowId, new OnTvShowListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<TvShow> tvShows, int totalPages) {
                mSimilarTvShows.clear();
                mSimilarTvShows.addAll(tvShows);
                mCurrentSimilarTvShowsPage++;
                mTotalSimilarTvShowsPage = totalPages;
                callback.onSuccess(tvShows, totalPages);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void getNextPageSimilarTvShowsList(int id, final OnTvShowListLoadedCallback callback) {
        isNextPageLoading = true;
        mTvShowRepos.getSimilarTvShowsList(id, new OnTvShowListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<TvShow> tvShows, int totalPages) {
                mSimilarTvShows.addAll(tvShows);
                mCurrentSimilarTvShowsPage++;
                isNextPageLoading = false;
                callback.onSuccess(tvShows, totalPages);
            }

            @Override
            public void onError() {
                isNextPageLoading = false;
                callback.onError();
            }
        });
    }

    public void getTvShowVideosList(final OnVideoListLoadedCallback callback) {
        mTvShowRepos.getTvShowVideosList(mTvShowId, new OnVideoListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Video> videos) {
                callback.onSuccess(videos);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public int getTvShowId() {
        return mTvShowId;
    }

    public void setTvShowId(int mTvShowId) {
        this.mTvShowId = mTvShowId;
    }

    public TvShow getTvShow() {
        return mTvShow;
    }

    public ArrayList<Credit> getCredits() {
        return mCredits;
    }

    public ArrayList<Review> getmReviews() {
        return mReviews;
    }

    public ArrayList<TvShow> getmSimilarTvShows() {
        return mSimilarTvShows;
    }

    public int getCurrentSimilarTvShowsPage() {
        return mCurrentSimilarTvShowsPage;
    }

    public int getTotalSimilarTvShowsPage() {
        return mTotalSimilarTvShowsPage;
    }

    public boolean isNextPageLoading() {
        return isNextPageLoading;
    }
}
