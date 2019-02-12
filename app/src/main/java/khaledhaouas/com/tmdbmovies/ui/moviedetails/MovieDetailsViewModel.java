package khaledhaouas.com.tmdbmovies.ui.moviedetails;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.models.entities.Credit;
import khaledhaouas.com.tmdbmovies.models.entities.Movie;
import khaledhaouas.com.tmdbmovies.models.entities.Review;
import khaledhaouas.com.tmdbmovies.models.entities.Video;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnCreditListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnMovieLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnMoviesListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnReviewListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnVideoListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.repositories.CreditsRepos;
import khaledhaouas.com.tmdbmovies.models.repositories.MoviesRepos;
import khaledhaouas.com.tmdbmovies.models.repositories.ReviewsRepos;

public class MovieDetailsViewModel extends ViewModel {
    private static final String TAG = "MovieDetailsViewModel";
    private MoviesRepos mMovieRepos;
    private CreditsRepos mCreditRepos;
    private ReviewsRepos mReviewRepos;

    private int mMovieId;
    private Movie mMovie;
    private ArrayList<Credit> mCredits;
    private ArrayList<Review> mReviews;
    private ArrayList<Movie> mSimilarMovies;

    private int mCurrentSimilarMoviesPage;
    private int mTotalSimilarMoviesPage;

    private boolean isNextPageLoading = false;

    public MovieDetailsViewModel() {
        mMovieRepos = new MoviesRepos();
        mCreditRepos = new CreditsRepos();
        mReviewRepos = new ReviewsRepos();

        mReviews = new ArrayList<>();
        mCredits = new ArrayList<>();
        mSimilarMovies = new ArrayList<>();
    }

    public void getMovieDetails(final OnMovieLoadedCallback callback) {
        mMovieRepos.getMovieDetails(mMovieId, new OnMovieLoadedCallback() {
            @Override
            public void onSuccess(Movie movie) {
                mMovie = movie;
                callback.onSuccess(movie);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });

    }

    public void getMovieCreditList(final OnCreditListLoadedCallback callback) {
        mCreditRepos.getMovieCreditsList(mMovieId, new OnCreditListLoadedCallback() {
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

    public void getMovieReviewList(final OnReviewListLoadedCallback callback) {
        mReviewRepos.getMovieReviewsList(mMovieId, new OnReviewListLoadedCallback() {
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

    public void getSimilarMoviesList(final OnMoviesListLoadedCallback callback) {
        mMovieRepos.getSimilarMoviesList(mMovieId, new OnMoviesListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> movies, int totalPages) {
                mSimilarMovies.clear();
                mSimilarMovies.addAll(movies);
                mCurrentSimilarMoviesPage++;
                mTotalSimilarMoviesPage = totalPages;
                callback.onSuccess(movies, totalPages);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void getNextPageSimilarMoviesList(int id, final OnMoviesListLoadedCallback callback) {
        isNextPageLoading = true;
        mMovieRepos.getSimilarMoviesList(id, new OnMoviesListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> movies, int totalPages) {
                mSimilarMovies.addAll(movies);
                mCurrentSimilarMoviesPage++;
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

    public void getMovieVideosList(final OnVideoListLoadedCallback callback) {
        mMovieRepos.getMovieVideosList(mMovieId, new OnVideoListLoadedCallback() {
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

    public int getMovieId() {
        return mMovieId;
    }

    public void setMovieId(int mMovieId) {
        this.mMovieId = mMovieId;
    }

    public Movie getMovie() {
        return mMovie;
    }

    public ArrayList<Credit> getCredits() {
        return mCredits;
    }

    public ArrayList<Review> getmReviews() {
        return mReviews;
    }

    public ArrayList<Movie> getmSimilarMovies() {
        return mSimilarMovies;
    }

    public int getCurrentSimilarMoviesPage() {
        return mCurrentSimilarMoviesPage;
    }

    public int getTotalSimilarMoviesPage() {
        return mTotalSimilarMoviesPage;
    }

    public boolean isNextPageLoading() {
        return isNextPageLoading;
    }
}
