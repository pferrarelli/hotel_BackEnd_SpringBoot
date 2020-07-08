package piattaforme.hotel.altro.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import piattaforme.hotel.altro.entities.Cliente;
import piattaforme.hotel.altro.services.ClienteService;
import piattaforme.hotel.altro.support.ResponseMessage;
import piattaforme.hotel.altro.support.exceptions.ClienteAlredyExistsException;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity creaCliente(@RequestBody @Valid Cliente cliente){
        try{
            clienteService.addCliente(cliente);
        }catch(ClienteAlredyExistsException ce){
            return new ResponseEntity<>(new ResponseMessage("Il cliente esiste."), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseMessage("Cliente aggiunto"), HttpStatus.OK);
    }

    @GetMapping("/tutti")
    public List<Cliente> getAll() {
        return clienteService.getAllClienti();
    }

    @GetMapping("/uno")
    public Cliente verifica(@RequestParam(value="email") String email){
        return clienteService.getClienteByEmail(email);
    }

}
