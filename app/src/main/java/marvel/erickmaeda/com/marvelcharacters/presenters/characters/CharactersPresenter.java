package marvel.erickmaeda.com.marvelcharacters.presenters.characters;

import marvel.erickmaeda.com.marvelcharacters.system.mvp.Presenter;
import marvel.erickmaeda.com.marvelcharacters.ui.view.CharactersView;

public interface CharactersPresenter extends Presenter {
    void setView(CharactersView charactersView);

    void loadCharacters(String nameStartsWith);

    void loadCharacters();
}
