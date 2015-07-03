package peladafc.com.murallocalizacao;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.melnykov.fab.FloatingActionButton;

import java.util.List;

import peladafc.com.murallocalizacao.adapters.InstitutosAdapter;
import peladafc.com.murallocalizacao.globals.Globals;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private double[] coordenadas;

    RecyclerView rv;
    LinearLayoutManager llm;
    InstitutosAdapter adapter;
    FloatingActionButton fab;

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
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        rv = (RecyclerView) findViewById(R.id.recycler_maps);
        llm = new LinearLayoutManager(this);
        adapter = new InstitutosAdapter();
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);


    }

    private void setUpMap(GoogleMap mMap) {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng mapCenter = new LatLng(coordenadas[0], coordenadas[1]);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 16));
        mMap.setBuildingsEnabled(false);
        mMap.setMyLocationEnabled(true);

        for (int i = 0; i < Globals.campusSelecionado.getInstitutos().size(); i++) {
            IconGenerator iconFactory = new IconGenerator(this);
            Bitmap iconBitmap = iconFactory.makeIcon(Globals.campusSelecionado.getInstitutos().get(i).getSigla());
            LatLng institutoLocation = new LatLng(Globals.campusSelecionado.getInstitutos().get(i).getLatitude(), Globals.campusSelecionado.getInstitutos().get(i).getLongitude());
            mMap.addMarker(new MarkerOptions().position(institutoLocation).flat(true).icon(BitmapDescriptorFactory.fromBitmap(iconBitmap)).title(Globals.campusSelecionado.getInstitutos().get(i).getNome()));
        }

    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        setUpMap(googleMap);
        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Globals.campusSelecionado.getInstitutos().get(i).getLatitude(), Globals.campusSelecionado.getInstitutos().get(i).getLongitude()), 18));
                    }
                })
        );

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tipoMapa = googleMap.getMapType();

                if(tipoMapa == GoogleMap.MAP_TYPE_HYBRID){
                    tipoMapa = 0;
                }else{
                    tipoMapa++;
                }
                googleMap.setMapType(tipoMapa);
            }
        });
    }


    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            public void onItemClick(View view, int position);
        }

        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildPosition(childView));
                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        }
    }
}
