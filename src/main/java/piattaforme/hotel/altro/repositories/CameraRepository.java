package piattaforme.hotel.altro.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import piattaforme.hotel.altro.entities.Camera;
import piattaforme.hotel.altro.support.enums.TipoCamera;

import javax.persistence.LockModeType;
import java.util.Date;

@Repository
public interface CameraRepository extends JpaRepository<Camera,Integer> {

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select c " +
            "from Camera c " +
            "where c.tipoCamera=?3 and c not in(" +
                                                "select c1 " +
                                                "from Camera c1 left join c1.prenotazioni p " +
                                                "where p.checkin<?2 and p.checkout>?1 )")
    Page<Camera> findCamereLibereByTipoCamera(Date in, Date fin, TipoCamera tipoCamera, Pageable pageable);

    @Lock(LockModeType.OPTIMISTIC)
    Camera findById(int id);

    @Query("select count(c) " +
            "from Camera c " +
            "where c.tipoCamera=?3 and c not in(" +
                                                "select c1 " +
                                                "from Camera c1 left join c1.prenotazioni p " +
                                                "where p.checkin<?2 and p.checkout>?1 )")
    int findNumeroCamereLibereByTipoCamera(Date in, Date fin, TipoCamera tipoCamera);

    boolean existsByNumero(int numero);

    @Query("select count(c) from Camera c")
    int contaCamere();
}
