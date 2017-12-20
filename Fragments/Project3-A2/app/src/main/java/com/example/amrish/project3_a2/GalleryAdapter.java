package com.example.amrish.project3_a2;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Amrish on 31-Oct-17.
 */

public class GalleryAdapter extends BaseAdapter {
    private Context context;

    /**
     * List of ids of the images
     */
    private final int[] images = {R.drawable.willis, R.drawable.msic, R.drawable.fmnh, R.drawable.navypier, R.drawable.jhc, R.drawable.sa};

    public GalleryAdapter(Context context) {
        this.context = context;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return images.length;
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return images[position];
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return images[position];
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView = null;
        if (convertView == null) {

            //Creating image view
            imageView = new ImageView(context);

            //reducing the resolution to save on size
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;

            //Using Bitmap for resizing the thumbnails, so has to reduce the memory usages
            imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),
                    images[position], options));

            //setting the scale type
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            //Layout of the view
            imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500));

        } else {
            //old view can be reused
            imageView = (ImageView) convertView;
        }
        return imageView;
    }
}
