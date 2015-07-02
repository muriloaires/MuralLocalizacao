package peladafc.com.murallocalizacao.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import peladafc.com.murallocalizacao.MapsActivity;
import peladafc.com.murallocalizacao.Modelos.Campus;
import peladafc.com.murallocalizacao.Modelos.Cidade;
import peladafc.com.murallocalizacao.Modelos.Instituto;
import peladafc.com.murallocalizacao.R;
import peladafc.com.murallocalizacao.globals.Globals;

/**
 * Created by AIRES on 29/06/2015.
 */
public class InstitutosAdapter extends RecyclerView.Adapter<InstitutosAdapter.InstitutoViewHolder> {
    private List<Instituto> institutos;

    public InstitutosAdapter() {
        institutos = new ArrayList<>();
        for (int i = 0; i < Globals.campusSelecionado.getInstitutos().size(); i++) {
            institutos.add(Globals.campusSelecionado.getInstitutos().get(i));
        }
    }


    @Override
    public InstitutoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.instituto_item, parent, false);
        InstitutoViewHolder pvh = new InstitutoViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final InstitutoViewHolder holder, final int position) {

        holder.txtNomeInstituto.setText(institutos.get(position).getNome());
        holder.txtSiglaInstituto.setText(institutos.get(position).getSigla());
        holder.location.setOnLongClickListener((new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String uri = "geo:0,0?q=" + Globals.campusSelecionado.getInstitutos().get(position).getLatitude() + "," + Globals.campusSelecionado.getInstitutos().get(position).getLongitude();
                v.getContext().startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));
                return true;
            }

        }));

    }

    @Override
    public int getItemCount() {
        return institutos.size();
    }

    public static class InstitutoViewHolder extends RecyclerView.ViewHolder {
        TextView txtNomeInstituto;
        TextView txtSiglaInstituto;
        ImageView location;

        public InstitutoViewHolder(View itemView) {
            super(itemView);
            txtNomeInstituto = (TextView) itemView.findViewById(R.id.txtNomeInstituto);
            txtSiglaInstituto = (TextView) itemView.findViewById(R.id.txtSiglaInstituto);
            location = (ImageView) itemView.findViewById(R.id.imagemLocation);
        }


    }
}
