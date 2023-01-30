package com.example.registrationlogindemo;

import com.example.registrationlogindemo.entity.Mensaje;
import com.example.registrationlogindemo.entity.Usuario;
import com.example.registrationlogindemo.service.ServicioMensajes;
import com.example.registrationlogindemo.service.ServicioUsuarios;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Locale;

@SpringBootApplication
public class RegistrationLoginDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistrationLoginDemoApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(ServicioUsuarios servicioUsuarios){
		return args -> {
			if(servicioUsuarios.findAll().size()<1) {
				servicioUsuarios.save(new Usuario("antonio", "antonio@antonio.com", "https://i.pravatar.cc/150?u=antonio@antonio.com"));
				for (int i = 0; i < 10; i++) {
					String correo="maria" + i + "@benito.com";
					servicioUsuarios.save(new Usuario("María " + i, correo, "https://i.pravatar.cc/150?u=" + correo));
				}
			}
		};
	}

	@Bean
	CommandLineRunner addMensajes(ServicioMensajes servicio, ServicioUsuarios servicioUsuarios){
		return args -> {
			//Si no hay mensajes, creo 10 mensajes entre los usurios antonio y María 1
			if(servicio.findAll().size()<1) {
				Usuario antonio=servicioUsuarios.findByUsername("antonio");
				Usuario maria1=servicioUsuarios.findByUsername("María 1");
				Faker faker = new Faker(new Locale("es-ES"));
				for (int i = 0; i < 10; i++) {
					//El usuario antonio envía un mensaje a María
					Mensaje mensaje=new Mensaje(antonio, maria1, faker.chuckNorris().fact());
					mensaje.setFecha(LocalDate.now().minusDays(i).atStartOfDay());
					servicio.save(mensaje);
					//Y viceversa
					Mensaje mensaje2=new Mensaje(maria1, antonio, faker.backToTheFuture().quote());
					mensaje2.setFecha(LocalDate.now().minusDays(i).atStartOfDay());
					servicio.save(mensaje2);
				}
			}
		};
	}
}
