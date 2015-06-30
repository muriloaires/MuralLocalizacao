package peladafc.com.murallocalizacao.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import java.util.List;

import peladafc.com.murallocalizacao.Modelos.Cidade;
import peladafc.com.murallocalizacao.R;
import peladafc.com.murallocalizacao.ReadyToGo;
import peladafc.com.murallocalizacao.Services.CampusTask;
import peladafc.com.murallocalizacao.adapters.CampusAdapter;
import peladafc.com.murallocalizacao.globals.Globals;

/**
 * Created by AIRES on 26/06/2015.
 */
public class CampusFragment extends Fragment {
    private CampusAdapter adapter;
    private CampusTask tsk;
    private List<Cidade> cidades;

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View rootView;


        rootView = inflater.inflate(R.layout.fragment_campus,
                container, false);
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(rootView.getContext());
        rv.setLayoutManager(llm);
        CampusAdapter adapter = new CampusAdapter();
        rv.setAdapter(adapter);

        return rootView;
    }
}