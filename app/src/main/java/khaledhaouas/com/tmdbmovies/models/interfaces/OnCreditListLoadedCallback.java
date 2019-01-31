package khaledhaouas.com.tmdbmovies.models.interfaces;

import java.util.ArrayList;

import khaledhaouas.com.tmdbmovies.models.entities.Credit;

public interface OnCreditListLoadedCallback {
    void onSuccess(ArrayList<Credit> credits);

    void onError();
}
