package peladafc.com.murallocalizacao.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import peladafc.com.murallocalizacao.MapsActivity;
import peladafc.com.murallocalizacao.Modelos.Campus;
import peladafc.com.murallocalizacao.Modelos.Cidade;
import peladafc.com.murallocalizacao.R;
import peladafc.com.murallocalizacao.globals.Globals;

/**
 * Created by AIRES on 29/06/2015.
 * Classe responsável por insrir os objetos na recyclerview
 */
public class CampusAdapter extends RecyclerView.Adapter <CampusAdapter.CampusViewHolder>{
    private List<Campus> campus;

    public void setCampus(List<Cidade> cidade) {
        campus = new ArrayList<>();
        for (int i = 0; i < cidade.size(); i++){
            for (int j = 0; j < cidade.get(i).getCampusList().size(); j++) {
                campus.add(cidade.get(i).getCampusList().get(j));
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
    public void onBindViewHolder(final CampusViewHolder holder,final int position) {
        String nomeCampus;
        nomeCampus = campus.get(position).getNome().equalsIgnoreCase("") ? "Campus "+campus.get(position).getOrdem() : "Campus "+campus.get(position).getNome();
        holder.txtNomeCampus.setText(nomeCampus);
        holder.txtNomeCidade.setText(campus.get(position).getNomeCidade());
        holder.bairroCampus.setText("Setor " + campus.get(position).getSetor());

        //a partir deste click no layout, a aplicação passará para a próxima activity, com o mapa do campus selecionado
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(v.getContext(), MapsActivity.class);
                Globals.campusSelecionado = campus.get(position);
                v.getContext().startActivity(nextScreen);
            }
        });

    }

    @Override
    public int getItemCount() {
        return campus.size();
    }

    public static class CampusViewHolder extends RecyclerView.ViewHolder {
        TextView txtNomeCampus;
        TextView txtNomeCidade;
        TextView bairroCampus;
        LinearLayout ll;
        public CampusViewHolder(View itemView) {
            super(itemView);
            bairroCampus = (TextView) itemView.findViewById(R.id.setor);
            ll = (LinearLayout) itemView.findViewById(R.id.layoutItemCampus);
            txtNomeCampus = (TextView) itemView.findViewById(R.id.txtNomeCampus);
            txtNomeCidade = (TextView) itemView.findViewById(R.id.txtCidadeCampus);
        }



    }
}
