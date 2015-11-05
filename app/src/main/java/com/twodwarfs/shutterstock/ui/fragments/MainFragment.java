package com.twodwarfs.shutterstock.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.twodwarfs.shutterstock.R;
import com.twodwarfs.shutterstock.ui.adapters.ImagesAdapter;
import com.twodwarfs.shutterstock.model.Categories;
import com.twodwarfs.shutterstock.model.Image;
import com.twodwarfs.shutterstock.net.ApiRequestTask;
import com.twodwarfs.shutterstock.utils.Logger;
import com.twodwarfs.shutterstock.utils.Utils;

/**
 * Created by Aleksandar Balalovski
 *
 * Main Fragment, all logics is here.
 */

public class MainFragment extends Fragment {

    private static final String CATEGORIES = "categories";

    private RecyclerView mRecyclerView;
    private ImagesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        setHasOptionsMenu(true);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_images);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ImagesAdapter();
        mAdapter.setOnItemClickListener(new ImagesAdapter.IOnItemClick() {
            @Override
            public void onItemClick(View v, int position) {
                Image image = (Image) mAdapter.getItem(position);
                if (image != null) {
                    Utils.doToast(getActivity(), image.getDesc());
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        if(Utils.hasActiveNetworkConnection(getActivity())) {
            new ApiRequestTask(CATEGORIES, new ApiRequestTask.OnResultListener() {
                @Override
                public void onResult(String json) {
                    Categories c = Categories.fromJson(json);
                    mAdapter.setCategories(c.getCategories());
                }
            }).execute();
        }
        else {
            Utils.doToast(getActivity(), getString(R.string.no_connectivity));
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        SearchView searchView = (SearchView) MenuItemCompat.
                getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(mOnQueryTextListener);
    }

    /**
     * Search query text listener
     */
    private SearchView.OnQueryTextListener mOnQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            mAdapter.getFilter().filter(newText.toString());
            return true;
        }
    };
}
