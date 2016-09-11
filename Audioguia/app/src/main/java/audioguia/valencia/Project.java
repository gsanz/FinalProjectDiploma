package audioguia.valencia;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import java.util.Vector;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.Location;
import android.util.Log;
import android.content.Intent;
import android.view.View;
public class Project extends FragmentActivity implements OnMapReadyCallback,LocationListener {

    private GoogleMap mMap;
    private LocationManager manejador;
    private Location mejorLocaliz,actualLocaliz;
    private LatLng actual_position = new LatLng(-34, 151);
    private Location l;
    private static final long DOS_MINUTOS = 2*60*120;
    private LocationsVisited locationsvisited;
    public static AlmacenPuntuaciones almacen = new AlmacenPuntuacionesArray();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audioguide);
        locationsvisited = new LocationsVisited(this);
        locationsvisited.guardar();
        almacen = new AlmacenPuntuacionesArray();
        long i = 12345678910L;
        manejador =(LocationManager) getSystemService(LOCATION_SERVICE);
    }


    /**
     * Manipulates the map once available.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override public void onResume()
    {
        super.onResume();
        if (manejador.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            manejador.requestLocationUpdates(LocationManager.GPS_PROVIDER,20*100,5,this);
        }
        if (manejador.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        {
            manejador.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10*100,10,this);
        }
    }

    @Override protected void onPause() {
        super.onPause();
        manejador.removeUpdates((LocationListener) this);
    }

     public void mostrar_lista(View view) {
        Intent visit_place = new Intent(this, Puntuaciones.class);
        startActivity(visit_place);
    }

    public void mostrar_instrucciones(View view) {
        Intent mostrar_instrucciones = new Intent(this, Instrucciones.class);
        startActivity(mostrar_instrucciones);
    }

    public void ver_posiciones(View view) {
        Intent ver_posiciones = new Intent(this, MapsActivity.class);
        Bundle actual_location = new Bundle();
        actual_location.putDouble("LatitudeVlc",actualLocaliz.getLatitude());
        actual_location.putDouble("LongitudeVlc",actualLocaliz.getLongitude());
        ver_posiciones.putExtras(actual_location);
        startActivity(ver_posiciones);
    }

    public void mostrar_tiempo(View view){
        Intent mostrar_tiempo_valencia = new Intent(this, WeatherActivity.class);
        startActivity(mostrar_tiempo_valencia);
    }


    public void onLocationChanged(Location localiz)
    {
        long kl=231L;
        actualLocaliz = localiz;
        almacen = new AlmacenPuntuacionesArray();
        Vector <String> localizaciones =locationsvisited.listaPuntuaciones();
        for (int i =0; i< localizaciones.size(); i++)
        {
            String[] aux = localizaciones.get(i).split("___");
            double latitud = Double.parseDouble(aux[2]);
            double longitud = Double.parseDouble(aux[3]);
            Location lz = new Location(aux[4]);
            lz.setLatitude(latitud);
            lz.setLongitude(longitud);
            float dfinal = localiz.distanceTo(lz);
            almacen.guardarPuntuacion(i,aux[1]+"   "+String.valueOf(dfinal)+"m",kl);
            if (dfinal < 500)
            {
                Intent visit_place = new Intent(this, show_image.class);
                visit_place.putExtra("lugar",aux[1]);
                visit_place.putExtra("image",aux[4]);
                visit_place.putExtra("audio",aux[5]);
                startActivity(visit_place);
            }
        }
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
