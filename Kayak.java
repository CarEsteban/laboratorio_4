import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;


public class Kayak{

    public static void main(String[] args) {
        int menu=0, opc=0;
        Scanner scanner = new Scanner(System.in);
        boolean contiuar=true, confirmado,loggedIn=false,doReserva=true;
        Usuario usuario =null;
        String tipoPlan;
        File nombreFile = new File("usuarios.csv");
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

        abrirCSV( "usuarios.csv",  nombreFile);

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
                    tipoPlan="VIP";
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

                        try (BufferedWriter wr = new BufferedWriter(new FileWriter(nombreFile,true))){
                    
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
                    
        
                    String[] datosUsuario = autenticarUsuario(nombreUsuario, contrasenia,nombreFile);

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
                                      
                                    break;
                                    
                                case 2: //confirmacion
                                
                                    
                                    break;
 
                                case 3: //modo perfil, cambiar contraseña
                                    
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
    
    
    public static void abrirCSV(String nombreString, File nombreFile){
        if(nombreString == "usuarios.csv"){
            String[] encabezado = {"Nombre", "Password"};
            crearCSV(nombreFile, encabezado);

        }
    }


    public static String[] autenticarUsuario(String usuario, String contrasenia, File nombreFile) {
        String contraseniaEncrip = generarHashMD5(contrasenia);
    
        try (BufferedReader br = new BufferedReader(new FileReader(nombreFile))) {
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
    public static void crearCSV(File nombreFile, String[] encabezado) {
        try {
            if (!nombreFile.exists()) {
                FileWriter writer = new FileWriter(nombreFile);
    
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