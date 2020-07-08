package piattaforme.hotel.altro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import piattaforme.hotel.altro.entities.Prezzi;
import piattaforme.hotel.altro.repositories.PrezziRepository;
import piattaforme.hotel.altro.support.exceptions.PrezziErratiException;
import piattaforme.hotel.altro.support.exceptions.PrezziNonPresentiException;

import java.util.List;

@Service
public class PrezziService {

    @Autowired
    private PrezziRepository prezziRepository;

    @Transactional(readOnly = false)
    public void setPrezzi(Prezzi nuoviPrezzi) throws PrezziErratiException {

        List<Prezzi> old= prezziRepository.findAll();
        for(Prezzi p : old){
            prezziRepository.delete(p);
        }
        if(nuoviPrezzi.getPrezzoBandB()<=0 ||
                nuoviPrezzi.getPrezzoMezzaPensione()<=0 ||
                nuoviPrezzi.getPrezzoPensioneCompleta()<=0 ||
                nuoviPrezzi.getPrezzoSingola()<=0 ||
                nuoviPrezzi.getPrezzoDoppia()<=0 ||
                nuoviPrezzi.getPrezzoMatrimoniale()<=0 ||
                nuoviPrezzi.getPrezzoTripla()<=0){
            throw new PrezziErratiException();
        }
        prezziRepository.save(nuoviPrezzi);
    }

    @Transactional(readOnly = true)
    public Prezzi getPrezzi() throws PrezziNonPresentiException {
        List<Prezzi> p = prezziRepository.findAll();
        if(p.isEmpty()){
            throw new PrezziNonPresentiException();
        }
        return p.get(0);
    }

}
