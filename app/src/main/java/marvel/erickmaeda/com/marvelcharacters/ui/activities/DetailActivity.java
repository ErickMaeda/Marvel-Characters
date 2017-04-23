package marvel.erickmaeda.com.marvelcharacters.ui.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import marvel.erickmaeda.com.marvelcharacters.R;
import marvel.erickmaeda.com.marvelcharacters.databinding.DetailActivityBinding;
import marvel.erickmaeda.com.marvelcharacters.entities.Character;
import marvel.erickmaeda.com.marvelcharacters.ui.view.DetailView;

public class DetailActivity extends AppCompatActivity implements DetailView {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private DetailActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        setCharacter((Character) getIntent().getSerializableExtra(Character.class.getSimpleName()));
        setToolbarTitle(((Character) getIntent().getSerializableExtra(Character.class.getSimpleName())).getName());
    }

    @Override
    public void setCharacter(Character character) {
        Log.d(TAG, "setCharacter: "+character.toString());
        binding.setItem(character);
    }

    @Override
    public void setToolbarTitle(String title) {
        binding.toolbar.setTitle(title);
        setSupportActionBar(binding.toolbar);
    }
}
