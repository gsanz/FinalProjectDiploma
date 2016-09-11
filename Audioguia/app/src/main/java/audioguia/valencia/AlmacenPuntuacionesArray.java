package audioguia.valencia;
import java.util.Vector;

/**
 * Created by gorsanmo on 14/11/15.
 */
public class AlmacenPuntuacionesArray implements AlmacenPuntuaciones {

private Vector<String> puntuaciones;
    public AlmacenPuntuacionesArray()
    {
        puntuaciones = new Vector<String>();
    }
    @Override public void guardarPuntuacion(int puntos,String nombre, long fecha)
    {
        puntuaciones.add(puntos+" "+nombre);
    }

    @Override public Vector<String> listaPuntuaciones(int cantidad)
    {
        return puntuaciones;
    }

}
