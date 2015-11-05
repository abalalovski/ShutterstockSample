package com.twodwarfs.shutterstock.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.twodwarfs.shutterstock.R;
import com.twodwarfs.shutterstock.model.BaseModel;
import com.twodwarfs.shutterstock.model.Category;
import com.twodwarfs.shutterstock.model.Image;
import com.twodwarfs.shutterstock.model.Response;
import com.twodwarfs.shutterstock.net.DownloadImageTask;
import com.twodwarfs.shutterstock.net.ApiRequestTask;
import com.twodwarfs.shutterstock.cons.Constants;
import com.twodwarfs.shutterstock.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandar Balalovski
 * <p/>
 * Images Adapter class that implements the logics of multi-type RecyclerView Adapter.
 * The logics is pretty clear, the only thing I find disturbing is that the
 * Search functionality will not work perfectly, it will just give you hint on the idea
 * how I intended to implement it.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> implements Filterable {

    /**
     * Main holder of all data
     **/
    private List<BaseModel> mData = new ArrayList();

    /**
     * Holds images only, used for search
     **/
    private List<Image> mImages = new ArrayList();

    /**
     * Initial unmodified data that serves as helper
     **/
    private List<BaseModel> mInitialData = new ArrayList<>();

    private Context mContext;

    /**
     * Counter for the categories
     **/
    private int mCounter = 0;

    public List<BaseModel> getItems() {
        return mData;
    }

    /**
     * My implementation of OnItemClickListener interface.
     * In our case we don't need this.
     */
    public interface IOnItemClick {
        void onItemClick(View v, int position);
    }

    static IOnItemClick mOnItemClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public View mRootView;

        public ViewHolder(View v) {
            super(v);
            mRootView = v;
            mRootView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    public ImagesAdapter() {
    }

    public void setOnItemClickListener(IOnItemClick listener) {
        mOnItemClickListener = listener;
    }

    /**
     * Get the Model type by the position
     **/
    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        /** neat and cosy. In case it's Image, create the ViewHolder with the image_item,
         * inflation, in case it's Category, with the category_item layout.
         */
        switch (viewType) {
            case Constants.ModelTypes.IMAGE:
                View v = inflater.inflate(R.layout.image_item, parent, false);
                return new ViewHolder(v);
            case Constants.ModelTypes.CATEGORY:
                View groupView = inflater.inflate(R.layout.group_item, parent, false);
                return new ViewHolder(groupView);
            default:
                View defView = inflater.inflate(R.layout.image_item, parent, false);
                return new ViewHolder(defView);
        }
    }

    /**
     * Make the call to the API for the images for the catId category
     *
     * @param catId Category id for which images should be loaded from API
     */
    private void loadImagesForCategory(final int catId) {
        if (Utils.hasActiveNetworkConnection(mContext)) {
            new ApiRequestTask("search?category=" + catId, new ApiRequestTask.OnResultListener() {
                @Override
                public void onResult(String json) {
                    Response r = Response.fromJson(json);
                    for (int i = 0; i < r.getImages().size(); i++) {
                        Image image = r.getImages().get(i);

                        int additionFactor = mCounter + catId + 1;
                        if (mData.size() > additionFactor) {
                            mData.add(additionFactor, image);
                        }

                        mImages.add(image);
                        mCounter += 1;
                    }

                    notifyDataSetChanged();
                }
            }).execute();
        } else {
            Utils.doToast(mContext, mContext.getString(R.string.no_connectivity));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        switch (viewType) {
            case Constants.ModelTypes.IMAGE:
                Image image = (Image) mData.get(position);
                ImageView imageView = (ImageView) holder.mRootView.findViewById(R.id.imageView_image);
                TextView textViewDescription = (TextView) holder.mRootView.findViewById(R.id.textView_description);
                textViewDescription.setText(image.getDesc());


                /** download the image for this position item **/
                new DownloadImageTask(mContext, imageView)
                        .execute(image.getAssets().getSmallThumb().getUrl());
                break;
            case Constants.ModelTypes.CATEGORY:
                Category c = (Category) mData.get(position);
                loadImagesForCategory(Integer.parseInt(c.getId()));

                TextView textViewCategoryName = (TextView) holder.mRootView.findViewById(R.id.textView_category);
                textViewCategoryName.setText(c.getName());
                break;
        }
    }

    public BaseModel getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setCategories(List<Category> categories) {
        mData.addAll(categories);
        mInitialData.addAll(categories);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new ImagesFilter();
    }

    /**
     * Filter for the Search functionality
     **/
    private class ImagesFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                results.values = mData;
                results.count = mData.size();
                mData = mInitialData;
            } else {
                List<Image> images = new ArrayList<>();
                for (Image image : mImages) {
                    if (image.getDesc().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        images.add(image);
                    }
                }

                results.values = images;
                results.count = images.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0) {
//                notifyDataSetInvalidated();
            } else {
                mData = (ArrayList<BaseModel>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}
