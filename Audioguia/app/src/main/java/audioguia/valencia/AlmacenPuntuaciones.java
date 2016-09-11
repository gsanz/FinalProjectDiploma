package audioguia.valencia;
import java.util.Vector;

/**
 * Created by gorsanmo on 14/11/15.
 */
public interface AlmacenPuntuaciones {

    public void guardarPuntuacion(int puntos, String nombre, long fecha);
    public Vector<String> listaPuntuaciones(int cantidad);
}
