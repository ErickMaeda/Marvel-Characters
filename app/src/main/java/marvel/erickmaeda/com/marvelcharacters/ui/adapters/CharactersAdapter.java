package marvel.erickmaeda.com.marvelcharacters.ui.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

import marvel.erickmaeda.com.marvelcharacters.R;
import marvel.erickmaeda.com.marvelcharacters.databinding.CharactersAdapterBinding;
import marvel.erickmaeda.com.marvelcharacters.entities.Character;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder> {

    private List<Character> characters;
    private static OnClickListener onClickListener;
    private static OnLongClickListener onLongClickListener;
    private static OnLikeListener onLikeLister;

    public CharactersAdapter(List<Character> characters) {
        this.characters = characters;
    }

    public CharactersAdapter(List<Character> characters, OnClickListener clickListener) {
        this.characters = characters;
        this.setOnClickListener(clickListener);
    }

    public CharactersAdapter(List<Character> characters, OnClickListener clickListener, OnLongClickListener longClickListener) {
        this.characters = characters;
        this.setOnClickListener(clickListener);
        this.setOnLongClickListener(longClickListener);
    }

    public CharactersAdapter(List<Character> characters, OnClickListener clickListener, OnLongClickListener longClickListener, OnLikeListener likeListener) {
        this.characters = characters;
        this.setOnClickListener(clickListener);
        this.setOnLongClickListener(longClickListener);
        this.setOnLikeListener(likeListener);
    }

    @Override
    public CharactersAdapter.CharactersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CharactersViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_characters, parent, false));
    }

    @Override
    public void onBindViewHolder(CharactersAdapter.CharactersViewHolder holder, int position) {
        holder.binding.setItem(characters.get(position));
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        CharactersAdapter.onClickListener = onClickListener;
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        CharactersAdapter.onLongClickListener = onLongClickListener;
    }

    public void setOnLikeListener(OnLikeListener onLikeLister) {
        CharactersAdapter.onLikeLister = onLikeLister;
    }

    public interface OnClickListener {
        void onItemClick(Character character);
    }

    public interface OnLongClickListener {
        void onLongItemClick(Character character);
    }

    public interface OnLikeListener {
        void onLikeClick(Character character, boolean liked);
    }

    public class CharactersViewHolder extends RecyclerView.ViewHolder implements com.like.OnLikeListener, View.OnClickListener, View.OnLongClickListener {

        private CharactersAdapterBinding binding;

        public CharactersViewHolder(View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            binding.ivPoster.setOnClickListener(this);
            binding.ivPoster.setOnLongClickListener(this);
            binding.ivLike.setOnLikeListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onClickListener != null) {
                onClickListener.onItemClick(characters.get(getAdapterPosition()));
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (onLongClickListener != null) {
                onLongClickListener.onLongItemClick(characters.get(getAdapterPosition()));
            }
            return false;
        }

        @Override
        public void liked(LikeButton likeButton) {
            if (onLikeLister != null) {
                onLikeLister.onLikeClick(characters.get(getAdapterPosition()), true);
            }
        }

        @Override
        public void unLiked(LikeButton likeButton) {
            if (onLikeLister != null) {
                onLikeLister.onLikeClick(characters.get(getAdapterPosition()), false);
            }
        }
    }
}
