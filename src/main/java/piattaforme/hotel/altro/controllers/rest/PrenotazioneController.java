package piattaforme.hotel.altro.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import piattaforme.hotel.altro.entities.Prenotazione;
import piattaforme.hotel.altro.services.ClienteService;
import piattaforme.hotel.altro.services.PrenotazioneService;
import piattaforme.hotel.altro.support.exceptions.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/prenotazione")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;
    @Autowired
    private ClienteService clienteService;

    @PostMapping("/crea")
    public int creaPrenotazione(@RequestBody @Valid Prenotazione prenotazione,
                                           @RequestParam(value="nrDoppie",defaultValue ="0")int doppie,
                                           @RequestParam(value="nrSingole",defaultValue ="0")int singole,
                                           @RequestParam(value="nrMatrimoniali",defaultValue = "0")int matrimoniali,
                                           @RequestParam(value="nrTriple", defaultValue = "0")int triple) {


        if((prenotazione.getCheckin()).compareTo(prenotazione.getCheckout())>=0){
            return -6;
        }
        if(doppie+singole+matrimoniali+triple ==0){
            return -7;
        }
        try{
            return prenotazioneService.addPrenotazione(prenotazione,doppie,matrimoniali,singole,triple).getCodicePrenotazione();
        } catch (DoppiaNonDisponibileException e) {
            return -5;
        } catch (TriplaNonDisponibileException e) {
            return -4;
        } catch (MatrimonialeNonDisponibileException e) {
            return -3;
        } catch (SingolaNonDisponibileException e) {
            return -2;
        } catch (ClienteNotExistsException e) {
            return -1;
        }
    }

    @GetMapping("/mostra")
    public List<Prenotazione> mostraPrenotazioni(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return prenotazioneService.getAllPrenotazioni(pageNumber, pageSize, "checkin");
    }

    @GetMapping("/search")
    public List<Prenotazione> prenotazioniByPeriodAndCliente(@RequestParam(value="checkin") Date checkin,
                                                             @RequestParam(value="checkout")Date checkout,
                                                             @RequestParam(value="email_cliente")String email_cliente) throws DateErrateException, ClienteNotExistsException {
        if(checkin.compareTo(checkout)>=0){
            throw new DateErrateException();
        }
        if(!clienteService.clienteExists(email_cliente)){
            throw new ClienteNotExistsException();
        }
        return prenotazioneService.getPrenotazioniByPeriod(checkin,checkout,email_cliente);
    }

    @GetMapping("/codice")
    public Prenotazione cercaConCodice(@RequestParam(value="code") int codicePrenotazione){
        return prenotazioneService.cercaPerCodice(codicePrenotazione);
    }

    @DeleteMapping("/elimina")
    public boolean rimuovi(@RequestParam(value="codice") int codicePrenotazione){
        return prenotazioneService.rimuoviPrenotazione(codicePrenotazione);
    }

    @GetMapping("/conta")
    public int conteggio(){
        return prenotazioneService.conta();
    }

    @GetMapping("/byCheckOut")
    public List<Prenotazione> mostraPrenotazionibyCheckOut(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return prenotazioneService.getAllPrenotazioni(pageNumber, pageSize, "checkout");
    }

}
