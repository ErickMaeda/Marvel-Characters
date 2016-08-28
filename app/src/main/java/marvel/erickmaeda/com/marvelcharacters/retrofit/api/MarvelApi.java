package marvel.erickmaeda.com.marvelcharacters.retrofit.api;

import marvel.erickmaeda.com.marvelcharacters.retrofit.entities.character_response.ResponseCharacter;
import marvel.erickmaeda.com.marvelcharacters.system.utils.Constants;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface MarvelApi {

    @GET(Constants.Settings.CHARACTERS_URL)
    Call<ResponseCharacter> getCharacterWhereNameStartsWith(@Query("nameStartsWith") String nameStartsWith, @Query("hash") String hash, @Query("ts") String currentTimeMillis);

    @GET(Constants.Settings.CHARACTERS_URL)
    Observable<ResponseCharacter> getCharacterWhereNameStartsWithObservable(@Query("nameStartsWith") String nameStartsWith, @Query("hash") String hash, @Query("ts") String currentTimeMillis);

    @GET(Constants.Settings.CHARACTERS_URL)
    Call<ResponseCharacter> getCharacters(@Query("hash") String hash, @Query("ts") String currentTimeMillis);

    @GET(Constants.Settings.CHARACTERS_URL)
    Observable<ResponseCharacter> getCharactersObservable(@Query("hash") String hash, @Query("ts") String currentTimeMillis);
}
