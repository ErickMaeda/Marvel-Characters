package marvel.erickmaeda.com.marvelcharacters.ui.activities;

import java.util.List;

import marvel.erickmaeda.com.marvelcharacters.entities.Character;

/**
 * Created by erick on 27/08/16.
 */
public interface CharactersView {

    void setCharacters(List<Character> characters);

    void onError(String error);
}
