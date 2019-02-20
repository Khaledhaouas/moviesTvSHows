package khaledhaouas.com.tmdbmovies.models.interfaces;

import khaledhaouas.com.tmdbmovies.models.entities.TvShow;

public interface OnTvShowLoadedCallback {
    void onSuccess(TvShow show);

    void onError();
}
