
//Esta clase no estaba diseñada, ya que se habia pensado usar todo en las variables del usuario
//pero por practicidad para agregar al archivo de csv se agregó, en lo que afecta es que algunas variables
//del usuario estarán definidas acá, y el usuario ahora tendrá un arraylist de reserva para poder tener 
//varias reserva

//esto no afecta en gran parte al codigo ya que solo es para facilitar el ingreso de datos al csv y tener 
//una mejor escabalididad en el laboratorio (lo habia pensado de otra forma pero me iba a complicar mucho)

//se agregó la nueva clase al documento del diseño y al diagrama uml

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reserva {
    LocalDate fechaViaje;
    String  tipoVuelo, aerolinea, fechaViajeString;
    int cantBoletos;
    Usuario usuario;

    
    public Reserva(LocalDate fechaViaje, String tipoVuelo, String aerolinea, int cantBoletos, Usuario usuario) {
        this.fechaViaje = fechaViaje;
        this.tipoVuelo = tipoVuelo;
        this.aerolinea = aerolinea;
        this.cantBoletos = cantBoletos;
        this.usuario = usuario;
    }

    public String getTipoVuelo() {
        return tipoVuelo;
    }

    public String getAerolinea() {
        return aerolinea;
    }

    public int getCantBoletos() {
        return cantBoletos;
    }

    public LocalDate getFechaViaje() {
        return fechaViaje;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        fechaViajeString = fechaViaje.format(formatter);
        
        return usuario.getNombre()+","+fechaViajeString+","+tipoVuelo+","+String.valueOf(cantBoletos)+","+aerolinea;
    }

}
