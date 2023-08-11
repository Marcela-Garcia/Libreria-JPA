/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import entidad.Cliente;
import entidad.Libro;
import entidad.Prestamo;
import persistencia.PrestamoDAO;

import java.time.LocalDate;
import java.util.Scanner;

public class PrestamoService {
    private final PrestamoDAO DAO;
    Scanner leer = new Scanner(System.in).useDelimiter("\n");

    public PrestamoService() {
        this.DAO = new PrestamoDAO();
    }

    public void crearPrestamo() {
        LocalDate hoy = LocalDate.now();

        Libro libro;
        while (true) {
            try {
                libro = (new LibroService()).buscarLibroPorISBN();
                if (libro != null) {
                    System.out.println("Libro encontrado!");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Ingrese un valor valido!");
                leer.next();
            }
        }

        if (libro.getEjemplaresRestantes() == 0) {
            System.out.println("No hay mas ejemplares disponibles!");
            return;
        }

        Cliente cliente;
        while (true) {
            try {
                cliente = (new ClienteService()).buscarClientePorDocumento();
                if (cliente != null) {
                    System.out.println("Cliente encontrado!");
                    break;
                }
            } catch (Exception e) {
                System.out.println("Ingrese un valor valido!");
                leer.next();
            }
        }
        LocalDate fechaPrestamo;
        while (true) {
            try {
                System.out.println("Ingrese la fecha del prestamo en formato (yyyy-MM-dd):");
                String fecha = leer.next();
                fechaPrestamo = LocalDate.parse(fecha);
                System.out.println(fechaPrestamo);
                break;
            } catch (Exception e) {
                System.out.println("Ingrese un valor valido!");
            }
        }
        LocalDate fechaDevolucion;
        while (true) {
            try {
                System.out.println("Ingrese la fecha de devolucion en formato (yyyy-MM-dd):");
                String fecha = leer.next();
                fechaDevolucion = LocalDate.parse(fecha);
                System.out.println(fechaDevolucion);
                break;
            } catch (Exception e) {
                System.out.println("Ingrese un valor valido!");
            }
        }

        if (fechaDevolucion.isBefore(fechaPrestamo)) {
            System.out.println("La fecha de devolucion no puede ser anterior a la fecha de prestamo!");
            return;
        }
        if (fechaDevolucion.isBefore(hoy)) {
            System.out.println("La fecha de devolucion no puede ser anterior a la fecha actual!");
            return;
        }

        if (libro.getEjemplaresRestantes() == 0) {
            System.out.println("No hay mas ejemplares disponibles!");
        } else {
            LibroService libroService = new LibroService();
            libro.setEjemplaresRestantes(libro.getEjemplaresRestantes() - 1);
            libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() + 1);
            libroService.editar(libro);
            Prestamo prestamo = new Prestamo(fechaPrestamo, fechaDevolucion, libro, cliente);
            DAO.guardar(prestamo);
            System.out.println("Prestamo creado!");
        }
    }

    public void listarPrestamos() {
        Cliente cliente = (new ClienteService()).buscarClientePorDocumento();
        if (cliente == null) {
            System.out.println("El cliente no existe!");
        } else {
            DAO.listarPrestamosPorCliente(cliente);
        }
    }

    public void devolverPrestamo() {
        Cliente cliente = (new ClienteService()).buscarClientePorDocumento();
        if (cliente == null) {
            System.out.println("El cliente no existe!");
        } else {
            DAO.listarPrestamosPorCliente(cliente);
        }
        System.out.println("Ingrese id  del prestamo que desea devolver:");
        int id = leer.nextInt();
        Prestamo prestamo = DAO.buscarPrestamoPorID(id);
        if (prestamo == null) {
            System.out.println("El prestamo no existe!");
        } else {
            LibroService libroService = new LibroService();
            Libro libro = prestamo.getLibro();
            libro.setEjemplaresRestantes(libro.getEjemplaresRestantes() + 1);
            libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() - 1);
            libroService.editar(libro);
            DAO.eliminar(prestamo);
            System.out.println("Prestamo devuelto!");
        }
    }

    public void editar(Prestamo prestamo) {
        DAO.editar(prestamo);
    }

}