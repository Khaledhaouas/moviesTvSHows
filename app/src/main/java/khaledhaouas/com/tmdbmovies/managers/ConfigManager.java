package khaledhaouas.com.tmdbmovies.managers;

public class ConfigManager {
    private String mProtocol = "https";
    private String mHost = "api.themoviedb.org";
    private String mApiVersion = "3";
    private String mApiKey = "d55cd758f9f54f8e3b6741901424c964";
    private static final ConfigManager ourInstance = new ConfigManager();

    public static ConfigManager getInstance() {
        return ourInstance;
    }

    private ConfigManager() {

    }

    public String getHost() {
        return mHost;
    }

    public String getApiKey() {
        return mApiKey;
    }

    public String getApiVersion() {
        return mApiVersion;
    }

    public String getAppRoot() {
        return mProtocol + "://" + mHost + "/" + mApiVersion + "/";
    }

    public String addApiKeyToRequest() {
        return "?api_key=" + mApiKey;
    }

}
