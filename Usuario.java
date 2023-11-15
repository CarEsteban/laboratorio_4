import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Usuario implements Premium {

    String nombre,password, numTarjeta;
    int numAsiento,cantMaletas;
    //Se agregó este arraylist para poder mostrar todas las reservas que tenga el usuario
    //no se tenía diseñado pero ya se agregó al documento
    ArrayList<Reserva> reservas;

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

    @Override
    public void setNumAsiento(int numAsiento) {
        this.numAsiento = numAsiento;
    }

    @Override
    public void setCantMaletas(int cantMaletas) {
        this.cantMaletas = cantMaletas;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre() {
        return nombre;
    }

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

    public int getNumAsiento() {
        return numAsiento;
    }

    public int getCantMaletas() {
        return cantMaletas;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public void setReserva(Reserva reserva){
        reservas.add(reserva);
    }

    public String cambiarPassword(String passwordVieja){
        return "hola";
    }

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
            }else if(verificacion.equals("tarjeta")){
                return hexString.toString().equals(this.numTarjeta);
            }else{
                return false;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false; 
        }
    }
    


}
