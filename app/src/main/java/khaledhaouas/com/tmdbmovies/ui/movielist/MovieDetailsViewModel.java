package khaledhaouas.com.tmdbmovies.ui.movielist;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.models.entities.Credit;
import khaledhaouas.com.tmdbmovies.models.entities.Movie;
import khaledhaouas.com.tmdbmovies.models.entities.Review;
import khaledhaouas.com.tmdbmovies.models.entities.Video;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnCreditListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnMovieLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnReviewListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnSimilarMoviesListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnVideoListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.repositories.CreditsRepos;
import khaledhaouas.com.tmdbmovies.models.repositories.MoviesRepos;
import khaledhaouas.com.tmdbmovies.models.repositories.ReviewsRepos;

public class MovieDetailsViewModel extends ViewModel {
    private static final String TAG = "MovieDetailsViewModel";
    private MoviesRepos mMovieRepos;
    private CreditsRepos mCreditRepos;
    private ReviewsRepos mReviewRepos;

    private Movie mMovie;
    private ArrayList<Credit> mCredits;
    private ArrayList<Review> mReviews;
    private ArrayList<Movie> mSimilarMovies;

    public MovieDetailsViewModel() {
        mMovieRepos = new MoviesRepos();
        mCreditRepos = new CreditsRepos();
        mReviewRepos = new ReviewsRepos();

        mReviews = new ArrayList<>();
        mCredits = new ArrayList<>();
        mSimilarMovies = new ArrayList<>();
    }

    public void getMovieDetails(int id, final OnMovieLoadedCallback callback) {
        mMovieRepos.getMovieDetails(id, new OnMovieLoadedCallback() {
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

    public void getMovieCreditList(int id, final OnCreditListLoadedCallback callback) {
        mCreditRepos.getMovieCreditsList(id, new OnCreditListLoadedCallback() {
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

    public void getMovieReviewList(int id, final OnReviewListLoadedCallback callback) {
        mReviewRepos.getMovieReviewsList(id, new OnReviewListLoadedCallback() {
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

    public void getSimilarMoviesList(int id, final OnSimilarMoviesListLoadedCallback callback) {
        mMovieRepos.getSimilarMoviesList(id, new OnSimilarMoviesListLoadedCallback() {
            @Override
            public void onSuccess(ArrayList<Movie> movies) {
                mSimilarMovies.clear();
                mSimilarMovies.addAll(movies);
                callback.onSuccess(movies);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public void getMovieVideosList(int id, final OnVideoListLoadedCallback callback) {
        mMovieRepos.getMovieVideosList(id, new OnVideoListLoadedCallback() {
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
}
