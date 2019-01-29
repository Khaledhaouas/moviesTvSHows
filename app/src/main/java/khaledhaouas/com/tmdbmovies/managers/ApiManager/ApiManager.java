package khaledhaouas.com.tmdbmovies.managers.ApiManager;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by khaledhoues on 2017/08/03.
 */

public class ApiManager {
    private static final String TAG = "ApiManager";

    private static ApiManager sInstance = null;

    public static ApiManager getsInstance() {
        return sInstance == null ? new ApiManager() : sInstance;
    }

    private ApiManager() {
    }

    public void GET(final String url, final ApiServerCallback callback) {
        try {
            HttpsTrustManager.allowAllSSL();
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    url, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e(TAG, response.toString());


                            try {
                                callback.onSuccess(response);
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback.onFailure(0);
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        callback.onFailure(error.networkResponse.statusCode);

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailure(0);
                    }
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
//                    headers.put("Authorization", token);

                    return headers;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    return params;
                }
            };
            int x = 2;
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                    x, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleyManager.getInstance().addToRequestQueue(jsonObjReq, "tag");
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure(0);
        }

    }

    public void DELETE(final String url, final ApiServerCallback callback) {
        try {
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.DELETE,
                    url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e(TAG, response.toString());

                            try {
                                callback.onSuccess(response);
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback.onFailure(0);
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        callback.onFailure(error.networkResponse.statusCode);

                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailure(0);
                    }

                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    headers.put("Accept", "application/json");
//                    headers.put("Authorization", token);

                    return headers;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    return params;
                }
            };
            int x = 2;
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                    x, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleyManager.getInstance().addToRequestQueue(jsonObjReq, "tag");
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure(0);
        }

    }

    public void POST(final String url, final JSONObject jsonObject, final ApiServerCallback callback) {
        try {
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e(TAG, response.toString());

                            try {
                                callback.onSuccess(response);
                            } catch (Exception e) {
                                e.printStackTrace();
                                callback.onFailure(0);
                            }

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    try {
                        callback.onFailure(error.networkResponse.statusCode);
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailure(0);
                    }
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    headers.put("Accept", "application/json");
//                    headers.put("Authorization", token);

                    return headers;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    return params;
                }
            };
            int x = 2;
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                    x, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleyManager.getInstance().addToRequestQueue(jsonObjReq, "tag");
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure(0);
        }

    }
}
