package loveq.rc.plaiddemo.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import loveq.rc.plaiddemo.BuildConfig;
import loveq.rc.plaiddemo.data.api.ClientAuthInterceptor;
import loveq.rc.plaiddemo.data.api.DenvelopingConverter;
import loveq.rc.plaiddemo.data.api.designernews.DesignerNewsService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rc on 2017/12/25.
 * Description:
 */

public class DesignerNewsPrefs {
    private static volatile DesignerNewsPrefs singleton;
    private static final String DESIGNER_NEWS_PREF = "DESIGNER_NEWS_PREF";
    private final SharedPreferences prefs;
    private String accessToken;
    private static final String KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN";
    private boolean isLoggedIn = false;
    private static final String KEY_USER_ID = "KEY_USER_ID";
    private static final String KEY_USER_NAME = "KEY_USER_NAME";
    private static final String KEY_USER_AVATAR = "KEY_USER_AVATAR";
    private long userId;
    private String username;
    private String userAvatar;
    private DesignerNewsService api;


    public static DesignerNewsPrefs get(Context context) {
        if (singleton == null) {
            synchronized (DesignerNewsPrefs.class) {
                singleton = new DesignerNewsPrefs(context);
            }
        }
        return singleton;
    }

    private DesignerNewsPrefs(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(DESIGNER_NEWS_PREF, Context
                .MODE_PRIVATE);
        accessToken = prefs.getString(KEY_ACCESS_TOKEN, null);
        isLoggedIn = !TextUtils.isEmpty(accessToken);
        if (isLoggedIn) {
            userId = prefs.getLong(KEY_USER_ID, 0l);
            username = prefs.getString(KEY_USER_NAME, null);
            userAvatar = prefs.getString(KEY_USER_AVATAR, null);
        }
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public DesignerNewsService getApi() {
        if (api == null) createApi();
        return api;
    }

    private void createApi() {
        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(
                        new ClientAuthInterceptor(accessToken, BuildConfig.DESIGNER_NEWS_CLIENT_ID))
                .build();
        final Gson gson = new Gson();
        api = new Retrofit.Builder()
                .baseUrl(DesignerNewsService.ENDPOINT)
                .client(client)
                .addConverterFactory(new DenvelopingConverter(gson))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(DesignerNewsService.class);
    }
}
