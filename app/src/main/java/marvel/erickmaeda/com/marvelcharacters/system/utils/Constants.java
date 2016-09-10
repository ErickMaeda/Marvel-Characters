package marvel.erickmaeda.com.marvelcharacters.system.utils;

public class Constants {

    public static final class Keys {
        /**
         * Put here your private and public keys generated on http://developer.marvel.com/
         */
        public static final String PRIVATE_KEY = "df8b1cee286d615e1a942ec6a4313b5a05f1b8a3";
        public static final String PUBLIC_KEY = "d213fcd0a1944fb4b318f2c2eeefa2a2";
    }

    public class Settings {
        /**
         * Main URL from API http://developer.marvel.com/
         * if the version changes please change here the URL
         */
        public static final String MAIN_URL = "https://gateway.marvel.com:443/v1/public/";
        public static final String CHARACTERS_URL = MAIN_URL + "characters?apikey=" + Keys.PUBLIC_KEY;
    }

    public static final class ImagesSize {

        /**
         * Image sizes from API http://developer.marvel.com/
         */
        public static final String PORTRAIT_SMALL = "portrait_small";
        public static final String PORTRAIT_MEDIUM = "portrait_medium";
        public static final String PORTRAIT_XLARGE = "portrait_xlarge";
        public static final String PORTRAIT_FANTASTIC = "portrait_fantastic";
        public static final String PORTRAIT_UNCANNY = "portrait_uncanny";
        public static final String PORTRAIT_INCREDIBLE = "portrait_incredible";
    }

    public static final class SavedInstanceState {
        /**
         * This class is responsible to record the keys about SavedInstanceState
         */
        public static final class Characters {
            public static final String LAST_ITEM_SEARCHED = "last_item_searched";
        }
    }
}
