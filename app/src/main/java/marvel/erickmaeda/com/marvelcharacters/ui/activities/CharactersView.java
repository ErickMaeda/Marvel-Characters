package marvel.erickmaeda.com.marvelcharacters.ui.activities;

import java.util.List;

import marvel.erickmaeda.com.marvelcharacters.entities.Character;

public interface CharactersView {

    void setCharacters(List<Character> characters);

    void onError(String error);
}
