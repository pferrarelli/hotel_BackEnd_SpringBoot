package piattaforme.hotel.altro.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import piattaforme.hotel.altro.entities.Camera;
import piattaforme.hotel.altro.repositories.CameraRepository;
import piattaforme.hotel.altro.support.enums.TipoCamera;
import piattaforme.hotel.altro.support.exceptions.CameraAlredyExistsException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CameraService {

    @Autowired
    private CameraRepository cameraRepository;

    @Transactional(readOnly = true)
    public List<Camera> getAllCamere(){
        return cameraRepository.findAll();
    }

    @Transactional(readOnly = false)
    public void addCamera(Camera camera) throws CameraAlredyExistsException {
        if(cameraRepository.existsByNumero(camera.getNumero()) ){
            throw new CameraAlredyExistsException();
        }
        cameraRepository.save(camera);
    }

    @Transactional(readOnly = true)
    public int getQuantitaByTipoAndPeriodo(Date in, Date fin, TipoCamera tipoCamera){
        return cameraRepository.findNumeroCamereLibereByTipoCamera(in,fin,tipoCamera);
    }

    @Transactional(readOnly = true)
    public int conta(){
        return cameraRepository.contaCamere();
    }

    @Transactional(readOnly = true)
    public List<Camera> getAllCamere(int pageNumber, int pageSize, String sort){
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(sort));
        Page<Camera> pagedResult = cameraRepository.findAll(paging);
        if ( pagedResult.hasContent() ) {
            return pagedResult.getContent();
        }
        else {
            return new ArrayList<>();
    }
}

    @Transactional(readOnly = false)
    public boolean rimuoviCamera(int id){
        Camera c = cameraRepository.findById(id);
        if(c == null){
            return false;
        }
        if(c.getPrenotazioni().size() > 0){
            return false;
        }
        cameraRepository.delete(c);
        return true;
    }
}
