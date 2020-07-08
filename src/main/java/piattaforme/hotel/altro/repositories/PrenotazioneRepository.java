package piattaforme.hotel.altro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import piattaforme.hotel.altro.entities.Prenotazione;

import javax.persistence.LockModeType;
import java.util.Date;
import java.util.List;


@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione,Integer> {

    @Query("select p from Prenotazione p where p.checkin > ?1 and p.checkout < ?2 and p.cliente.email = ?3")
    List<Prenotazione> findByPeriodAndCliente(Date inizio, Date fine, String email);

    Prenotazione findPrenotazioneByCodicePrenotazione(int codicePrenotazione);

    boolean existsByCodicePrenotazione(int codicePrenotazione);


    Integer deleteByCodicePrenotazione(int id_prenotazione);

    @Query("select count(p) from Prenotazione p")
    int contaPrenotazioni();
}
