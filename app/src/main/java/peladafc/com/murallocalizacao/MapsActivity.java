package peladafc.com.murallocalizacao;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import peladafc.com.murallocalizacao.globals.Globals;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap;
    private double latitudeCampus;
    private double longitudeCampus;
    private double[] coordenadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            coordenadas = new double[]{0.0, 0.0};
        } else {
            coordenadas = extras.getDoubleArray("LAT_LONG");
        }
        setContentView(R.layout.activity_maps);
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

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 16));
        mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_play_dark))
                .position(mapCenter)
                .flat(true)
                .rotation(245));

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(mapCenter)
                .zoom(16)
                .bearing(90)
                .build();

        // Animate the change in camera view over 2 seconds
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                2000, null);
    }


}
