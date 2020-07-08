package piattaforme.hotel.altro.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import piattaforme.hotel.altro.entities.Prezzi;
import piattaforme.hotel.altro.services.PrezziService;
import piattaforme.hotel.altro.support.ResponseMessage;
import piattaforme.hotel.altro.support.exceptions.PrezziErratiException;
import piattaforme.hotel.altro.support.exceptions.PrezziNonPresentiException;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/prezzi")
public class PrezziController {

    @Autowired
    private PrezziService prezziService;

    @PostMapping("/modifica")
    public ResponseEntity modificaPrezzi(@RequestBody @Valid Prezzi prezzi) throws PrezziErratiException {
        prezziService.setPrezzi(prezzi);
        return new ResponseEntity<>(new ResponseMessage("Prezzi modificati"), HttpStatus.OK);
    }

    @GetMapping
    public Prezzi mostraPrezzi() throws PrezziNonPresentiException {
        return prezziService.getPrezzi();
    }
}
