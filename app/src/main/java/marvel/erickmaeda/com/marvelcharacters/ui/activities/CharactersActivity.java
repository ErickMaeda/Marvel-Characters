package marvel.erickmaeda.com.marvelcharacters.ui.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import marvel.erickmaeda.com.marvelcharacters.R;
import marvel.erickmaeda.com.marvelcharacters.databinding.CharactersActivityBinding;
import marvel.erickmaeda.com.marvelcharacters.entities.Character;
import marvel.erickmaeda.com.marvelcharacters.presenters.characters.CharactersPresenter;
import marvel.erickmaeda.com.marvelcharacters.presenters.characters.CharactersPresenterImpl;
import marvel.erickmaeda.com.marvelcharacters.system.mvp.PresenterHolder;
import marvel.erickmaeda.com.marvelcharacters.system.utils.Constants;
import marvel.erickmaeda.com.marvelcharacters.ui.adapters.CharactersAdapter;
import marvel.erickmaeda.com.marvelcharacters.ui.view.CharactersView;

public class CharactersActivity extends AppCompatActivity implements CharactersAdapter.OnLikeListener, CharactersView, SearchView.OnQueryTextListener, CharactersAdapter.OnClickListener, CharactersAdapter.OnLongClickListener {

    private String TAG = this.getClass().getSimpleName();
    private CharactersPresenter presenter;
    private SearchView searchView;
    private String lastItemSearched = "";
    private Toast toast;
    private CharactersActivityBinding binding;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_characters);
        setSupportActionBar(binding.toolbar);
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
        searchView.setQuery(lastItemSearched, false);
        searchView.setOnQueryTextListener(this);
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
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG, "onQueryTextSubmit: ");
        handler.removeCallbacksAndMessages(null);
        lastItemSearched = query;
        binding.pbCharacters.setVisibility(View.VISIBLE);
        if (query.length() > 0)
            presenter.loadCharacters(query);
        else
            presenter.loadCharacters();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(TAG, "onQueryTextChange: ");
        handler.removeCallbacksAndMessages(null);
        lastItemSearched = newText;
        if (newText.length() == 0) {
            binding.pbCharacters.setVisibility(View.VISIBLE);
            presenter.loadCharacters();
        } else {
            handler.postDelayed(() -> {
                binding.pbCharacters.setVisibility(View.VISIBLE);
                presenter.loadCharacters(newText);
            }, 500);
        }
        return false;
    }

    @Override
    public void setCharacters(List<Character> characters) {
        if (characters.isEmpty())
            showToast(getString(R.string.activity_characters_search_not_found, lastItemSearched));
        binding.pbCharacters.setVisibility(View.GONE);
        binding.recyclerView.setAdapter(new CharactersAdapter(characters, this, this, this));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        }
    }

    @Override
    public void onError(String error) {
        binding.pbCharacters.setVisibility(View.GONE);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.default_error));
        alert.setMessage(error);
        alert.setNeutralButton(getString(R.string.default_ok), null);
        alert.show();
    }

    @Override
    public void onItemClick(Character character) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Character.class.getSimpleName(), character);
        startActivity(intent);
        showToast(getString(R.string.activity_characters_on_item_click_message) + character.getName());
    }

    @Override
    public void onLongItemClick(Character character) {
        showToast(getString(R.string.activity_characters_on_item_long_click_message) + character.getName());
    }

    /**
     * Call this method if you want to show a simple info message.
     * Like "Internet Unnavailable" or "Connection error"
     * Don't worry this method will subscribe if has anyother showing.
     *
     * @param msg Message to show
     */
    public void showToast(String msg) {
        try {
            toast.getView().isShown();
            toast.setText(msg);
        } catch (Exception e) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    private void setLayoutManager() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        }
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

    @Override
    public void onLikeClick(Character character, boolean liked) {
        showToast(getString(R.string.activity_characters_on_like_clike) + character.getName() + " | Liked: " + liked);
    }
}
