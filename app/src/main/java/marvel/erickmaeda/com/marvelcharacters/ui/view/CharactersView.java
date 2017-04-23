package marvel.erickmaeda.com.marvelcharacters.ui.view;

import android.view.View;

import java.util.List;

import marvel.erickmaeda.com.marvelcharacters.entities.Character;

public interface CharactersView extends marvel.erickmaeda.com.marvelcharacters.system.mvp.View {

    void setCharacters(List<Character> characters);

    void onError(String error);
}
