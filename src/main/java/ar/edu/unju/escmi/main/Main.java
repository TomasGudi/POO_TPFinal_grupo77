package ar.edu.unju.escmi.main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import ar.edu.unju.escmi.entities.*;
import ar.edu.unju.escmi.exceptions.ClienteNoExisteException;
import ar.edu.unju.escmi.exceptions.FechaNoDisponibleException;
import ar.edu.unju.escmi.dao.imp.*;

public class Main {
	 
	private static ClienteDaoImp clienteDaoImp = new ClienteDaoImp();
	private static ReservaDaoImp reservaDaoImp = new ReservaDaoImp();
	private static SalonDaoImp salonDaoImp = new SalonDaoImp();

	private static ServicioAdicional camara360 = null;
	private static ServicioAdicional cabinaFotos;
	private static ServicioAdicional filmacion;
	private static ServicioAdicional pintacaritas;
	
	private static List<Reserva> reservas = new ArrayList<>(); 
	
	private static int reservasSinPagar = 0;
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		boolean band=true;
		precargarSalones();
		precargarServicios();
		do {
			System.out.println("======== MENÚ PRINCIPAL ========");
	        System.out.println("1. Alta de cliente");
	        System.out.println("2. Consultar cliente");
	        System.out.println("3. Modificar cliente");
	        System.out.println("4. Realizar pago");
	        System.out.println("5. Realizar reserva");
	        System.out.println("6. Consultar todas las reservas");
	        System.out.println("7. Consultar una reserva");
	        System.out.println("8. Consultar salones");
	        System.out.println("9. Consultar servicios adicionales");
	        System.out.println("10. Salir");
	        System.out.println("===============================");
	        System.out.print("Seleccione una opción: ");
			String op = sc.nextLine();
			
			switch(op) {
			
			case "1":
				altaCliente(sc);
			break;
			
			case "2":
				boolean datoInvalido=true;
				do {
					try {
						System.out.print("\nIngrese el ID del cliente: ");
						long id = sc.nextLong();
						consultarCliente(id);
						datoInvalido=false;
					}
					catch(Exception e) {
						System.out.print("\nDato no valido, ingrese nuevamente el id.");
					}
					finally {
						sc.nextLine();
					}
				}while(datoInvalido);
			break;
			
			case "3":
				modificarCliente(sc);
			break;
			
			case "4":
				if(reservasSinPagar>0) {
					realizarPago(sc);
				}
				else {
					System.out.println("\nNo hay pagos pendientes.");
				}
			break;
			
			case "5":
				realizarReserva(sc);
			break;
			
			case "6":
				consultarReservas();
			break;
			
			case "7":
				datoInvalido=true;
				do {
					try {
						System.out.print("\nIngrese el ID de la reserva: ");
						long id = sc.nextLong();
						consultarUnaReserva(id);
						datoInvalido=false;
					}
					catch(Exception e) {
						System.out.println("\nDato no valido, ingrese nuevamente el ID.");
					}
					finally {
						sc.nextLine();
					}
				}while(datoInvalido);
			break;
			
			case "8":
				consultarSalones();
			break;
			
			case "9":
				consultarServicios();
			break;
			
			case "10":
				System.out.println("\n*****FIN DEL PROGRAMA*****\n");
				band=false;
			break;
			
			default: System.out.println("\nOpcion no disponible");
			}
		}while(band);
		
		sc.close(); 
	}
	
	public static void precargarSalones() {
		
		Salon salonCosmos = new Salon("Cosmos", 60, false, 20000);
	    Salon salonEsmeralda = new Salon("Esmeralda", 20, false, 10000);
	    Salon salonGalaxy = new Salon("Galaxy", 100, true, 50000);

        salonDaoImp.guardarSalon(salonCosmos);
        salonDaoImp.guardarSalon(salonEsmeralda);
        salonDaoImp.guardarSalon(salonGalaxy);

        System.out.println("\nSalones cargados correctamente.\n");
    }
		
	public static void precargarServicios() {

	    camara360 = new ServicioAdicional("Cámara 360", 5000);
	    cabinaFotos = new ServicioAdicional("Cabina de fotos", 3000);
	    filmacion = new ServicioAdicional("Filmación", 8000);
	    pintacaritas = new ServicioAdicional("Pintacaritas", 2000);    

	    System.out.println("\nServicios adicionales cargados correctamente.\n");
	}
	
	public static void altaCliente(Scanner sc) {

		System.out.print("\nIngrese nombre: ");
		String nombre = sc.nextLine();
		System.out.print("Ingrese apellido: ");
		String apellido = sc.nextLine();
		System.out.print("Ingrese domicilio: ");
		String domicilio = sc.nextLine();
		System.out.print("Ingrese el numero telefonico: ");
		String tel = sc.nextLine();
		
		boolean datoInvalido=false;
		int dni=0;
		do {
			try {
				datoInvalido=false;
				System.out.print("Ingrese DNI: ");
				dni = sc.nextInt();
			}
			catch(Exception e) {
				System.out.println("\nDato no valido, vuelva a ingresar el DNI");
				datoInvalido=true;
			}
			finally {
				sc.nextLine();
			}
		}while(datoInvalido);
		
		Cliente cliente = new Cliente(dni, nombre, apellido, domicilio, tel);
		
		clienteDaoImp.guardarCliente(cliente);
	 
		System.out.println("\nCliente registrado exitosamente.\n");
	}
	
	public static void consultarCliente(long id) {
		Cliente cli = clienteDaoImp.consultarCliente(id);
		try {
			clienteNoExiste(cli);
			cli.mostrarCliente();
		}
		catch(ClienteNoExisteException e) {
			System.out.println("\nEl cliente ingresado no existe.");
		}
	}
	
	public static void modificarCliente(Scanner sc) {
	    
		clienteDaoImp.mostrarTodosLosClientes();
		
		Cliente cliente = new Cliente();
	    long idCliente=0;
	    boolean datoInvalido=true;
	    do {
	    	try {
	    		System.out.print("\nIngrese el ID del cliente a modificar: ");
		        idCliente = sc.nextLong();
		        
		        cliente = clienteDaoImp.consultarCliente(idCliente);
		        
		        clienteNoExiste(cliente);
		        
		        datoInvalido=false;
	    	}
	    	catch(InputMismatchException | ClienteNoExisteException e) {
	    		System.out.println("\nDato no valido, ingrese nuevamente el ID.");
	    	}
	    	finally {
	    		sc.nextLine();
	    	}
	    } while (datoInvalido);

	    System.out.print("Ingrese el nuevo nombre: ");
	    cliente.setNombre(sc.nextLine());

	    System.out.print("Ingrese el nuevo apellido: ");
	    cliente.setApellido(sc.nextLine());

	    System.out.print("Ingrese el nuevo domicilio: ");
	    cliente.setDomicilio(sc.nextLine());
	    
	    System.out.print("Ingrese el nuevo telefono: ");
	    cliente.setTelefono(sc.nextLine());

		do {
			try {
				datoInvalido=false;
				System.out.print("Ingrese el nuevo DNI: ");
				cliente.setDni(sc.nextInt());
			}
			catch(Exception e) {
				System.out.println("\nDato no valido, vuelva a ingresar el DNI");
				datoInvalido=true;
			}
			finally {
				sc.nextLine();
			}
		}while(datoInvalido);
		
	    clienteDaoImp.modificarCliente(cliente);
	    System.out.println("\nDatos del cliente actualizados.\n");
	}

	public static void realizarPago(Scanner sc) {
		
		reservaDaoImp.mostrarTodosLasReservas();
		
		Reserva reserva = null;
		boolean datoInvalido=true;
		long idReserva=0;
		do {
			try {
				System.out.print("Ingrese el ID de la reserva: ");
				idReserva = sc.nextLong();
				
		        reserva = reservaDaoImp.consultarReserva(idReserva);
		        if (reserva == null) {
		            System.out.println("\nNo se encontró una reserva con el ID proporcionado.");
		        }
		        else datoInvalido=false;
			}
			catch(Exception e) {
				System.out.println("\nDato no valido, vuelva a ingresar el ID.");
			}
			finally {
				sc.nextLine();
			}
		}while(datoInvalido);

        if (reserva.isCancelado()) {
            System.out.println("La reserva ya está cancelada. No se requiere más pagos.");
            return;
        }

        datoInvalido=true;
        double monto = 0;
        do {
			try {
				System.out.println("monto faltante $"+reserva.calcularPagoPendiente());
				System.out.print("Ingrese el monto a pagar: ");
				monto = sc.nextDouble();
				datoInvalido=false;
			}
			catch(Exception e) {
				System.out.println("\nDato no valido, vuelva a ingresar el monto.");
			}
			finally {
				sc.nextLine();
			}
		}while(datoInvalido);
        
        reserva.setMontoPagado(reserva.getMontoPagado() + monto);

        if (reserva.getMontoPagado() >= reserva.calcularMontoTotal()) {
            
        	reserva.setCancelado(true);
        	
        	if(reserva.getMontoPagado() > reserva.calcularMontoTotal()) {
            	System.out.println("\nDinero devuelto: $"+(reserva.getMontoPagado()-reserva.calcularMontoTotal()));
            	reserva.setMontoPagado(reserva.calcularMontoTotal());
            }
            
        	reservasSinPagar--;
        	System.out.println("\nPago completo, la reserva ha sido cancelada.\n");
        } 
        else {
            System.out.println("\nPago realizado, monto pendiente: $" + reserva.calcularPagoPendiente());
        }

        reservaDaoImp.guardarReserva(reserva);
	}
	
	public static void realizarReserva(Scanner sc) {
		
		Reserva nuevaReserva = new Reserva();
		
		clienteDaoImp.mostrarTodosLosClientes();
		
		Cliente cliente = null;
	    long idCliente=0;
	    boolean datoInvalido=true;
	    do {
	    	try {
	    		System.out.print("\nIngrese el ID del cliente: ");
		        idCliente = sc.nextLong();
		        
		        cliente = clienteDaoImp.consultarCliente(idCliente);
		        
		        clienteNoExiste(cliente);
		        
		        datoInvalido=false;
	    	}
	    	catch(InputMismatchException |ClienteNoExisteException e) {
	    		System.out.println("\nDato no valido, ingrese nuevamente el ID.");
	    	}
	    	finally {
	    		sc.nextLine();
	    	}
	    } while (datoInvalido);

	    nuevaReserva.setCliente(cliente);
	    
        salonDaoImp.mostrarLosSalones();
	    
	    datoInvalido = true;
        Salon salon = null;
        do {
        	try {
        		System.out.print("Ingrese el ID del salón:");
                long idSalon = sc.nextLong();
                salon = salonDaoImp.consultarSalon(idSalon);

                if (salon == null) {
                    System.out.println("\nEl salón no existe. Intente nuevamente.");
                }
                else datoInvalido=false;
        	}
            catch(Exception e) {
            	System.out.println("\nDato no valido, ingrese nuvamente el ID.");
            }
        	finally {
        		sc.nextLine();
        	}
        } while (datoInvalido);

        nuevaReserva.setSalon(salon);
        
        datoInvalido=true;
        LocalDate fecha = null;
        do {
        	try {
        		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        		System.out.print("\nIngrese la fecha de la reserva(dd-mm-yyyy): ");
        		String fechaStr = sc.nextLine();
        		
        		fecha = LocalDate.parse(fechaStr, formato);
        		if(fecha.isBefore(LocalDate.now())) {
        			System.out.println("\nLa fecha de reserva no puede ser anterior a la actual, ingrese nuevamente la fecha.");
        			continue;
        		}
        		
        		for(Reserva reserva: reservas) {
        			if(fecha.equals(reserva.getFecha())) {
        				throw new FechaNoDisponibleException("La fecha no se encuentra disponible.");
        			}
        		}
        		
        		datoInvalido=false;
        	}
        	catch(Exception e){
        		System.out.println("\nEl formato no es valido, ingrese nuevamente la fecha.");
        	}
        }while(datoInvalido);
        
        nuevaReserva.setFecha(fecha);
        
        datoInvalido=true;
        short horaFin=0, horaInicio=0;
        do {
        	try {
        		System.out.println("Horario de 10am a 23pm.\n"+"El tiempo de reserva minimo es de 4 horas.\n");
                System.out.print("Ingrese la hora de inicio (formato 24 horas): ");
                horaInicio = sc.nextShort();

                System.out.print("Ingrese la hora de fin (formato 24 horas): ");
                horaFin = sc.nextShort();
                
                if((horaInicio < 10 || horaInicio > 23) || (horaFin < 10 || horaFin > 23)) {
                	System.out.println("\nLas horas elegidas se encuentran fuera del horario disponible, ingrese nuevamente.");
                	continue;
                }
                if(horaInicio > horaFin) {
                	System.out.println("\nLa hora de inicio no puede ser mayor a la hora de finalizacion, ingrese nuevamente.");
                	continue;
                }
                if(horaFin-horaInicio < 4) {
                	System.out.println("\nEl tiempo minimo de reserva es de 4 horas, ingrese nuevamente.");
                	continue;
                }
                
                datoInvalido = false;
        	}
        	catch(Exception e) {
        		System.out.println("\nDato no valido, ingrese nuevamente las horas.");
        	}
        	finally {
        		sc.nextLine();
        	}
        }while(datoInvalido);
       
        nuevaReserva.setHoraFin(horaFin);
        nuevaReserva.setHoraInicio(horaInicio);
        
        boolean agregarServicio = true;
        do {
            System.out.println("\n¿Desea incluir el servicio de Camara 360?(Costo Adicional: $5000)(s/n)");
            String respuesta = sc.nextLine();
            switch(respuesta) {
            case "s":
            	nuevaReserva.setServicios(camara360);
            	agregarServicio=false;
            break;
            case "n":
            	agregarServicio=false;
            break;
            default: System.out.println("\nOpcion no disponible, ingrese nuevamente.");
            }
        } while (agregarServicio);

        agregarServicio = true;
        do {
            System.out.println("\n¿Desea incluir el servicio de Cabina de fotos?(Costo Adicional: $3000)(s/n)");
            String respuesta = sc.nextLine();
            switch(respuesta) {
            case "s":
            	nuevaReserva.setServicios(cabinaFotos);
            	agregarServicio=false;
            break;
            case "n":
            	agregarServicio=false;
            break;
            default: System.out.println("\nOpcion no disponible, ingrese nuevamente.");
            }
        } while (agregarServicio);
        
        agregarServicio = true;
        do {
            System.out.println("\n¿Desea incluir el servicio de Filmacion?(Costo Adicional: $8000)(s/n)");
            String respuesta = sc.nextLine();
            switch(respuesta) {
            case "s":
            	nuevaReserva.setServicios(filmacion);
            	agregarServicio=false;
            break;
            case "n":
            	agregarServicio=false;
            break;
            default: System.out.println("\nOpcion no disponible, ingrese nuevamente.");
            }
        } while (agregarServicio);
        
        agregarServicio = true;
        do {
            System.out.println("\n¿Desea incluir el servicio de Pintacaritas?(Costo Adicional: $2000)(s/n)");
            String respuesta = sc.nextLine();
            switch(respuesta) {
            case "s":
            	nuevaReserva.setServicios(pintacaritas);
            	agregarServicio=false;
            break;
            case "n":
            	agregarServicio=false;
            break;
            default: System.out.println("\nOpcion no disponible, ingrese nuevamente.");
            }
        } while (agregarServicio);
        
        double montoTotal = nuevaReserva.calcularMontoTotal(); 

        System.out.println("\nMonto total de la reserva: " + montoTotal);

        
        datoInvalido=true;
        double pagoAdelantado=0;
        do {
        	try {
        		System.out.print("\nIngrese un pago adelantado (opcional, 0 si no desea): ");
        		pagoAdelantado = sc.nextDouble();
        		if (pagoAdelantado > montoTotal) {
                    System.out.println("\nEl pago adelantado no puede ser mayor al monto total de la reserva, ingrese nuevamente.");
                    continue;
                }
        		datoInvalido=false;
        	}
        	catch(Exception e) {
        		System.out.println("\nDato no valido, ingrese nuevamente el pago adelantado.");
        	}
        	finally {
        		sc.nextLine();
        	}
        }while(datoInvalido);
              
        nuevaReserva.setPagoAdelantado(pagoAdelantado);
        nuevaReserva.setMontoPagado(pagoAdelantado);
        nuevaReserva.setEstado(true);
        if (pagoAdelantado == montoTotal) {
        	nuevaReserva.setCancelado(true);
        }
        else {        	
        	nuevaReserva.setCancelado(false);
        }
        
        cliente.setReservas(nuevaReserva);
        salon.setReservas(nuevaReserva);
        reservas.add(nuevaReserva);
        reservaDaoImp.guardarReserva(nuevaReserva);

        reservasSinPagar++;
        System.out.println("\nReserva creada con éxito.");
        nuevaReserva.mostrarDatos();
	}
	
	public static void consultarReservas() {
		 if(reservas.isEmpty()) {
			 System.out.println("\nNingun salon se encuentra reservado.");
		 }
		 else {
			 reservaDaoImp.mostrarTodosLasReservas();
		 }
	}
	
	public static void consultarUnaReserva(long idReserva) {
		Reserva reserva = reservaDaoImp.consultarReserva(idReserva);
	    if (reserva != null) {
	    	reserva.mostrarDatos();
	    } 
	    else {
	    	System.out.println("\nNo se encontró una reserva con el ID especificado.");
	    }

	}
	
	public static void consultarSalones() {
		salonDaoImp.mostrarLosSalones();
	}
	
	public static void consultarServicios() {
		camara360.mostrarDatos();
		cabinaFotos.mostrarDatos();
		filmacion.mostrarDatos();
		pintacaritas.mostrarDatos();
	}
	
	public static void clienteNoExiste(Cliente cliente) throws ClienteNoExisteException {
		if(cliente==null) {
			throw new ClienteNoExisteException("El cliente no existe.");
		}
	}
	
}