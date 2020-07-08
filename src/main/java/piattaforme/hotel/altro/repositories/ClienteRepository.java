package piattaforme.hotel.altro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import piattaforme.hotel.altro.entities.Cliente;

import javax.persistence.LockModeType;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Integer> {

    @Lock(LockModeType.OPTIMISTIC)
    Cliente findById(int id);

    boolean existsByEmail(String email);
    Cliente findByEmail(String email);
}
