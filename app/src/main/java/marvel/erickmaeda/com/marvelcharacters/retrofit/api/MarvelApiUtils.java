package marvel.erickmaeda.com.marvelcharacters.retrofit.api;

import marvel.erickmaeda.com.marvelcharacters.system.utils.Constants;
import marvel.erickmaeda.com.marvelcharacters.system.utils.HashUtils;

public class MarvelApiUtils {

    public static String mountHash(long timeMillis) {
        return HashUtils.md5(timeMillis + Constants.Keys.PRIVATE_KEY + Constants.Keys.PUBLIC_KEY);
    }
}
