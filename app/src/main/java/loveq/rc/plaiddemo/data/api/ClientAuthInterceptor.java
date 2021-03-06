package loveq.rc.plaiddemo.data.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rc on 2017/12/25.
 * Description:
 */

public class ClientAuthInterceptor implements Interceptor {

    private final String accessToken;
    private final String clientId;
    private final boolean hasAccessToken;

    public ClientAuthInterceptor(@Nullable String accessToken, @NonNull String clientId) {
        if (!TextUtils.isEmpty(accessToken)) {
            this.accessToken = accessToken;
            hasAccessToken = true;
        } else {
            this.accessToken = null;
            hasAccessToken = false;
        }
        this.clientId = clientId;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        if (hasAccessToken) {
            requestBuilder.addHeader("Authorization", "Bearer" + accessToken);
        } else {
            HttpUrl url = chain.request().url().newBuilder()
                    .addQueryParameter("client_id", clientId)
                    .build();
            requestBuilder.url(url);
        }
        return chain.proceed(requestBuilder.build());
    }
}
