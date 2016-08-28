package marvel.erickmaeda.com.marvelcharacters.presenters.characters;

import marvel.erickmaeda.com.marvelcharacters.system.mvp.Presenter;
import marvel.erickmaeda.com.marvelcharacters.ui.activities.CharactersView;

public interface CharactersPresenter extends Presenter {
    void create();

    void setView(CharactersView charactersView);

    void loadCharacters(String nameStartsWith);

    void loadCharacters();
}
