package marvel.erickmaeda.com.marvelcharacters.system.utils;

import android.util.Log;

import java.util.concurrent.Callable;

import rx.Observable;

public class RxUtils {

    private static final String TAG = RxUtils.class.getName();

    /**
     * Class responsible to create the async calls.
     * @param func
     * @param <T>
     * @return
     */
    public static <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(subscriber -> {
                    try {
                        subscriber.onNext(func.call());
                    } catch (Exception ex) {
                        Log.e(TAG, "RxError", ex);
                    }
                }
        );
    }
}