import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;
/**
 * Clase principal que gestiona las operaciones del sistema Kayak.
 *
 * <p>Esta clase contiene el método principal (main) que ejecuta el programa y controla las opciones
 * del usuario. Se encarga de la gestión de usuarios, reservas y operaciones relacionadas con el sistema de
 * reservas de vuelo.</p>
 */

public class Kayak{
    /**
     * Método principal que inicia la ejecución del programa.
     *
     * @param args Argumentos de línea de comandos (no se utilizan en este programa).
     * @throws IOException Excepción de entrada/salida.
     */
    public static void main(String[] args) throws IOException {
        int menu=0, opc=0;
        Scanner scanner = new Scanner(System.in);
        boolean contiuar=true, confirmado,loggedIn=false,doReserva;
        Usuario usuario =null;
        String tipoPlan="VIP";
        File usuariosFile = new File("usuarios.csv") , reservasFile = new File("reservas.csv");
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

        abrirCSV( "usuarios.csv",  usuariosFile);
        abrirCSV( "reservas.csv",  reservasFile);

        while (contiuar) {
            iniciarOCrear();
            
            try {
                opc = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Ingrese un número.");
            }

            switch (opc) {
                case 1:
                    String nombre, password,passwordConfirmar;
                    int plan;
                    System.out.println("BIENVENIDO A LA CREACION DE SU USUARIO");
                    System.out.println("==========================================");

                    System.out.println("Ingrese que plan prefiere, gratis(1) o VIP(2)");
                    plan = scanner.nextInt();scanner.nextLine();

                    if(plan==1){
                        System.out.println("Lo sentimos, no contamos con este plan");
                        contiuar=false;
                        break;
                    }
                    System.out.println("Ingrese su nombre de usuario");
                    nombre = scanner.nextLine();
                    System.out.println("Ingrese su contraseña");
                    password = scanner.nextLine();

                    usuario = new Usuario(nombre, password);

                    System.out.println("Confirme su contraseña");
                    passwordConfirmar = scanner.nextLine();

                    confirmado = usuario.verificarEncriptacion(passwordConfirmar, "password");

                    if (confirmado) {
                        System.out.println("Usuario creado correctamente");

                        String[] datosUsuario = {usuario.getNombre(),usuario.getPassword()};

                        try (BufferedWriter wr = new BufferedWriter(new FileWriter(usuariosFile,true))){
                    
                            StringBuilder linea = new StringBuilder();
                    
                            for (int i = 0; i < datosUsuario.length; i++) {
                    
                                linea.append(datosUsuario[i]);
                    
                                if (i < datosUsuario.length - 1) {
                                        linea.append(",");
                                }
                            }
                            linea.append("\n");
                    
                            wr.append(linea.toString());
                    
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        usuarios.add(new Usuario(nombre, password));
                        
                        break;


                    }else{
                        try {
                            Thread.sleep(700);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Como que algo no cuadra, usuario no creado");
                        break;

                    }

                case 2:
                    String nombreUsuario, contrasenia;
                    System.out.println("BIENVENIDO AL INICIO DE SESIÓN");
                    System.out.println("Ingrese su nombre de usuario:");
                    nombreUsuario = scanner.nextLine();
                    System.out.println("Ingrese su contraseña:");
                    contrasenia = scanner.nextLine();
                    
        
                    String[] datosUsuario = autenticarUsuario(nombreUsuario, contrasenia,usuariosFile);

                    if (datosUsuario!=null) {
                        loggedIn = true;
                        
                        String nombreU = datosUsuario[0];
                        String contraseniaUsuario = datosUsuario[1];
                        
                        // Crear una instancia de Usuario
                        usuario = new Usuario(nombreU, contraseniaUsuario);
                        
                    }else {
                        System.out.println("Usuario o contraseña incorrectos. Intente nuevamente.");
                    }

                    if (loggedIn) { // Si se inició sesión
                        while (loggedIn) {
                            System.out.println("");
                            System.out.println("Bienvenido " + usuario.getNombre());

                            printMenu();

                            try {
                                menu = scanner.nextInt();
                                scanner.nextLine();
                            } catch (InputMismatchException e) {
                                System.out.println("Ingrese un número.");
                                scanner.nextLine();
                            }
                            switch (menu) {
                                case 1: //reserva
                                    doReserva=true;
                                    while (doReserva) {
                                        String fechaString, tipoVuelo,aerolinea;
                                        LocalDate fechaViaje;
                                        int numTipo, cantBoletos;
                                        Reserva reserva;
                                        System.out.println("Ingrese la fecha del viaje en el formato YYYY-MM-DD");
                                        fechaString = scanner.nextLine();
                                        fechaViaje = LocalDate.parse(fechaString);
                                        
                                        System.out.println("Ingrese si es ida y vuelta (1) o solo ida(2)");
                                        numTipo = scanner.nextInt();scanner.nextLine();
                                        if(numTipo==1){
                                            tipoVuelo="Ida y vuelta";
                                        }else if(numTipo==2){
                                            tipoVuelo="Solo ida";
                                        }else{
                                            tipoVuelo = null;
                                        }
                                        
                                        System.out.println("Ingrese la cantidad de boletos");
                                        cantBoletos = scanner.nextInt();scanner.nextLine();

                                        System.out.println("Ingrese la areolinea");
                                        aerolinea = scanner.nextLine();
                                        
                                        reserva = new Reserva(fechaViaje, tipoVuelo, aerolinea, cantBoletos, usuario);
                                        usuario.setReserva(reserva);

                                        doReserva=volverAlMenu(scanner, " a ingresar otra reserva? ");
                                        
                                    }

                                    try (BufferedReader reader = new BufferedReader(new FileReader(reservasFile))) {
                                        // Crear una lista para almacenar las líneas existentes en el archivo
                                        ArrayList<String> lineasExistente = new ArrayList<>();
                                        String lineaExistente;
                                        while ((lineaExistente = reader.readLine()) != null) {
                                            lineasExistente.add(lineaExistente);
                                        }
                            
                                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reservasFile, true))) {
                                            // Recorrer la lista de reservas y escribir cada reserva en el archivo CSV si no existe
                                            
                                            
                                            for (Reserva reserva : usuario.getReservas()) {
                                                // Verificar si la línea ya existe en el archivo
                                                if (!lineasExistente.contains(reserva.toString())) {
                                                    // Escribir la línea CSV en el archivo
                                                    writer.write(reserva.toString());
                                                    writer.newLine();  
                                                }
                                            }
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    usuario.setReserva(null);
                                      
                                    break;
                                    
                                case 2: //confirmacion
                                    System.out.println(usuario.getNombre());
                                    try (BufferedReader br = new BufferedReader(new FileReader(reservasFile))) {
                                        String linea;
                                        boolean esEncabezado = true;  
                                        boolean definioReserva = false; 
                                        while ((linea = br.readLine()) != null) {
                                            if (esEncabezado) {
                                                esEncabezado = false;
                                                continue;
                                            }
                            
                                            // Dividir la línea en campos usando la coma como delimitador
                                            String[] campos = linea.split(",");
                            
                                            // Obtener valores de cada campo
                                            String nombreUsuarioCSV = campos[0];
                                            if (nombreUsuarioCSV.equals(usuario.getNombre())) {
                                                
                                                // Obtener valores de cada campo
                                                String fechaViaje = campos[1];
                                                LocalDate fechDate;
                                                fechDate = LocalDate.parse(fechaViaje);
                                                String tipo = campos[2];
                                                int cantBoletos = Integer.parseInt(campos[3]);
                                                String aerolinea = campos[4];
                                

                                                usuario.setReserva(new Reserva(fechDate, tipo, aerolinea, cantBoletos, usuario));
                                                // Indicar que se definió al menos una reserva
                                                definioReserva = true;
                                            }

                                        }
                                        // Mostrar mensaje solo si se definió alguna reserva
                                        if (definioReserva) {
                                            System.out.println("Bienvenido a la confirmación de su reserva");
                                            // Imprimir información de usuarios y reservas
                                            int i=0;
                                            for (Reserva reserva : usuario.getReservas()) {
                                                i++;
                                                System.out.println("\t" +i+") "+ reserva);
                                            }
                                        } else {
                                            System.out.println("Lo sentimos, necesitamos que defina primero alguna reserva");
                                            break;
                                        }
                                    
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    System.out.println("Ingrese cual de sus reservas desea confirmar");
                                    int eleccion = scanner.nextInt();scanner.nextLine();


                                    System.out.print("Ingrese el número de tarjeta: ");
                                    String numeroTarjeta = scanner.nextLine();
                                    usuario.setNumTarjeta(numeroTarjeta);

                                    System.out.print("Ingrese el número de asiento: ");
                                    int numeroAsiento = scanner.nextInt();scanner.nextLine();

                                    usuario.setNumAsiento(numeroAsiento);

                                    System.out.print("Ingrese la cantidad de maletas: ");
                                    int cantidadMaletas = scanner.nextInt();scanner.nextLine();

                                    usuario.setCantMaletas(cantidadMaletas);

                                    System.out.println("Este sería su itineario:");
                                    System.out.println(usuario.getReservas().get(eleccion-1));
                                    System.out.println("Numero de asiento: " + usuario.getNumAsiento() );
                                    System.out.println("Numero de maletas: " + usuario.getCantMaletas() );

                                    


                                    
                                    break;
 
                                case 3: //modo perfil, cambiar contraseña
                                    String contraseniaCambiar;
                                    System.out.println("Usted tine el plan " + tipoPlan);
                                    System.out.println("Bienvenido al cambio de contraseña");
                                    System.out.println("Ingrese la nueva contraseña");
                                    contraseniaCambiar = generarHashMD5(scanner.nextLine());

                                    if(contraseniaCambiar.equals(usuario.getPassword())){
                                        System.out.println("Esta era su contraseña anteriormente, si desea cambiarla ingrese otra");
                                    }
 
                                    try (BufferedReader br = new BufferedReader(new FileReader(usuariosFile))) {
                                        List<String> lines = new ArrayList<>();
                                    
                                        // Leer todas las líneas y actualizar la contraseña si es necesario
                                        String line;
                                        while ((line = br.readLine()) != null) {
                                            String[] datos = line.split(",");
                                            if (datos.length == 2) {
                                                String nombreEnArchivo = datos[0];
                                                if (nombreEnArchivo.equals(usuario.getNombre())) {
                                                    datos[1] = contraseniaCambiar;
                                                }
                                                line = String.join(",", datos);
                                            }
                                            lines.add(line);
                                        }
                                    
                                        // Escribir el contenido actualizado de vuelta al archivo
                                        try (BufferedWriter wr = new BufferedWriter(new FileWriter(usuariosFile))) {
                                            for (String updatedLine : lines) {
                                                wr.write(updatedLine);
                                                wr.newLine();
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    System.out.println("Cambio de contraseña realizado");

                                    
                                    break;
                                case 4:
                                    loggedIn = false;
                                    break;
                                case 0:
                                    loggedIn = false;
                                    continue;
                                default:
                                    System.out.println("");
                                    System.out.println("Número inválido. Intente nuevamente.");
                                    break;
                            }
                            if(!loggedIn){
                                break;
                            }
                            loggedIn = volverAlMenu(scanner, " al menu? ");
                        }   
                    }      

                    break;

                
            }


            contiuar = volverAlMenu(scanner," al menú principal? ");




        }

        scanner.close();

    }
      
    private static boolean volverAlMenu(Scanner scanner, String eleccion) {
        System.out.println("¿Desea volver" + eleccion + " (1: Sí, 0: No): ");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        if (opcion == 0) {
            if (eleccion.equals(" al menú principal? ")) {
                System.out.println("Saliendo del programa.");

                return false;
            }else if(eleccion.equals(" al menú? ")){
                System.out.println("Regresando al menu...");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return false;
            } else {
                System.out.println("Saliendo de la opción.");
                return false;
            }
        } else {
            return true;
        }
    }
    public static void printMenu() {
        System.out.println("*************************************");
        System.out.println("            Menú Principal");
        System.out.println("*************************************");
        System.out.println("Ingrese la opción que desee:");
        System.out.println("1: Hacer una reserva");
        System.out.println("2: Confirmar el vuelo");
        System.out.println("3: Entrar al perfil");
        System.out.println("4: Salir");
    }

    public static void iniciarOCrear() {
        System.out.println("Ingrese la opción que desee:");
        System.out.println("1: Crear usuario");
        System.out.println("2: Iniciar sesión");
        System.out.println("3: Salir");
        System.out.print("Opcion: ");
    }    
    
    
    public static void abrirCSV(String nombreString, File file){
        if(nombreString == "usuarios.csv"){
            String[] encabezado = {"Nombre", "Password"};
            crearCSV(file, encabezado);

        }else if(nombreString.equals("reservas.csv")){
            String[] encabezado = {"usuario","fecha-viaje","tipo","cantidad-boletos","aerolinea","numero-tarjeta"};
            crearCSV(file, encabezado);
        }
    }


    public static String[] autenticarUsuario(String usuario, String contrasenia, File usuariosFile) {
        String contraseniaEncrip = generarHashMD5(contrasenia);
    
        try (BufferedReader br = new BufferedReader(new FileReader(usuariosFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datosUsuario = line.split(",");

                if (datosUsuario.length == 2) {
                    String nombreEnArchivo = datosUsuario[0];
                    String contraseniaEnArchivo = datosUsuario[1];
                    if (nombreEnArchivo.equals(usuario) && contraseniaEnArchivo.equals(contraseniaEncrip)) {
                        return datosUsuario;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public static String generarHashMD5(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(texto.getBytes());
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
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void crearCSV(File file, String[] encabezado) {
        try {
            if (!file.exists()) {
                FileWriter writer = new FileWriter(file);
    
                for (int i = 0; i < encabezado.length; i++) {
                    writer.append(encabezado[i]);
                    if (i < encabezado.length - 1) {
                        writer.append(",");
                    }
                }
    
                writer.append("\n");
                System.out.println("Archivo CREADO");

                writer.close();
            } else {
                System.out.println("Archivo abierto correctamente");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}