package piattaforme.hotel.altro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import piattaforme.hotel.altro.entities.Camera;
import piattaforme.hotel.altro.entities.Cliente;
import piattaforme.hotel.altro.entities.Prenotazione;
import piattaforme.hotel.altro.entities.Prezzi;
import piattaforme.hotel.altro.repositories.CameraRepository;
import piattaforme.hotel.altro.repositories.ClienteRepository;
import piattaforme.hotel.altro.repositories.PrenotazioneRepository;
import piattaforme.hotel.altro.repositories.PrezziRepository;
import piattaforme.hotel.altro.support.enums.TipoCamera;
import piattaforme.hotel.altro.support.exceptions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;
    @Autowired
    private CameraRepository cameraRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PrezziRepository prezziRepository;

    @Transactional(readOnly = false)
    public Prenotazione addPrenotazione(Prenotazione prenotazione, int nrDoppie, int nrMatrimoniali, int nrSingole, int nrTriple)
            throws DoppiaNonDisponibileException, SingolaNonDisponibileException,
            MatrimonialeNonDisponibileException, TriplaNonDisponibileException, ClienteNotExistsException {

        //Il cliente esiste?
        if(!clienteRepository.existsByEmail(prenotazione.getCliente().getEmail())){
            throw new ClienteNotExistsException();
        }

        /**
         * Controllo le camere disponibili e ne scelgo quante richieste
        **/

        List<Camera> list = new LinkedList<>();

        if(nrDoppie>0){
            Pageable paging = PageRequest.of(0, nrDoppie, Sort.by("numero"));
            Page<Camera> doppie = cameraRepository.findCamereLibereByTipoCamera(prenotazione.getCheckin(),prenotazione.getCheckout(), TipoCamera.DOPPIA, paging);
            if( !doppie.hasContent() || doppie.getSize()<nrDoppie)throw new DoppiaNonDisponibileException();
            for(Camera c : doppie.getContent()){
                list.add(c);
            }
        }

        if(nrMatrimoniali>0){
            Pageable paging = PageRequest.of(0, nrMatrimoniali, Sort.by("numero"));
            Page<Camera> matrimoniali=cameraRepository.findCamereLibereByTipoCamera(prenotazione.getCheckin(),prenotazione.getCheckout(), TipoCamera.MATRIMONIALE, paging);
            if( !matrimoniali.hasContent() || matrimoniali.getSize()<nrMatrimoniali)throw new MatrimonialeNonDisponibileException();
            for(Camera c : matrimoniali){
                list.add(c);
            }
        }

        if(nrSingole>0){
            Pageable paging = PageRequest.of(0, nrSingole, Sort.by("numero"));
            Page<Camera> singole=cameraRepository.findCamereLibereByTipoCamera(prenotazione.getCheckin(),prenotazione.getCheckout(), TipoCamera.SINGOLA, paging);
            if( !singole.hasContent() || singole.getSize()<nrSingole)throw new SingolaNonDisponibileException();
            for(Camera c : singole){
                list.add(c);
            }
        }

        if(nrTriple>0){
            Pageable paging = PageRequest.of(0, nrTriple, Sort.by("numero"));
            Page<Camera> triple=cameraRepository.findCamereLibereByTipoCamera(prenotazione.getCheckin(),prenotazione.getCheckout(), TipoCamera.TRIPLA, paging);
            if( !triple.hasContent() || triple.getSize()<nrTriple)throw new TriplaNonDisponibileException();
            for(Camera c: triple){
                list.add(c);
            }
        }

        /**
         * Calcolo quanti giorni ci sono tra il check in e il check out
         */
        long in = prenotazione.getCheckin().getTime();//millisecondi
        long out = prenotazione.getCheckout().getTime();

        int notti = (int) ((out - in)/(1000*60*60*24));

        /**
         * Salvo la prenotazione ancora incompleta
         */
        prenotazione.setPrezzoTotale(0);

        Prenotazione result = prenotazioneRepository.save(prenotazione);

        /**
         * Prelevo i prezzi
         */
        Prezzi prezzi= prezziRepository.findAll().get(0);

        float prezzoTot=0;
        /**
         Primo switch:
         Modifico il prezzo in base al tipo di Servizio scelto.
         Per semplificare la procedura, lo calcolo in base al numero di camere prenotate.
         prezzoTot = prezzoTipoServizio * numero di camere prenotate * notti

         Switch nel for:
         aggiungo il prezzo di ogni singola camera al prezzo totale
         **/
        switch (result.getTipoServizio()){
            case BED: break;
            case BANDB: prezzoTot=((prezzi.getPrezzoBandB())*(list.size())*notti); break;
            case MEZZAPENSIONE: prezzoTot=((prezzi.getPrezzoMezzaPensione())*(list.size())*notti); break;
            case PENSIONECOMPLETA: prezzoTot=((prezzi.getPrezzoPensioneCompleta())*(list.size())*notti); break;
            default: throw new IllegalArgumentException();
        }

        /**
        Aggiungo le camere alla prenotazione
         */

        for(Camera cc : list){

            switch(cc.getTipoCamera()){
                case DOPPIA: prezzoTot+=((prezzi.getPrezzoDoppia())*notti); break;
                case MATRIMONIALE: prezzoTot+=((prezzi.getPrezzoMatrimoniale())*notti); break;
                case SINGOLA: prezzoTot+=((prezzi.getPrezzoSingola())*notti); break;
                case TRIPLA: prezzoTot+=((prezzi.getPrezzoTripla())*notti); break;
                default: throw new IllegalArgumentException();
            }

            cc.getPrenotazioni().add(result);
            result.getCamere().add(cc);
        }
        /**
         * Aggiorno la prenotazione
         */
        result.setPrezzoTotale(prezzoTot);

        boolean valido = false;
        int ris= Math.abs(result.getCliente().hashCode())+ Math.abs(result.hashCode());
        for(Camera c : result.getCamere()){
            ris+=((13*ris)+ Math.abs(c.hashCode()));
        }
        while(!valido) {
            ris += 17;
            valido = !prenotazioneRepository.existsByCodicePrenotazione(Math.abs(ris));
        }
        result.setCodicePrenotazione(Math.abs(ris));

        /**
         * Aggiorno lato cliente
         */
        Cliente cliente = clienteRepository.findById(result.getCliente().getId());
        cliente.getPrenotazioni().add(result);

        return result;
    }

    @Transactional(readOnly = true)
    public List<Prenotazione> getPrenotazioniByPeriod(Date inizio, Date fine, String email_cliente){
        return prenotazioneRepository.findByPeriodAndCliente(inizio,fine,email_cliente);
    }

    @Transactional(readOnly = true)
    public List<Prenotazione> getAllPrenotazioni(int pageNumber, int pageSize, String sort){
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sort));
        Page<Prenotazione> pagedResult = prenotazioneRepository.findAll(paging);
        if ( pagedResult.hasContent() ) {
            return pagedResult.getContent();
        }
        else {
            return new ArrayList<>();
        }
    }

    @Transactional(readOnly = true)
    public int conta(){
        return prenotazioneRepository.contaPrenotazioni();
    }

    @Transactional(readOnly = true)
    public Prenotazione cercaPerCodice(int codicePrenotazione){
        return prenotazioneRepository.findPrenotazioneByCodicePrenotazione(codicePrenotazione);
    }

    @Transactional(readOnly = false)
    public boolean rimuoviPrenotazione(int codice){
        Prenotazione p = prenotazioneRepository.findPrenotazioneByCodicePrenotazione(codice);
        if(p == null){return false;}
        //Se esiste la prenotazione, allora esisterà sicuramente il cliente
        Cliente c = clienteRepository.findById(p.getCliente().getId());

        List<Camera> camere = p.getCamere();
        prenotazioneRepository.deleteByCodicePrenotazione(codice);

        c.getPrenotazioni().remove(p);
        if(c.getPrenotazioni().size() == 0){
            clienteRepository.delete(c);
        }

        for(Camera camera : camere){
            Camera cc = cameraRepository.findById(camera.getId());
            cc.getPrenotazioni().remove(p);
        }
        return true;
    }
}
