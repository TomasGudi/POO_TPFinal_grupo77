package ar.edu.unju.escmi.main;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import ar.edu.unju.escmi.entities.*;
import ar.edu.unju.escmi.dao.*;
import ar.edu.unju.escmi.dao.imp.*;
import ar.edu.unju.escmi.exceptions.*;

public class Main {

	private static IClienteDao ClienteDAO = new ClienteDAOImpl();
	private static ISalonDao SalonDAO = new SalonDAOImpl();
	private static IServicioAdicionalDao ServicioAdicionalDAO = new ServicioAdicionalDAOImpl();
	private static IReservaDao ReservaDAO = new ReservaDAOImpl();

	public static void main(String[] args) {		
		int opcion = 0;
        Scanner scanner = new Scanner(System.in);
        
		do {
			try {
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
				scanner.nextLine();
				
				switch (opcion) {
				case 1:
					altaCliente(scanner);
					break;
				case 2:
					consultarClientes();
					break;
				case 3:
					modificarCliente(scanner);
					break;
				case 4:
					realizarPago(scanner);
					break;
				case 5:
					realizarReserva();
					break;
				case 6:
					consultarTodasLasReservas();
					break;
				case 7:
					consultarUnaReserva(scanner);
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
			} catch (InputMismatchException e) {
		        System.out.println("Error: Entrada inválida. Por favor, ingrese un valor numérico.");
		        scanner.nextLine();
		    }
		} while (opcion != 10);
		
		scanner.close(); 
	}

	private static void altaCliente(Scanner scanner) {
		System.out.print("Ingrese nombre del cliente: ");
		String nombre = scanner.nextLine();
		System.out.print("Ingrese apellido del cliente: ");
		String apellido = scanner.nextLine();
		System.out.print("Ingrese DNI del cliente: ");
		long dni = scanner.nextLong();
		scanner.nextLine();
		System.out.print("Ingrese domicilio del cliente: ");
		String domicilio = scanner.nextLine();
		System.out.print("Ingrese teléfono del cliente: ");
		String telefono = scanner.nextLine();

		Cliente cliente = new Cliente(nombre, apellido, domicilio, dni, telefono, true);
		ClienteDAO.guardar(cliente);
		System.out.println("Cliente registrado exitosamente.");
	}

	private static void consultarClientes() {
		try {
	        List<Cliente> clientes = ClienteDAO.obtenerTodos();

	        if (clientes.isEmpty()) {
	            throw new ClienteNoExisteException("No hay clientes activos en el sistema.");
	        }

	        System.out.println("\nListado de Clientes Activos:");
	        for (Cliente cliente : clientes) {
	            System.out.println(cliente);
	        }

	    } catch (ClienteNoExisteException e) {
	        System.out.println("Error: " + e.getMessage());
	    } catch (Exception e) {
	        System.out.println("Ocurrió un error inesperado: " + e.getMessage());
	    }
	}

	private static void modificarCliente(Scanner scanner) {
		System.out.print("Ingrese el ID del cliente a modificar: ");
		long id = scanner.nextLong();
		scanner.nextLine();

		Cliente cliente = ClienteDAO.buscarPorId(id);
		if (cliente == null) {
			System.out.println("Cliente no encontrado.");
			return;
		}

		System.out.print("Ingrese nuevo nombre del cliente (actual: " + cliente.getNombre() + "): ");
		cliente.setNombre(scanner.nextLine());
		System.out.print("Ingrese nuevo apellido del cliente (actual: " + cliente.getApellido() + "): ");
		cliente.setApellido(scanner.nextLine());
		System.out.print("Ingrese nuevo domicilio del cliente (actual: " + cliente.getDomicilio() + "): ");
		cliente.setDomicilio(scanner.nextLine());
		System.out.print("Ingrese nuevo teléfono del cliente (actual: " + cliente.getTelefono() + "): ");
		cliente.setTelefono(scanner.nextLine());

		ClienteDAO.actualizar(cliente);
		System.out.println("Cliente actualizado exitosamente.");
	}

	private static void realizarPago(Scanner scanner) {
		System.out.print("Ingrese el ID de la reserva: ");
		long reservaId = scanner.nextLong();
		scanner.nextLine();

		Reserva reserva = ReservaDAO.buscarPorId(reservaId);
		if (reserva == null) {
			System.out.println("Reserva no encontrada.");
			return;
		}

		System.out.println("Datos de la Reserva:");
		System.out.println(reserva);

		double montoPendiente = reserva.calcularPagoPendiente();
		System.out.println("Monto pendiente: $" + montoPendiente);

		System.out.print("Ingrese el monto a pagar: ");
		double pago = scanner.nextDouble();
		scanner.nextLine();

		if (pago > montoPendiente) {
			System.out.println("Error: El monto ingresado excede el monto pendiente.");
			return;
		}

		reserva.setPagoAdelantado(reserva.getPagoAdelantado() + pago);
		if (reserva.calcularPagoPendiente() >= 0) {
			reserva.setCancelado(true);
		}

		ReservaDAO.actualizar(reserva);
		System.out.println("Pago registrado exitosamente. Estado de la reserva: "
				+ (reserva.isCancelado() ? "CANCELADO" : "PAGO PENDIENTE"));
	}



	private static void realizarReserva() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Ingrese el DNI del cliente: ");
		long dni = scanner.nextLong();
		scanner.nextLine(); 
		Cliente cliente = ClienteDAO.buscarPorId(dni);
		if (cliente == null) {
			System.out.println("Cliente no encontrado. Procediendo a registrarlo.");
			altaCliente(scanner);
			cliente = ClienteDAO.buscarPorId(dni);
		}

		consultarSalones();
		System.out.print("Seleccione el ID del salón: ");
		long salonId = scanner.nextLong();
		scanner.nextLine();
		Salon salon = SalonDAO.buscarPorId(salonId);
		if (salon == null) {
			System.out.println("Salón no encontrado. Intente nuevamente.");
			return;
		}

		System.out.print("Ingrese la fecha de la reserva (AAAA-MM-DD): ");
		LocalDate fecha = LocalDate.parse(scanner.nextLine());
		System.out.print("Ingrese la hora de inicio (HH:MM): ");
		LocalTime horaInicio = LocalTime.parse(scanner.nextLine());
		LocalTime horaFin = horaInicio.plusHours(4);
		System.out.print("¿Desea agregar horas adicionales? (s/n): ");
		if (scanner.nextLine().equalsIgnoreCase("s")) {
			System.out.print("Ingrese la cantidad de horas adicionales: ");
			int horasExtras = scanner.nextInt();
			scanner.nextLine(); 
			horaFin = horaFin.plusHours(horasExtras);
		}

		try {
			ReservaDAO.verificarDisponibilidad(salon, fecha, horaInicio, horaFin);
		} catch (SalonNoDisponibleException e) {
			System.out.println("Error: " + e.getMessage());
			return;
		}

		consultarServiciosAdicionales();
		System.out.print("Ingrese el ID de los servicios adicionales (separados por comas, o vacío para ninguno): ");
		String serviciosInput = scanner.nextLine();
		List<ServicioAdicional> serviciosAdicionales = new ArrayList<>();
		if (!serviciosInput.isEmpty()) {
			for (String idStr : serviciosInput.split(",")) {
				long servicioId = Long.parseLong(idStr.trim());
				ServicioAdicional servicio = ServicioAdicionalDAO.buscarPorId(servicioId);
				if (servicio != null) {
					serviciosAdicionales.add(servicio);
					
				}
			}
		}

		double pagoAdelantado =	0;
		Reserva reserva = new Reserva(cliente, salon, fecha, horaInicio, horaFin, pagoAdelantado, serviciosAdicionales, true);
		System.out.print("¿Desea realizar un pago adelantado? (s/n): ");
		if (scanner.nextLine().equalsIgnoreCase("s")) {
			System.out.print("Ingrese el monto del pago adelantado: ");
			pagoAdelantado = scanner.nextDouble();
			scanner.nextLine(); 
			reserva.setPagoAdelantado(pagoAdelantado);
			
		}
		

		ReservaDAO.guardar(reserva);
		System.out.println("Reserva realizada exitosamente.");
	}

	private static void consultarTodasLasReservas() {
		System.out.println("\nListado de Reservas:");
		for (Reserva reserva : ReservaDAO.obtenerTodos()) {
			System.out.println(reserva);
		}
	}

	private static void consultarUnaReserva(Scanner scanner) {
		System.out.print("Ingrese el ID de la reserva: ");
		long reservaId = scanner.nextLong();
		scanner.nextLine(); 
		Reserva reserva = ReservaDAO.buscarPorId(reservaId);
		if (reserva == null) {
			System.out.println("Reserva no encontrada.");
		} else {
			System.out.println("Datos de la Reserva:");
			System.out.println(reserva);
		}
	}

	private static void consultarSalones() {
		System.out.println("\nListado de Salones:");
		for (Salon salon : SalonDAO.obtenerTodos()) {
			System.out.println(salon);
		}
	}

	private static void consultarServiciosAdicionales() {
		System.out.println("\nListado de Servicios Adicionales:");
		for (ServicioAdicional servicio : ServicioAdicionalDAO.obtenerTodos()) {
			System.out.println(servicio);
		}
	}
}