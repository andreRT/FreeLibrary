package com.andre.freelibrary.principal;

import com.andre.freelibrary.model.DatosAutor;
import com.andre.freelibrary.model.DatosLibro;
import com.andre.freelibrary.model.Libro;
import com.andre.freelibrary.model.Autor;
import com.andre.freelibrary.repository.BookRepository;
import com.andre.freelibrary.service.ConsumoAPI;
import com.andre.freelibrary.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books?search=";
    private ConvierteDatos conversor = new ConvierteDatos();

    private List<DatosLibro> datosLibro = new ArrayList<>();
    private BookRepository repositorio;

    public Principal(BookRepository repository) {
        this.repositorio = repository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ****************************************
                    
                    Ingrese el numero de la opcion que desee
                    
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLibrosIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private DatosLibro getDatosLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));
        System.out.println("Respuesta JSON: " + json);

        if (json == null || json.isEmpty()) {
            System.out.println("No se recibió contenido de la API.");
            return null;
        }

        DatosLibro datos = null;
        try {
            datos = conversor.obtenerDatos(json, DatosLibro.class);
        } catch (RuntimeException e) {
            System.err.println("Error al convertir JSON a DatosLibro: " + e.getMessage());
        }

        return datos;
    }

    private void buscarLibroTitulo() {
        DatosLibro datos = getDatosLibro();
        if (datos == null) {
            System.out.println("No se pudo obtener datos del libro.");
            return;
        }
        Libro libro = new Libro(datos);

        // Asocia autores
        List<Autor> autores = new ArrayList<>();
        for (DatosAutor datosAutor : datos.autores()) {
            Autor autor = new Autor(datosAutor);
            autor.getLibros().add(libro);
            autores.add(autor);
        }
        libro.setAutores(autores);

        repositorio.save(libro);
        System.out.println(datos);
    }

    private void listarLibrosRegistrados() {
        // Implementar lógica para listar libros registrados
    }

    private void listarAutoresRegistrados() {
        // Implementar lógica para listar autores registrados
    }

    private void listarAutoresVivos() {
        // Implementar lógica para listar autores vivos en un determinado año
    }

    private void listarLibrosIdioma() {
        // Implementar lógica para listar libros por idioma
    }
}


