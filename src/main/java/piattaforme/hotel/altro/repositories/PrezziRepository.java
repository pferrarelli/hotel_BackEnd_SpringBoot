package piattaforme.hotel.altro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import piattaforme.hotel.altro.entities.Prezzi;

@Repository
public interface PrezziRepository extends JpaRepository<Prezzi,Integer> {
}
