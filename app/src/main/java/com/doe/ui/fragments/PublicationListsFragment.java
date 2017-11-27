package com.doe.ui.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doe.R;
import com.doe.models.Donation;

import java.util.ArrayList;

public class PublicationListsFragment extends Fragment {

    private ArrayList<Donation> donationLists;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publication_lists, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.publication_list);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);


        return view;
    }

}
