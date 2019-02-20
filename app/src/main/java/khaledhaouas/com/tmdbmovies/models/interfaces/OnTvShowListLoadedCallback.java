package khaledhaouas.com.tmdbmovies.models.interfaces;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.models.entities.TvShow;

public interface OnTvShowListLoadedCallback {
    void onSuccess(ArrayList<TvShow> shows, int totalPages);

    void onError();
}
