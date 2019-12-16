package micro.usuarios.publico.services.externo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import dto.main.Respuesta;
import dto.micro.correo.Mail;
import micro.usuarios.publico.services.EmailService;

@FeignClient(name = "micro-correos" , url = "http://localhost:8603/micro-correo" , fallback = EmailService.class)
public interface IEmailExternalService {

    @PostMapping(value = "/correo/registro")
    Respuesta<Boolean> registro( @RequestBody Mail correo );

    @PostMapping(value = "/correo/cambiar/password")
    Respuesta<Boolean> cambiarPassword( @RequestBody Mail correo );
    
}
