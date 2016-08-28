package marvel.erickmaeda.com.marvelcharacters.ui.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import marvel.erickmaeda.com.marvelcharacters.R;
import marvel.erickmaeda.com.marvelcharacters.entities.Character;
import marvel.erickmaeda.com.marvelcharacters.presenters.characters.CharactersPresenter;
import marvel.erickmaeda.com.marvelcharacters.presenters.characters.CharactersPresenterImpl;
import marvel.erickmaeda.com.marvelcharacters.system.mvp.PresenterHolder;
import marvel.erickmaeda.com.marvelcharacters.system.utils.Constants;

public class CharactersActivity extends AppCompatActivity implements CharactersView, SearchView.OnQueryTextListener {

    private String TAG = this.getClass().getSimpleName();
    private Toolbar toolbar;
    private CharactersPresenter presenter;
    private SearchView searchView;
    private ProgressBar pbCharacters;
    private String lastItemSearched = "";
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters);
        findViews();
        setSupportActionBar(toolbar);
        setLayoutManager();
        presenter = createPresenter();
        presenter.create();
        if (savedInstanceState != null)
            lastItemSearched = savedInstanceState.getString(Constants.SavedInstanceState.Characters.LAST_ITEM_SEARCHED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_characters, menu);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(this);
        searchView.setQuery(lastItemSearched, false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(Constants.SavedInstanceState.Characters.LAST_ITEM_SEARCHED, String.valueOf(searchView.getQuery()));
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        pbCharacters.setVisibility(View.VISIBLE);
        if (query.length() > 0)
            presenter.loadCharacters(query);
        else
            presenter.loadCharacters();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length() == 0) {
            pbCharacters.setVisibility(View.VISIBLE);
            presenter.loadCharacters();
        }
        return false;
    }

    @Override
    public void setCharacters(List<Character> characters) {
        pbCharacters.setVisibility(View.GONE);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

        }
    }

    @Override
    public void onError(String error) {
        pbCharacters.setVisibility(View.GONE);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.default_error));
        alert.setMessage(error);
        alert.setNeutralButton(getString(R.string.default_ok), null);
        alert.show();
    }

    public void showToast(String msg) { //"Toast toast" is declared in the class
        try {
            toast.getView().isShown();     // true if visible
            toast.setText(msg);
        } catch (Exception e) {         // invisible if exception
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        }
        toast.show();  //finally display it
    }

    private void setLayoutManager() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

        }
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        pbCharacters = (ProgressBar) findViewById(R.id.pbCharacters);
    }

    /**
     * Method responsible to create the presenter or use the old one.
     *
     * @return current presenter
     */
    private CharactersPresenter createPresenter() {
        presenter = PresenterHolder.getInstance().getPresenter(CharactersPresenter.class);
        if (presenter == null) {
            presenter = new CharactersPresenterImpl(this);
            PresenterHolder.getInstance().putPresenter(CharactersPresenter.class, presenter);
        } else {
            presenter.setView(this);
        }
        return presenter;
    }
}
