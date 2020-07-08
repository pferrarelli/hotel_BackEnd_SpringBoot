package piattaforme.hotel.altro.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import piattaforme.hotel.altro.entities.Camera;
import piattaforme.hotel.altro.services.CameraService;
import piattaforme.hotel.altro.support.enums.TipoCamera;
import piattaforme.hotel.altro.support.exceptions.CameraAlredyExistsException;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/camera")
public class CameraController {

    @Autowired
    private CameraService cameraService;

    @PostMapping("/crea")
    public boolean creaCamera(@RequestBody @Valid Camera camera) {
        try{
            cameraService.addCamera(camera);
        } catch (CameraAlredyExistsException e) {
            return false;
        }
        return true;
    }


    @GetMapping("/mostra")
    public List<Camera> mostraCamere() {
        return cameraService.getAllCamere();
    }

    @GetMapping("/size")
    public int getSize(@RequestParam(value="from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date in,
                       @RequestParam(value="to") @DateTimeFormat(pattern = "yyyy-MM-dd")Date fin,
                       @RequestParam(value="tipo") TipoCamera tipoCamera){

        return cameraService.getQuantitaByTipoAndPeriodo(in,fin,tipoCamera);
    }

    @GetMapping("/conta")
    public int conteggio(){
        return cameraService.conta();
    }

    @GetMapping("/getCamere")
    public List<Camera> mostraCamere(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return cameraService.getAllCamere(pageNumber, pageSize, "numero");
    }

    @DeleteMapping("/elimina")
    public boolean rimuovi(@RequestParam(value="id") int id) {
        return cameraService.rimuoviCamera(id);
    }
}
