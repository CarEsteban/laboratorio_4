import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Clase que representa a un usuario del sistema.
 * Implementa la interfaz Premium.
 */
public class Usuario implements Premium {

    /** Nombre del usuario. */
    String nombre;
    
    /** Contraseña del usuario, en formato MD5. */
    String password;
    
    /** Número de tarjeta del usuario, en formato MD5. */
    String numTarjeta;
    
    /** Número de asiento asignado al usuario. */
    int numAsiento;
    
    /** Cantidad de maletas que lleva el usuario. */
    int cantMaletas;
    
    /** Lista de reservas asociadas al usuario. */
    ArrayList<Reserva> reservas = new ArrayList<Reserva>();

    /**
     * Constructor de la clase Usuario.
     *
     * @param nombre    Nombre del usuario.
     * @param password  Contraseña del usuario.
     */
    public Usuario(String nombre, String password) {
        this.nombre = nombre;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] hash = md.digest();

            // Convertir el hash de bytes a representación hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            this.password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNumAsiento(int numAsiento) {
        this.numAsiento = numAsiento;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCantMaletas(int cantMaletas) {
        this.cantMaletas = cantMaletas;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return Contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return Nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el número de tarjeta del usuario.
     *
     * @param numTarjeta Número de tarjeta a establecer.
     */
    public void setNumTarjeta(String numTarjeta) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] hash = md.digest();

            // Convertir el hash de bytes a representación hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            this.numTarjeta = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el número de asiento asignado al usuario.
     *
     * @return Número de asiento del usuario.
     */
    public int getNumAsiento() {
        return numAsiento;
    }

    /**
     * Obtiene la cantidad de maletas que lleva el usuario.
     *
     * @return Cantidad de maletas del usuario.
     */
    public int getCantMaletas() {
        return cantMaletas;
    }

    /**
     * Obtiene la lista de reservas asociadas al usuario.
     *
     * @return Lista de reservas del usuario.
     */
    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    /**
     * Establece una reserva para el usuario.
     *
     * @param reserva Reserva a establecer.
     */
    public void setReserva(Reserva reserva) {
        if (reserva != null) {
            this.reservas.add(reserva);
        } else {
            this.reservas = null;
        }
    }

    /**
     * Cambia la contraseña del usuario.
     *
     * @param passwordVieja Contraseña antigua del usuario.
     * @return Nueva contraseña del usuario.
     */
    public String cambiarPassword(String passwordVieja) {
        return "hola"; // Implementar lógica de cambio de contraseña
    }

    /**
     * Verifica la encriptación de un dato específico.
     *
     * @param datoAVerificar Dato a verificar.
     * @param verificacion   Tipo de verificación ("password" o "tarjeta").
     * @return True si la verificación es exitosa, false si no lo es.
     * @throws NoSuchAlgorithmException Si el algoritmo de encriptación MD5 no está disponible.
     */
    public boolean verificarEncriptacion(String datoAVerificar, String verificacion) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(datoAVerificar.getBytes());
            byte[] hash = md.digest();

            // Convertir el hash de bytes a representación hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // Comparar el hash resultante con el hash almacenado
            if (verificacion.equals("password")) {
                return hexString.toString().equals(this.password);
            } else if (verificacion.equals("tarjeta")) {
                return hexString.toString().equals(this.numTarjeta);
            } else {
                return false;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    


}
