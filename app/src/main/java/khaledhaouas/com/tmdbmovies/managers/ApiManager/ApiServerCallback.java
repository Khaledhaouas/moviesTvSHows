package khaledhaouas.com.tmdbmovies.managers.ApiManager;

import org.json.JSONObject;

public interface ApiServerCallback {
    boolean onSuccess(JSONObject result);

    boolean onFailure(int statusCode);
}
