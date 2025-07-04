import java.util.Scanner;

public class App {
    /*
     * Variables que se usan en todo el programa, la lista de notas para guardar las
     * notas del usuario
     * nombre el nombre y datosv identifica si ya hay datos existentes
     */

    public static double notas[] = new double[3];
    public static String nombre = "";
    public static boolean datosv = false;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        do {
            /*
             * Se llama el metodo del menu y este nos da la variable int elegida para
             * posteriormente elegir que hacer
             */
            int opcion = menu(input);
            switch (opcion) {
                case 1:
                    // Llama el metodo de registrar a datos y datosv pasa a ser true para reconocer
                    // que el sistema ya posee datos
                    registrarDatos(input);
                    datosv = true;
                    break;
                case 2:
                    // Muestra los datos actuales
                    mostrarDatos(input);
                    break;
                case 3:
                    /*
                     * Calcula el promedio y llama la funcion de aprobar o reprobar, se reprueba con
                     * menos de 60
                     */
                    double promedio = promedioNotas(input);
                    boolean aprobo = aproboReprobo(promedio);
                    if (promedio >= 0) {
                        System.out.printf("Su promedio es %.2f%n", promedio);
                        if (aprobo) {
                            System.out.println("Ha aprobado");
                        } else {
                            System.out.println("Ha reprobado ");
                        }
                    }
                    System.out.print("Presione enter para volver a el menu ");
                    input.nextLine();
                    break;
                case 4:
                    // Se borran los datos
                    borrarDatos(input);
                    break;
                case 5:
                    // Sobreescribe los datos
                    sobreescribir(input);
                    break;
                default:
                    // Sale del programa gracias a el return
                    System.out.println("Saliendo de el programa...");
                    input.close();
                    return;
            }
        } while (true);
    }

    private static int menu(Scanner input) {
        do {
            // Primero se muestra el menu, atraves de este metodo
            System.out.println("\n--- SISTEMA DE REGISTRO DE ESTUDIANTES ---\n");
            System.out.println("""
                    1. Registrar datos de un estudiante
                    2. Mostrar datos del estudiante actual
                    3. Calcular promedio de notas del estudiante actual
                    4. Borrar datos actuales
                    5. Agregar nuevos datos
                    0. Salir
                                    """);
            // Se valida que la opcion haga parte de alguna de las opciones del menu, esto a
            // traves de regex
            // Para que permita digitar letras y no suelte un error y salga del programa

            System.out.print("Digite su opcion: ");
            String opcion = input.nextLine();
            if (opcion.matches("[0-5]")) {
                return Integer.parseInt(opcion);
            } else {
                System.out.println("Opcion no valida digite nuevamente");
            }
        } while (true);
    }

    private static void registrarDatos(Scanner input) {
        // un metodo para que el usuario pueda registar los datos
        System.out.println("\n--- REGISTRO DE DATOS ---\n");
        boolean t;
        do {
            do {
                // Llama a el metodo para que se valide el nombre y hasta que no retorne true
                // repite el do
                t = validarNombre(input);
            } while (!t);
            t = false;
            int i = 0;
            do {
                // Si el metodo de validar nota no retorna true la i nunca incrementa por ende
                // no avanza el ciclo y se reescribe una y otra vez
                // la misma parte de la lista
                t = validarNota(input, i);
                if (t) {
                    i++;
                }
            } while (i < 3);
            datosv = true;
            // Para hacer el resumen de datos se llama a el mismo metodo de la opcion 2, y
            // gracias a esto hace un resumen de los datos
            System.out.println("Resumen de los datos: ");
            mostrarDatos(input);
            // Esta funcion valida si la persona escoge si o no a traves de un booleano en
            // caso de decir no, deja volver a escribir los datos
            boolean validar = validarSiNo(input, "¿Esta seguro de la validez de los datos? (SI) (NO)");
            if (validar) {
                break;
            } else if (validar == false) {
                System.out.println("Digite los datos nuevamente ");
            }
        } while (true);
        System.out.print("Para volver a el menu presione enter ");
        input.nextLine();
    }

    private static boolean validarNota(Scanner input, int i) {
        // Valida la nota atraves de un regex, pues asi no soltara un error que cierre
        // el programa en caso de equivocarse y poner una letra
        System.out.print("Digite la nota " + (i + 1) + ": ");
        String validar = input.nextLine();
        if (validar.matches("[0-9]{1,2}(\\.[0-9]{1,2})?|100(\\.00)?")) {
            notas[i] = Double.parseDouble(validar);
            return true;
        } else {
            System.out.println("Nota no valida volver a digitar");
            return false;
        }
    }

    private static boolean validarNombre(Scanner input) {
        // Se valida el nombre del estudiante que sean solo letras y no acepte solo
        // espacios o un enter
        System.out.print("Digite el nombre del estudiante: ");
        nombre = input.nextLine();
        if (nombre.length() > 0 && !nombre.matches("[\s]*") && nombre.matches("[a-zA-Z\s]*")) {
            return true;
        } else {
            System.out.println("Nombre no valido ");
            return false;
        }
    }

    private static void mostrarDatos(Scanner input) {
        // Muestra los datos este metodo funciona tanto para hacer un resumen de datos
        // como para la opcion 2 de el menu
        System.out.println("\n--- MOSTRANDO DATOS ---\n");
        // Solo se ejecuta si ya hay datos
        if (datosv != false) {
            System.out.println("El estudiante " + nombre + " Tiene las siguiente notas");
            for (int i = 0; i < notas.length; i++) {
                System.out.println((i + 1) + " " + notas[i]);
            }
            System.out.print("Para continuar presione enter ");
            input.nextLine();
        } else {
            System.out.println("No hay datos existentes ");
            System.out.print("Para volver a el menu presione enter ");
            input.nextLine();
        }
    }

    private static double promedioNotas(Scanner input) {
        // Calcula el promedio solo si ya hay datos y devuelve el double para validar si
        // aprobo o reprobo en el otro metodo
        if (datosv != false) {
            System.out.println("Calculando el promedio actual...");
            double promedio = 0;
            for (int i = 0; i < notas.length; i++) {
                promedio += notas[i];
            }
            promedio /= 3;
            return promedio;
        } else {
            System.out.println("No hay datos existentes ");
            System.out.print("Para volver a el menu presione enter");
            input.nextLine();
            return -1;
        }
    }

    private static boolean borrarDatos(Scanner input) {
        // Borra todos los datos si ya existen
        if (datosv == false) {
            System.out.println("No existen datos actuales ");
            System.out.println("Volviendo a el menu...");
            return false;
        }
        // Valida si realmente quiere borrar los datos en caso de que no vuelve a el
        // menu
        boolean validar = validarSiNo(input, "¿Desea borrar todos los datos actuales? (SI) (NO)");
        if (validar) {
            System.out.println("Borrando datos del estudiante actual....");
            for (int i = 0; i < notas.length; i++) {
                notas[i] = 0;
            }
            nombre = "";
            System.out.println("Datos borrados con exito");
            System.out.print("Para continuar presione enter ");
            input.nextLine();
            datosv = false;
            return true;
        } else {
            System.out.println("Datos a salvo volviendo a el menu...");
            return false;
        }

    }

    private static boolean aproboReprobo(double promedio) {
        // Valida si el promedio alcanza para pasar
        if (promedio >= 60) {
            return true;
        } else {
            return false;
        }
    }

    private static void sobreescribir(Scanner input) {
        // Sobreescribe los datos ya puestos, solo si ya existen
        if (datosv == false) {
            System.out.println("No existen datos actuales, registre los datos con la opcion 1 ");
            return;
        }
        //Valida si la persona esta o no segura de borrar los datos a traves del metodo de borrar datos
        System.out.println("Al sobreescribir los datos se borraran los datos pasados ");
        boolean validacion = borrarDatos(input);
        if (validacion == false) {
            return;
        }
        registrarDatos(input);
    }

    private static boolean validarSiNo(Scanner input, String mensaje) {
        //Este metodo recibe un mensaje y valida si la persona dice si o no, es simplemente para ahorrar codigo y no escribir lo mismo una y otra vez
        System.out.println(mensaje);
        do {
            String validar = input.nextLine();
            if (validar.equalsIgnoreCase("si")) {
                return true;
            } else if (validar.equalsIgnoreCase("no")) {
                return false;
            } else {
                System.out.println("Opcion no valida ");
            }
        } while (true);
    }
}