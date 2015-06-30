package peladafc.com.murallocalizacao.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import peladafc.com.murallocalizacao.Modelos.Campus;
import peladafc.com.murallocalizacao.Modelos.Cidade;
import peladafc.com.murallocalizacao.R;
import peladafc.com.murallocalizacao.globals.Globals;

/**
 * Created by AIRES on 29/06/2015.
 */
public class CampusAdapter extends RecyclerView.Adapter <CampusAdapter.CampusViewHolder>{
    private List<Campus> campus;

    public CampusAdapter(){
        campus = new ArrayList<>();
        for (int i = 0; i < Globals.cidades.size(); i++) {
            Cidade cidade = Globals.cidades.get(i);
            for (int j = 0; j < cidade.getCampusList().size(); j++) {
                campus.add(cidade.getCampusList().get(j));
            }
        }
    }

    @Override
    public CampusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.campus_item, parent, false);
        CampusViewHolder pvh = new CampusViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CampusViewHolder holder, int position) {
        String nomeCampus;
        nomeCampus = campus.get(position).getNome().equalsIgnoreCase("") ? "Campus: "+campus.get(position).getOrdem() : campus.get(position).getNome();
        holder.txtNomeCampus.setText(nomeCampus);
        holder.txtNomeCidade.setText(campus.get(position).getNomeCidade());
    }

    @Override
    public int getItemCount() {
        return campus.size();
    }

    public static class CampusViewHolder extends RecyclerView.ViewHolder {
        TextView txtNomeCampus;
        TextView txtNomeCidade;
        public CampusViewHolder(View itemView) {
            super(itemView);
            txtNomeCampus = (TextView) itemView.findViewById(R.id.txtNomeCampus);
            txtNomeCidade = (TextView) itemView.findViewById(R.id.txtCidadeCampus);
        }
    }
}
