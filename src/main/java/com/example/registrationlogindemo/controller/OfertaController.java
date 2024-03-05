package com.example.registrationlogindemo.controller;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.service.UserService;
import com.example.registrationlogindemo.storage.StorageService;
import org.springframework.security.core.Authentication;
import com.example.registrationlogindemo.entity.Equipos;
import com.example.registrationlogindemo.entity.Oferta;
import com.example.registrationlogindemo.service.ServicioEquipo;
import com.example.registrationlogindemo.service.ServicioOferta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@Controller
public class OfertaController {

    @Autowired
    private ServicioOferta servicioOferta;

    @Autowired
    private ServicioEquipo servicioEquipo;

    @Autowired
    UserService servicioUsuarios;

    @Autowired
    public StorageService storageService;
    @GetMapping("/crud")
    public String listadoOferta(Model model,Authentication authentication){

        String username = authentication.getName();
        model.addAttribute("username", username);


        model.addAttribute("ofertas", servicioOferta.buscarOfertasPorEmailPropietario(username));
        return "crud";
    }
    @GetMapping("/crud/add")
    public String addOferta(Model model, Authentication authentication) {
        System.out.println("Inicio modificar");
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            model.addAttribute("username", username);
        }

        Oferta nuevaOferta = new Oferta();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User propietario = servicioUsuarios.findByEmail(username);
            nuevaOferta.setPropietario(propietario);
        }

        model.addAttribute("formOferta", nuevaOferta);

        List<Equipos> equipos = servicioEquipo.findAll();
        model.addAttribute("equipos", equipos);

        return "add";
    }

    /*@PostMapping("/crud/save")
    public String guardarOferta(@ModelAttribute("formOferta") Oferta formOferta, @RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            String vehiculo = storageService.store(file, String.valueOf(formOferta.getId()));
            System.out.println("La imagen a guardar es : " + vehiculo);

            formOferta.setVehiculo(MvcUriComponentsBuilder
                    .fromMethodName(FileUploadController.class, "serveFile", vehiculo).build().toUriString());
        }


        storageService.store(file, "file");
        servicioOferta.save(formOferta);
        return "redirect:/crud/add";
    }

     */

    @PostMapping("/crud/save")
    public String guardarOferta(@ModelAttribute("formOferta") Oferta formOferta,
                                @RequestParam("file") MultipartFile file){

        if (!file.isEmpty()) {
            String vehiculo = storageService.store(file, String.valueOf(formOferta.getId()));
            System.out.println("La imagen a guardar es : " + vehiculo);

            formOferta.setVehiculo(MvcUriComponentsBuilder
                    .fromMethodName(FileUploadController.class, "serveFile", vehiculo).build().toUriString());
        }


        storageService.store(file, "file");
        servicioOferta.save(formOferta);
        return "redirect:/crud/add";
    }



    @GetMapping("/crud/update/{id}")
    public String muestraOferta(@PathVariable long id, Model model){
        Oferta o= servicioOferta.findById(id);
        //El nombre del objeto debe ser el mismo que en el GetMapping de añadir
        //Y el mismo que en el th:object del formulario
        model.addAttribute("formOferta", o);
        List<Equipos> equipos = servicioEquipo.findAll();
        model.addAttribute("equipos", equipos);

        return "add";
    }
    @PostMapping("/crud/modificar")
    public String modificarOferta(@ModelAttribute("formOferta")Oferta o){
        servicioOferta.save(o);
        return "redirect:/crud";
    }

    @GetMapping("/crud/delete/{id}")
    public String borrarOferta(@PathVariable long id, Model model){

        servicioOferta.deleteById(id);
        return "redirect:/crud";
    }
}



