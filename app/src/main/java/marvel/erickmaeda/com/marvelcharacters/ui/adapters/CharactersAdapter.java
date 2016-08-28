package marvel.erickmaeda.com.marvelcharacters.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import marvel.erickmaeda.com.marvelcharacters.R;
import marvel.erickmaeda.com.marvelcharacters.entities.Character;
import marvel.erickmaeda.com.marvelcharacters.system.utils.Constants;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Character> characters;
    private Context context;
    private static OnClickListener onClickListener;
    private static OnLongClickListener onLongClickListener;

    public CharactersAdapter(List<Character> characters, Context context) {
        this.characters = characters;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public CharactersAdapter.CharactersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CharactersViewHolder(layoutInflater.inflate(R.layout.adapter_characters, parent, false));
    }

    @Override
    public void onBindViewHolder(CharactersAdapter.CharactersViewHolder holder, int position) {
        Character actual = characters.get(position);
        holder.tvName.setText(actual.getName());
        holder.tvDescription.setText(actual.getDescription());

        Picasso.with(context)
                .load(actual.getThumbnail().getPath() + "/" + Constants.ImagesSize.PORTRAIT_INCREDIBLE + "." + actual.getThumbnail().getExtension())
                .placeholder(R.mipmap.ic_launcher)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.ivPoster);
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

    public interface OnClickListener {
        void onItemClick(Character character);
    }

    public interface OnLongClickListener {
        void onLongItemClick(Character character);
    }

    public class CharactersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public ImageView ivPoster;
        public TextView tvName;
        public TextView tvDescription;

        public CharactersViewHolder(View itemView) {
            super(itemView);

            ivPoster = (ImageView) itemView.findViewById(R.id.ivPoster);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
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
    }
}
