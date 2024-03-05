package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.entity.*;
import com.example.registrationlogindemo.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class Principal {
    @Autowired
    ServicioMensajes servicioMensajes;
    @Autowired
    UserService servicioUsuarios;

    @Autowired
    ServicioEquipo servicioEquipo;

    @Autowired
    ServicioOferta servicioOferta;

    @Autowired
    ServicioOpiniones servicioOpiniones;
    @Autowired
    ServicioPasajero servicioPasajero;

    @GetMapping("/")
    public String inicio(Model model, Authentication authentication) {
        List<Oferta> todasLasOfertas = servicioOferta.findAll();

        if (authentication != null && authentication.isAuthenticated()) {
            String propietarioEmail = authentication.getName();

            List<Oferta> ofertasDelPropietario = servicioOferta.findOfertasByPropietarioEmail(propietarioEmail);

            todasLasOfertas.removeAll(ofertasDelPropietario);

            model.addAttribute("username", propietarioEmail);
        }

        model.addAttribute("propuesta", todasLasOfertas);

        return "ofertas";
    }

    @GetMapping("/misOfertas")
    public String misOfertas(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String propietarioEmail = authentication.getName();


            List<Oferta> ofertasDelPropietario = servicioOferta.findOfertasByPropietarioEmail(propietarioEmail);

            model.addAttribute("username", propietarioEmail);
            model.addAttribute("propuesta", ofertasDelPropietario);
        }

        return "ofertas";
    }



    @GetMapping("/oferta/{id}")
    public String oferta(@PathVariable long id, Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("username", username);

        Oferta o = servicioOferta.findById(id);

        model.addAttribute("o", o);

        List<Pasajero> pasajeros = servicioPasajero.findPasajerosByOfertaId(id);
        model.addAttribute("pasajeros", pasajeros);

        List<User> allUsers = servicioUsuarios.findAll();
        model.addAttribute("allUsers", allUsers);


        Pasajero nuevoPasajero = new Pasajero();
        model.addAttribute("nuevoPasajero", nuevoPasajero);

        User actual=servicioUsuarios.findByEmail(authentication.getName());
        User destinatario = o.getPropietario();
        model.addAttribute("actual", actual);
        model.addAttribute("receptor", destinatario);;

        //Debo enviar a la vista la lista de mensajes de "actual" a "destinatario" y viceversa
        List<Mensaje> lista1=servicioMensajes.findByEmisorAndDestinatario(actual, destinatario);
        List<Mensaje> lista2=servicioMensajes.findByEmisorAndDestinatario(destinatario, actual);

        //Mezclo las dos listas en una y la ordeno por fecha
        List<Mensaje> mezcla=new ArrayList<>();
        mezcla.addAll(lista1);
        mezcla.addAll(lista2);
        Collections.sort(mezcla, new Comparator<Mensaje>() {
            @Override
            public int compare(Mensaje m1, Mensaje m2) {
                return m1.getFecha().compareTo(m2.getFecha());
            }
        });
        model.addAttribute("listaMensajes", mezcla);

        //Envío un mensaje vacío que es el que después nos devolverá en PostMapping si escriben uno
        Mensaje mensaje=new Mensaje();
        mensaje.setEmisor(actual);
        mensaje.setDestinatario(destinatario);
        model.addAttribute("mensaje", new Mensaje());


        return "detalleOferta";
    }

    @PostMapping("/enviar")
    public String guardarMensaje(@ModelAttribute("mensaje") Mensaje mensaje,
                                 HttpServletRequest request, //Esto es para volver a la página desde la que nos han "llamado"
                                 @RequestParam("emisor") Long emisorid, //Es el id de quien envía el mensaje
                                 @RequestParam("destinatario") Long destinatarioid) // Es el id de quien recibe el mensaje
    {
        mensaje.setFecha(LocalDateTime.now());
        mensaje.setEmisor(servicioUsuarios.findById(emisorid));
        mensaje.setDestinatario(servicioUsuarios.findById(destinatarioid));
        servicioMensajes.save(mensaje);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }


    @PostMapping("/oferta/{id}/apuntarse")
    public String apuntarse(@PathVariable long id, @ModelAttribute Pasajero nuevoPasajero, Authentication authentication) {
        String username = authentication.getName();

        // Obtener la oferta
        Oferta oferta = servicioOferta.findById(id);

        // Obtener el usuario
        User usuario = servicioUsuarios.findByEmail(username);

        // Configurar la oferta y el usuario en el nuevo pasajero
        nuevoPasajero.setOferta(oferta);
        nuevoPasajero.setUsuario(usuario);

        // Guardar el nuevo pasajero en la base de datos
        servicioPasajero.save(nuevoPasajero);

        // Restar una plaza utilizando la query personalizada
        servicioOferta.restarPlaza(oferta.getId());

        return "redirect:/";
    }
    @GetMapping("/user")
    public String miPerfil(Authentication authentication, Model model) {

        String username = authentication.getName();
        model.addAttribute("username", username);

        User u = servicioUsuarios.findByEmail(username);
        model.addAttribute("u", u);


        model.addAttribute("opiniones", servicioOpiniones.findByUser(u));
        model.addAttribute("nuevoOpiniones", new Opiniones());


        return "detalleUser";
    }


    @GetMapping("/user/{email}")
    public String user(@PathVariable String email, Model model,Authentication authentication) {


        User u = servicioUsuarios.findByEmail(email);
        model.addAttribute("u", u);

        Opiniones nuevaOpinion = new Opiniones();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User opinador = servicioUsuarios.findByEmail(username);
            nuevaOpinion.setOpinador(opinador);
        }

        model.addAttribute("opiniones", servicioOpiniones.findByUser(u));
        model.addAttribute("nuevoOpiniones", nuevaOpinion);


        return "detalleUser";
    }

    @PostMapping("/opiniones/add")
    public String guardarComentario(@ModelAttribute("nuevoOpiniones") Opiniones opiniones, @RequestParam String email, Authentication authentication) {
        opiniones.setFecha(LocalDate.now());

        String username = authentication.getName();

        if (email.equalsIgnoreCase(username)) {
            return "redirect:/user/" + opiniones.getUser().getEmail();
        }

        User user = servicioUsuarios.findByEmail(email);
        opiniones.setUser(user);
        servicioOpiniones.save(opiniones);

        return "redirect:/user/" + opiniones.getUser().getEmail();
    }

    @GetMapping("/equipos")
    public String inicio(Model model){
        model.addAttribute("equipos", servicioEquipo.findAll());
        return "equipos";
    }


    /*@GetMapping("/")
    public String principal(Model model){
        //Recupero la lista de usuarios y la mando a la plantilla para decir quién soy
        model.addAttribute("lista", servicioUsuarios.findAll());
        return "destinatario";
    }

    @GetMapping("/chat/{id}")
    public String chat(@PathVariable long id, Model model, HttpSession request, Authentication authentication){

        User actual=servicioUsuarios.findByEmail(authentication.getName());
        User destinatario=servicioUsuarios.findById(id);
        model.addAttribute("actual", actual);
        model.addAttribute("receptor", destinatario);

        //Debo enviar a la vista la lista de mensajes de "actual" a "destinatario" y viceversa
        List<Mensaje> lista1=servicioMensajes.findByEmisorAndDestinatario(actual, destinatario);
        List<Mensaje> lista2=servicioMensajes.findByEmisorAndDestinatario(destinatario, actual);

        //Mezclo las dos listas en una y la ordeno por fecha
        List<Mensaje> mezcla=new ArrayList<>();
        mezcla.addAll(lista1);
        mezcla.addAll(lista2);
        Collections.sort(mezcla, new Comparator<Mensaje>() {
            @Override
            public int compare(Mensaje m1, Mensaje m2) {
                return m1.getFecha().compareTo(m2.getFecha());
            }
        });
        model.addAttribute("listaMensajes", mezcla);

        //Envío un mensaje vacío que es el que después nos devolverá en PostMapping si escriben uno
        Mensaje mensaje=new Mensaje();
        mensaje.setEmisor(actual);
        mensaje.setDestinatario(destinatario);
        model.addAttribute("mensaje", new Mensaje());
        return "chat";
    }

    @PostMapping("/enviar")
    public String guardarMensaje(@ModelAttribute("mensaje") Mensaje mensaje,
                                 HttpServletRequest request, //Esto es para volver a la página desde la que nos han "llamado"
                                 @RequestParam("emisor") Long emisorid, //Es el id de quien envía el mensaje
                                 @RequestParam("destinatario") Long destinatarioid) // Es el id de quien recibe el mensaje
    {
        mensaje.setFecha(LocalDateTime.now());
        mensaje.setEmisor(servicioUsuarios.findById(emisorid));
        mensaje.setDestinatario(servicioUsuarios.findById(destinatarioid));
        servicioMensajes.save(mensaje);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

     */
}
