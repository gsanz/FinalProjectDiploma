package audioguia.valencia;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.Location;
import android.util.Log;

import java.util.Vector;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,LocationListener {
    private GoogleMap mMap;
    private LocationsVisited locationsvisited;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        locationsvisited = new LocationsVisited(this);
        locationsvisited.guardar();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Bundle actual_location = getIntent().getExtras();
        Double LatitudeVlc = actual_location.getDouble("LatitudeVlc");
        Double LongitudeVlc = actual_location.getDouble("LongitudeVlc");
        Vector<String> localizaciones =locationsvisited.listaPuntuaciones();
        for (int i =0; i< localizaciones.size(); i++)
        {
            String[] aux = localizaciones.get(i).split("___");
            double latitud = Double.parseDouble(aux[2]);
            double longitud = Double.parseDouble(aux[3]);
            mMap.addMarker(new MarkerOptions().position(new LatLng(latitud,longitud)).title(aux[1].toString()));
        }
        LatLng valencia = new LatLng(LatitudeVlc, LongitudeVlc);
        mMap.addMarker(new MarkerOptions().position(valencia).title("Estás aquí"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(LatitudeVlc, LongitudeVlc), 14.0f));
    }

    public void onLocationChanged(Location localiz){

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}
