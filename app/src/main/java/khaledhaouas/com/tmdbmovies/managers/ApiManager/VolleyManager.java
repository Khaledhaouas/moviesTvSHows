package khaledhaouas.com.tmdbmovies.managers.ApiManager;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by khaledhoues on 2017/08/02.
 */

public class VolleyManager extends Application {
    public static final String TAG = VolleyManager.class
            .getSimpleName();
    private static final int DEFAULT_NETWORK_THREAD_POOL_SIZE = 4;
    private static VolleyManager mInstance;
    private RequestQueue mRequestQueue;

    public static synchronized VolleyManager getInstance() {

        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getBaseContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
