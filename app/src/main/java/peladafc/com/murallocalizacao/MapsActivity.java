package peladafc.com.murallocalizacao;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import peladafc.com.murallocalizacao.adapters.CampusAdapter;
import peladafc.com.murallocalizacao.adapters.InstitutosAdapter;
import peladafc.com.murallocalizacao.globals.Globals;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap;
    private double[] coordenadas;

    RecyclerView rv;
    LinearLayoutManager llm;
    InstitutosAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            coordenadas = new double[]{0.0, 0.0};
        } else {
            coordenadas = extras.getDoubleArray("LAT_LONG");
        }
        rv = (RecyclerView) findViewById(R.id.recycler_maps);
        llm = new LinearLayoutManager(this);
        adapter = new InstitutosAdapter();
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);

        setUpMapIfNeeded();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {

        if (mMap == null) {

            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
        LatLng mapCenter = new LatLng(coordenadas[0], coordenadas[1]);
        mMap.clear();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 16));
        mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.person))
                .position(mapCenter)
                .flat(true)
                );

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(mapCenter)
                .zoom(16)
                .bearing(90)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                2000, null);

        for (int i = 0; i < Globals.campusSelecionado.getInstitutos().size(); i ++){
            LatLng institutoLocation = new LatLng(Globals.campusSelecionado.getInstitutos().get(i).getLatitude(), Globals.campusSelecionado.getInstitutos().get(i).getLongitude() );
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.locationmap)).position(institutoLocation).flat(true).title(Globals.campusSelecionado.getInstitutos().get(i).getNome()).rotation(275-180));
        }
    }


}
