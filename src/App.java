import java.util.Scanner;

public class App {
    public static double notas[] = new double[3];
    public static String nombre = "";
    public static boolean datosv = false;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        do {
            int opcion = menu(input);
            switch (opcion) {
                case 1:
                    registrarDatos(input);
                    datosv = true;
                    break;
                case 2:
                    mostrarDatos(input);
                    break;
                case 3:
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
                    borrarDatos(input);
                    break;
                case 5:
                    sobreescribir(input);
                    break;
                default:
                    System.out.println("Saliendo de el programa...");
                    return;
            }
        } while (true);
    }

    private static int menu(Scanner input) {
        do {
            System.out.println("\n--- SISTEMA DE REGISTRO DE ESTUDIANTES ---\n");
            System.out.println("""
                    1. Registrar datos de un estudiante
                    2. Mostrar datos del estudiante actual
                    3. Calcular promedio de notas del estudiante actual
                    4. Borrar datos actuales
                    5. Agregar nuevos datos
                    0. Salir
                                    """);
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
        System.out.println("\n--- REGISTRO DE DATOS ---\n");
        boolean t;
        do {
            do {
                t = validarNombre(input);
            } while (!t);
            t = false;
            int i = 0;
            do {
                t = validarNota(input, i);
                if (t) {
                    i++;
                }
            } while (i < 3);
            datosv = true;
            System.out.println("Resumen de los datos: ");
            mostrarDatos(input);
            boolean validar = validarSiNo(input, "¿Esta seguro de la validez de los datos? (SI) (NO)");
            if (validar) {
                System.out.print("Para volver a el menu presione enter ");
                input.nextLine();
                break;
            } else if (validar==false) {
                System.out.println("Digite los datos nuevamente ");
            }
        } while (true);
        System.out.print("Para volver a el menu presione enter ");
        input.nextLine();
    }

    private static boolean validarNota(Scanner input, int i) {
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
        System.out.println("\n--- MOSTRANDO DATOS ---\n");
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

    private static void borrarDatos(Scanner input) {
        if (datosv == false) {
            System.out.println("No existen datos actuales ");
            System.out.println("Volviendo a el menu...");
            return;
        }
        boolean validar = validarSiNo(input, "¿Desea borrar todos los datos actuales? (SI) (NO)");
        if (validar) {
            System.out.println("Borrando datos del estudiante actual....");
            for (int i = 0; i < notas.length; i++) {
                notas[i] = 0;
            }
            nombre = "";
            System.out.println("Datos borrados con exito");
            System.out.print("Para volver a el menu presione enter ");
            input.nextLine();
            datosv = false;
        } else if (validar == false) {
            System.out.println("Datos a salvo volviendo a el menu...");
        } else {
            System.out.println("Opcion no valida ");
        }
    }

    private static boolean aproboReprobo(double promedio) {
        if (promedio >= 60) {
            return true;
        } else {
            return false;
        }
    }

    private static void sobreescribir(Scanner input) {
        if (datosv==false) {
            System.out.println("No existen datos actuales, registre los datos con la opcion 1 ");
            return;
        }
        boolean validar =validarSiNo(input, "¿Desea agregar nuevos datos? se sobreescribiran los datos actuales (SI) (NO)");
        if (validar) {
            borrarDatos(input);
            registrarDatos(input);
        }
    }

    private static boolean validarSiNo(Scanner input, String mensaje) {
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