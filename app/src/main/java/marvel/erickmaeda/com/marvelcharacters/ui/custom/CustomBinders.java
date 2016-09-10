package marvel.erickmaeda.com.marvelcharacters.ui.custom;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import marvel.erickmaeda.com.marvelcharacters.R;
import marvel.erickmaeda.com.marvelcharacters.entities.Thumbnail;
import marvel.erickmaeda.com.marvelcharacters.system.utils.Constants;

public class CustomBinders {

    private static final String TAG = CustomBinders.class.getSimpleName();

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, Thumbnail thumbnail) {
        Picasso.with(view.getContext())
                .load(thumbnail.getPath() + "/" + Constants.ImagesSize.PORTRAIT_MEDIUM + "." + thumbnail.getExtension())
                .placeholder(R.mipmap.ic_launcher)
                .error(android.R.drawable.stat_notify_error)
                .into(view);
    }
}
