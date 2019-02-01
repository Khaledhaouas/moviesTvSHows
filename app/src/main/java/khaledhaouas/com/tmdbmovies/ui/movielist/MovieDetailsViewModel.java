package khaledhaouas.com.tmdbmovies.ui.movielist;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.models.entities.Credit;
import khaledhaouas.com.tmdbmovies.models.entities.Movie;
import khaledhaouas.com.tmdbmovies.models.entities.Review;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnCreditListLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnMovieLoadedCallback;
import khaledhaouas.com.tmdbmovies.models.interfaces.OnReviewListLoadedCallback;
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

    public MovieDetailsViewModel() {
        mMovieRepos = new MoviesRepos();
        mCreditRepos = new CreditsRepos();
        mReviewRepos = new ReviewsRepos();

        mReviews = new ArrayList<>();
        mCredits = new ArrayList<>();
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

    public Movie getMovie() {
        return mMovie;
    }

    public ArrayList<Credit> getCredits() {
        return mCredits;
    }

    public ArrayList<Review> getmReviews() {
        return mReviews;
    }
}
