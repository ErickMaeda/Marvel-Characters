package marvel.erickmaeda.com.marvelcharacters.retrofit;

import marvel.erickmaeda.com.marvelcharacters.BuildConfig;
import marvel.erickmaeda.com.marvelcharacters.retrofit.api.MarvelApi;
import marvel.erickmaeda.com.marvelcharacters.system.utils.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAdapterProvider {

    private MarvelApi api;

    public RestAdapterProvider(){
        this.api = buildMarvelApi();
    }

    private MarvelApi buildMarvelApi() {
        return build(MarvelApi.class);
    }

    public <T> T build(final Class<T> apiClass) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        if(BuildConfig.DEBUG){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(interceptor);
        }
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constants.Settings.MAIN_URL)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(apiClass);
    }

    public MarvelApi getApi() {
        return api;
    }
}
