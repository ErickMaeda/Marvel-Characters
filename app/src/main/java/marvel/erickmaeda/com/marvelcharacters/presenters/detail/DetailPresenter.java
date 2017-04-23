package marvel.erickmaeda.com.marvelcharacters.presenters.detail;

import marvel.erickmaeda.com.marvelcharacters.system.mvp.Presenter;
import marvel.erickmaeda.com.marvelcharacters.ui.view.CharactersView;
import marvel.erickmaeda.com.marvelcharacters.ui.view.DetailView;

public interface DetailPresenter extends Presenter {
    void setView(DetailView view);

    void loadCharacter(String name);
}
