package peladafc.com.murallocalizacao.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import peladafc.com.murallocalizacao.R;
import peladafc.com.murallocalizacao.adapters.CampusAdapter;

/**
 * Created by AIRES on 26/06/2015.
 */
public class CampusFragment extends Fragment {
    CampusAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView)getActivity().findViewById(R.id.my_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        adapter = new CampusAdapter();
        rv.setAdapter(adapter);
        return rv;
    }
}