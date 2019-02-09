package khaledhaouas.com.tmdbmovies.models.interfaces;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.models.entities.Review;

public interface OnReviewListLoadedCallback {
    void onSuccess(ArrayList<Review> reviews);

    void onError();
}
