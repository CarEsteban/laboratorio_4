import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Clase que representa una reserva de vuelo.
 *
 * <p>Esta clase se diseñó para facilitar el ingreso de datos al archivo CSV y permitir una mejor escalabilidad
 * en el laboratorio. Aunque originalmente se había pensado utilizar únicamente variables dentro de la clase
 * Usuario, se agregó esta nueva clase para mejorar la gestión de reservas. Ahora, el usuario tiene un ArrayList
 * de reservas para poder gestionar varias reservas de vuelo.</p>
 *
 * <p>La adición de esta clase no afecta significativamente al código, ya que su principal función es facilitar
 * la incorporación de datos al archivo CSV y mejorar la escalabilidad del sistema.</p>
 *
 * <p>Se ha actualizado la documentación del diseño y el diagrama UML para reflejar la incorporación de esta nueva clase.</p>
 */
public class Reserva {

    /** Fecha del viaje. */
    LocalDate fechaViaje;

    /** Tipo de vuelo (Ida y vuelta, Solo ida, etc.). */
    String tipoVuelo;

    /** Aerolínea de la reserva. */
    String aerolinea;

    /** Cantidad de boletos reservados. */
    int cantBoletos;

    /** Usuario asociado a la reserva. */
    Usuario usuario;

    /**
     * Constructor de la clase Reserva.
     *
     * @param fechaViaje   Fecha del viaje.
     * @param tipoVuelo    Tipo de vuelo (Ida y vuelta, Solo ida, etc.).
     * @param aerolinea    Aerolínea de la reserva.
     * @param cantBoletos  Cantidad de boletos reservados.
     * @param usuario      Usuario asociado a la reserva.
     */
    public Reserva(LocalDate fechaViaje, String tipoVuelo, String aerolinea, int cantBoletos, Usuario usuario) {
        this.fechaViaje = fechaViaje;
        this.tipoVuelo = tipoVuelo;
        this.aerolinea = aerolinea;
        this.cantBoletos = cantBoletos;
        this.usuario = usuario;
    }

    /**
     * Obtiene el tipo de vuelo de la reserva.
     *
     * @return Tipo de vuelo.
     */
    public String getTipoVuelo() {
        return tipoVuelo;
    }

    /**
     * Obtiene la aerolínea de la reserva.
     *
     * @return Aerolínea.
     */
    public String getAerolinea() {
        return aerolinea;
    }

    /**
     * Obtiene la cantidad de boletos reservados.
     *
     * @return Cantidad de boletos.
     */
    public int getCantBoletos() {
        return cantBoletos;
    }

    /**
     * Obtiene la fecha del viaje.
     *
     * @return Fecha del viaje.
     */
    public LocalDate getFechaViaje() {
        return fechaViaje;
    }

    /**
     * Convierte la reserva a una representación de cadena (String).
     *
     * @return Representación de cadena de la reserva en formato CSV.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        String fechaViajeString = fechaViaje.format(formatter);

        return usuario.getNombre() + "," + fechaViajeString + "," + tipoVuelo + "," + String.valueOf(cantBoletos) + "," + aerolinea;
    }
}
