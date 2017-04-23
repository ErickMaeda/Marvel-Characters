package marvel.erickmaeda.com.marvelcharacters.presenters.characters;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import marvel.erickmaeda.com.marvelcharacters.entities.Character;
import marvel.erickmaeda.com.marvelcharacters.retrofit.RestAdapterProvider;
import marvel.erickmaeda.com.marvelcharacters.retrofit.api.MarvelApi;
import marvel.erickmaeda.com.marvelcharacters.retrofit.api.MarvelApiUtils;
import marvel.erickmaeda.com.marvelcharacters.retrofit.entities.character_response.ResponseCharacter;
import marvel.erickmaeda.com.marvelcharacters.system.utils.RxUtils;
import marvel.erickmaeda.com.marvelcharacters.ui.view.CharactersView;
import retrofit2.Response;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CharactersPresenterImpl implements CharactersPresenter {

    private static final String TAG = CharactersPresenterImpl.class.getSimpleName();
    private CharactersView view;
    private List<Character> characters;
    private MarvelApi api;
    private Subscription subscription;

    public CharactersPresenterImpl(CharactersView view) {
        this.view = view;
        this.api = new RestAdapterProvider().getApi();
    }

    @Override
    public void create() {
        if (characters == null || characters.size() == 0){
            Log.d(TAG, "create: Characters Empty");
            loadCharacters();
        }
    }

    @Override
    public void destroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void setView(CharactersView view) {
        this.view = view;
        if (characters != null)
            view.setCharacters(characters);
    }

    @Override
    public void loadCharacters(String nameStartsWith) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = RxUtils.makeObservable(() -> {
            Response<ResponseCharacter> response = null;
            try {
                long currentTimeMillis = System.currentTimeMillis();
                response = api.getCharacterWhereNameStartsWith(nameStartsWith, MarvelApiUtils.mountHash(currentTimeMillis), String.valueOf(currentTimeMillis)).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((response) -> {
                    if (response.isSuccessful()) {
                        view.setCharacters(response.body().getData().getResults());
                        this.characters = response.body().getData().getResults();
                    } else {
                        view.onError(response.code() + " | " + response.errorBody());
                    }
                }, (Throwable::printStackTrace));
    }

    @Override
    public void loadCharacters() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = RxUtils.makeObservable(() -> {
            Response<ResponseCharacter> response = null;
            try {
                long currentTimeMillis = System.currentTimeMillis();
                response = api.getCharacters(MarvelApiUtils.mountHash(currentTimeMillis), String.valueOf(currentTimeMillis)).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((response) -> {
                    if (response.isSuccessful()) {
                        view.setCharacters(response.body().getData().getResults());
                        this.characters = response.body().getData().getResults();
                    } else {
                        view.onError(response.code() + " | " + response.message());
                    }
                }, (Throwable::printStackTrace));
    }
}
