package marvel.erickmaeda.com.marvelcharacters.ui.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import marvel.erickmaeda.com.marvelcharacters.ui.adapters.CharactersAdapter;

public class CharactersActivity extends AppCompatActivity implements CharactersView, SearchView.OnQueryTextListener, CharactersAdapter.OnClickListener, CharactersAdapter.OnLongClickListener {

    private String TAG = this.getClass().getSimpleName();
    private RecyclerView recyclerView;
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
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Constants.SavedInstanceState.Characters.LAST_ITEM_SEARCHED, String.valueOf(searchView.getQuery()));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query.length() > 0)
            presenter.loadCharacters(query);
        else
            presenter.loadCharacters();
        pbCharacters.setVisibility(View.VISIBLE);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length() == 0) {
            presenter.loadCharacters();
            pbCharacters.setVisibility(View.VISIBLE);
        }
        return false;
    }

    @Override
    public void setCharacters(List<Character> characters) {
        CharactersAdapter charactersAdapter = new CharactersAdapter(characters, this);
        pbCharacters.setVisibility(View.GONE);
        recyclerView.setAdapter(charactersAdapter);
        charactersAdapter.setOnClickListener(this);
        charactersAdapter.setOnLongClickListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        }
    }

    @Override
    public void onError(String error) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.default_error));
        alert.setMessage(error);
        alert.setNeutralButton(getString(R.string.default_ok), null);
        alert.show();
    }

    @Override
    public void onItemClick(Character character) {
        Log.d(TAG, "onItemClick: " + character.getName());
        showToast("The character clicked was: " + character.getName());
    }

    @Override
    public void onLongItemClick(Character character) {
        Log.d(TAG, "onLongItemClick: " + character.getName());
        showToast("The character long clicked was: " + character.getName());
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
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        }
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.charactersView);
        pbCharacters = (ProgressBar) findViewById(R.id.pbCharacters);
    }

    /**
     * Method responsible to create the presenter or use the old one.
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
