package marvel.erickmaeda.com.marvelcharacters.ui.view;

import java.util.List;
import java.util.StringJoiner;

import marvel.erickmaeda.com.marvelcharacters.entities.Character;

public interface DetailView {
    void setCharacter(Character character);

    void setToolbarTitle(String title);
}
