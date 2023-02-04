package com.example.registrationlogindemo;

import com.example.registrationlogindemo.entity.Mensaje;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.service.ServicioMensajes;
import com.example.registrationlogindemo.service.UserService;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Locale;

@SpringBootApplication
public class SpringChatBasicoSeguridadApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringChatBasicoSeguridadApp.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(UserService servicioUsuarios){
		return args -> {
			if(servicioUsuarios.findAll().size()<1) {
				//La contraseña es 1234
				servicioUsuarios.save(new User("antonio", "asalinasci@gmail.com", "https://i.pravatar.cc/150?u=antonio@antonio.com", "$2a$12$QO8HqfpzA7cUGlyDFQ5/FeKfH.laaMRIFsQiQX8oCVStWX0HavrTW"));
				for (int i = 0; i < 10; i++) {
					String correo="maria" + i + "@benito.com";
					//La contraseña es 1234
					servicioUsuarios.save(new User("María " + i, correo, "https://i.pravatar.cc/150?u=" + correo, "$2a$12$QO8HqfpzA7cUGlyDFQ5/FeKfH.laaMRIFsQiQX8oCVStWX0HavrTW"));
				}
			}
		};
	}
	@Bean
	CommandLineRunner addMensajes(ServicioMensajes servicio, UserService servicioUsuarios){
		return args -> {
			//Si no hay mensajes, creo 10 mensajes entre los usurios antonio y María 1
			if(servicio.findAll().size()<1) {
				User antonio=servicioUsuarios.findByEmail("asalinasci@gmail.com");
				User maria1=servicioUsuarios.findByEmail("maria1@benito.com");
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
