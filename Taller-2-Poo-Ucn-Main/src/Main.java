// Sebastian Barrera
// Fernando Lagos

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        /*
        copie los "txt" a la carpeta bin para no tener problemas al abrirlos
        cree la clase "ManejadorUsuarios" para verificar los usuarios y contraseñas
        cree los menu para "ADMIN" y "USER"
         */

        Scanner scanner = new Scanner(System.in);
        ManejadorUsuarios manejador = new ManejadorUsuarios();
        manejador.cargarUsuarios("usuarios.txt");

        while (true)
        {
            System.out.println("\n========= Sistema de Autenticación =========");
            System.out.println("1. Ingresar al sistema");
            System.out.println("2. Salir del sistema");
            System.out.print(": ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion)
            {
                case 1:
                    System.out.print("Ingrese nombre de usuario: ");
                    String usuario = scanner.nextLine();

                    System.out.print("Ingrese contraseña: ");
                    String password = scanner.nextLine();

                    boolean autenticado = manejador.verificarContrasena(usuario, password);

                    if (autenticado)
                    {
                        String rol = manejador.obtenerRol(usuario);
                        System.out.println("Usuario: " + usuario);
                        System.out.println("Rol: " + rol);

                        if ("ADMIN".equals(rol))
                        {
                            menuAdmin(scanner, usuario);
                        }
                        else if ("USER".equals(rol))
                        {
                            menuUser(scanner, usuario);
                        }
                    }
                    else
                    {
                        System.out.println("Error: usuario o contraseña incorrectos");
                    }
                    break;

                case 2:
                    System.out.println("Saliendo del sistema...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    private static void menuAdmin(Scanner scanner, String username)
    {
        Sistema sistema = new Sistema();
        cargarDatosSistema(sistema);

        int opcion;
        do {
            System.out.println("\n========= Menu ADMIN =========");
            System.out.println("1. Ver la lista completa de PC junto a informacion");
            System.out.println("2. Agregar o eliminar PC de la lista");
            System.out.println("3. Clasificar PC segun el nivel de riesgo");
            System.out.println("4. Generar reporte de escaneo");
            System.out.println("5. Salir del sistema");
            System.out.print(": ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    //Mostrar lista completa de PCs
                    System.out.println("\n========= LISTA COMPLETA DE PCs =========");
                    sistema.mostrarPcsUser();
                    break;

                case 2:
                    //Agregar o eliminar PC
                    System.out.println("\n========= GESTIÓN DE PCs =========");
                    System.out.println("1. Agregar nueva PC");
                    System.out.println("2. Eliminar PC existente");
                    System.out.println("3. Volver al menu anterior");
                    System.out.print(": ");
                    int subOpcion = scanner.nextInt();
                    scanner.nextLine();

                    switch (subOpcion) {
                        case 1:
                            System.out.print("Ingrese ID de la PC: ");
                            String nuevoId = scanner.nextLine();
                            System.out.print("Ingrese IP de la PC: ");
                            String nuevaIp = scanner.nextLine();
                            System.out.print("Ingrese Sistema Operativo: ");
                            String nuevoSo = scanner.nextLine();
                            sistema.agregarPC(nuevoId, nuevaIp, nuevoSo);
                            System.out.println("PC agregada (simulación)");
                            break;
                        case 2:
                            System.out.print("Ingrese ID o IP de la PC a eliminar: ");
                            String pcEliminar = scanner.nextLine();
                            sistema.eliminarPC(pcEliminar);
                            System.out.println("PC eliminada (simulación)");
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println("Opción no válida");
                    }
                    break;

                case 3:
                    //Clasificar PCs por nivel de riesgo
                    System.out.println("\n========= CLASIFICACIÓN POR NIVEL DE RIESGO =========");
                    sistema.clasificarPcsVul();
                    break;

                case 4:
                    //Generar reporte de escaneo
                    System.out.println("\n========= GENERAR REPORTE DE ESCANEO =========");
                    System.out.print("Ingrese ID o IP de la PC a escanear: ");
                    String pcEscanear = scanner.nextLine();
                    try
                    {
                        sistema.escanearPc(pcEscanear, username, "reportes.txt");
                    }
                    catch (Exception e)
                    {
                        System.err.println("Error al generar reporte: " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println("Saliendo del menu ADMIN...");
                    break;

                default:
                    System.out.println("Opción no válida");
            }
        } while (opcion != 5);
    }

    private static void menuUser(Scanner scanner, String username)
    {
        Sistema sistema = new Sistema();
        cargarDatosSistema(sistema);

        int opcion;
        do {
            System.out.println("\n========= Menu USER =========");
            System.out.println("1. Ver la lista completa de PC");
            System.out.println("2. Scanear PC actual");
            System.out.println("3. Ver total de puertos abiertos en la red");
            System.out.println("4. Ordenar PCs segun ip");
            System.out.println("5. Generar reporte de escaneo");
            System.out.println("6. Salir del sistema");
            System.out.print(": ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    //Mostrar lista completa de PCs
                    System.out.println("\n========= LISTA COMPLETA DE PCs =========");
                    sistema.mostrarPcsUser();
                    break;

                case 2:
                    // Scanear PC actual
                    System.out.println("\n========= SCANEAR PC ACTUAL =========");
                    System.out.print("Ingrese ID o IP de la PC a escanear: ");
                    String pcActual = scanner.nextLine();
                    try {
                        sistema.escanearPc(pcActual, username, "scan_pc_actual.txt");
                    } catch (Exception e) {
                        System.err.println("Error al escanear PC: " + e.getMessage());
                    }
                    break;

                case 3:
                    // Ver total de puertos abiertos en la red
                    System.out.println("\n========= TOTAL DE PUERTOS ABIERTOS =========");
                    sistema.mostrarTotalPuertosAbiertos();
                    break;

                case 4:
                    // Ordenar PCs por IP
                    System.out.println("\n========= PCs ORDENADAS POR IP =========");
                    sistema.mostrarPcsOrdenadasPorIP();
                    break;

                case 5:
                    // Generar reporte de escaneo
                    System.out.println("\n========= GENERAR REPORTE DE ESCANEO =========");
                    System.out.print("Ingrese ID o IP de la PC a escanear: ");
                    String pcEscanear = scanner.nextLine();
                    try {
                        sistema.escanearPc(pcEscanear, username, "reportes_user.txt");
                    } catch (Exception e) {
                        System.err.println("Error al generar reporte: " + e.getMessage());
                    }
                    break;

                case 6:
                    System.out.println("Saliendo del menu USER...");
                    break;

                default:
                    System.out.println("Opción no válida");
            }
        } while (opcion != 6);
    }

    private static void cargarDatosSistema(Sistema sistema)
    {
        try
        {
            sistema.cargarPCs("pcs.txt");
            sistema.cargarPuertos("puertos.txt");
            sistema.cargarVulnerabilidades("vulnerabilidades.txt");
            System.out.println("Datos del sistema cargados exitosamente");
        }
        catch (Exception e)
        {
            System.err.println("Error al cargar datos del sistema: " + e.getMessage());
        }
    }
}