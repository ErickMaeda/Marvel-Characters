package marvel.erickmaeda.com.marvelcharacters.presenters.detail;

import java.io.IOException;
import java.util.List;

import marvel.erickmaeda.com.marvelcharacters.entities.Character;
import marvel.erickmaeda.com.marvelcharacters.retrofit.RestAdapterProvider;
import marvel.erickmaeda.com.marvelcharacters.retrofit.api.MarvelApi;
import marvel.erickmaeda.com.marvelcharacters.retrofit.api.MarvelApiUtils;
import marvel.erickmaeda.com.marvelcharacters.retrofit.entities.character_response.ResponseCharacter;
import marvel.erickmaeda.com.marvelcharacters.system.utils.RxUtils;
import marvel.erickmaeda.com.marvelcharacters.ui.view.CharactersView;
import marvel.erickmaeda.com.marvelcharacters.ui.view.DetailView;
import retrofit2.Response;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailPresenterImpl implements DetailPresenter {

    private DetailView view;
    private Character character;
    private Subscription subscription;

    public DetailPresenterImpl(DetailView view) {
        this.view = view;
    }

    @Override
    public void create() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void setView(DetailView view) {
    }

    @Override
    public void loadCharacter(String name) {

    }
}
