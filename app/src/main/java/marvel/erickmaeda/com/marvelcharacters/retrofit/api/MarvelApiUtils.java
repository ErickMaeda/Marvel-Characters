package marvel.erickmaeda.com.marvelcharacters.retrofit.api;

import marvel.erickmaeda.com.marvelcharacters.system.utils.Constants;
import marvel.erickmaeda.com.marvelcharacters.system.utils.HashUtils;

/**
 * Created by erick on 27/08/16.
 */
public class MarvelApiUtils {

    public static long lastCurrentTimeMounted = 0;

    public static String mountHash() {
        lastCurrentTimeMounted = System.currentTimeMillis();
        return HashUtils.md5(lastCurrentTimeMounted + Constants.Keys.PRIVATE_KEY + Constants.Keys.PUBLIC_KEY);
    }
}
