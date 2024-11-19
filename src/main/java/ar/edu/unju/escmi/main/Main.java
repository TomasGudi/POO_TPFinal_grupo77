package ar.edu.unju.escmi.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ar.edu.unju.escmi.model.Cliente;
import ar.edu.unju.escmi.model.Reserva;

public class Main {

    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Reserva> reservas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\nMenú de opciones:");
            System.out.println("1. Alta de Cliente");
            System.out.println("2. Consultar Clientes");
            System.out.println("3. Modificar Cliente");
            System.out.println("4. Realizar Pago");
            System.out.println("5. Realizar Reserva");
            System.out.println("6. Consultar todas las Reservas");
            System.out.println("7. Consultar una Reserva");
            System.out.println("8. Consultar Salones");
            System.out.println("9. Consultar Servicios Adicionales");
            System.out.println("10. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    altaCliente();
                    break;
                case 2:
                    consultarClientes();
                    break;
                case 3:
                    modificarCliente();
                    break;
                case 4:
                    realizarPago();
                    break;
                case 5:
                    realizarReserva();
                    break;
                case 6:
                    consultarReservas();
                    break;
                case 7:
                    consultarUnaReserva();
                    break;
                case 8:
                    consultarSalones();
                    break;
                case 9:
                    consultarServiciosAdicionales();
                    break;
                case 10:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 10);
    }

    // Implementación de las funciones asignadas

    private static void altaCliente() {
        System.out.println("Alta de Cliente:");
        System.out.print("Ingrese ID: ");
        long id = scanner.nextLong();
        scanner.nextLine();
        System.out.print("Ingrese DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Ingrese Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese Domicilio: ");
        String domicilio = scanner.nextLine();
        System.out.print("Ingrese Teléfono: ");
        String telefono = scanner.nextLine();

        Cliente nuevoCliente = new Cliente(id, dni, nombre, apellido, domicilio, telefono, true);
        clientes.add(nuevoCliente);
        System.out.println("Cliente registrado exitosamente.");
    }

    private static void consultarClientes() {
        System.out.println("Consultar Clientes:");
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            for (Cliente cliente : clientes) {
                System.out.println(cliente.mostrarCliente());
            }
        }
    }

    private static void modificarCliente() {
        System.out.println("Modificar Cliente:");
        System.out.print("Ingrese el ID del cliente a modificar: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        Cliente cliente = buscarClientePorId(id);
        if (cliente != null) {
            System.out.println("Cliente encontrado: " + cliente.getNombre() + " " + cliente.getApellido());
            System.out.print("Ingrese el nuevo nombre (actual: " + cliente.getNombre() + "): ");
            cliente.setNombre(scanner.nextLine());
            System.out.print("Ingrese el nuevo apellido (actual: " + cliente.getApellido() + "): ");
            cliente.setApellido(scanner.nextLine());
            System.out.print("Ingrese el nuevo domicilio (actual: " + cliente.getDomicilio() + "): ");
            cliente.setDomicilio(scanner.nextLine());
            System.out.print("Ingrese el nuevo teléfono (actual: " + cliente.getTelefono() + "): ");
            cliente.setTelefono(scanner.nextLine());
            System.out.println("Datos del cliente actualizados correctamente.");
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void realizarPago() {
        System.out.println("Realizar Pago:");
        System.out.print("Ingrese el ID de la reserva: ");
        long idReserva = scanner.nextLong();
        scanner.nextLine();

        Reserva reserva = buscarReservaPorId(idReserva);
        if (reserva != null) {
            System.out.println("Datos de la reserva:");
            System.out.println(reserva.mostrarDatos());
            System.out.print("Ingrese el monto a pagar: ");
            double monto = scanner.nextDouble();
            scanner.nextLine();

            reserva.setMontoPagado(reserva.getMontoPagado() + monto);
            if (reserva.getMontoPagado() >= reserva.calcularMontoTotal()) {
                reserva.setCancelado(true);
                System.out.println("Pago completado. Reserva CANCELADA.");
            } else {
                System.out.println("Pago realizado. Monto restante: $" + reserva.calcularPagoPendiente());
            }
        } else {
            System.out.println("Reserva no encontrada.");
        }
    }

    private static Cliente buscarClientePorId(long id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

    private static Reserva buscarReservaPorId(long id) {
        for (Reserva reserva : reservas) {
            if (reserva.getId() == id) {
                return reserva;
            }
        }
        return null;
    }

    private static void realizarReserva() {
        
    }

    private static void consultarReservas() {
        
    }

    private static void consultarUnaReserva() {
        
    }

    private static void consultarSalones() {
        
    }

    private static void consultarServiciosAdicionales() {
        
    }
}