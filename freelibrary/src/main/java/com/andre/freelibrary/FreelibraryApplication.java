package com.andre.freelibrary;

import com.andre.freelibrary.principal.Principal;
import com.andre.freelibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FreelibraryApplication implements CommandLineRunner {

	/*public static void main(String[] args) {
		SpringApplication.run(FreelibraryApplication.class, args);
	}*/
	@Autowired
	private BookRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(FreelibraryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository);
		principal.muestraElMenu();

	}

}
