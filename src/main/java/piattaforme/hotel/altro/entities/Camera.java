package piattaforme.hotel.altro.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import piattaforme.hotel.altro.support.enums.TipoCamera;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="camera")
public class Camera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private int id;

    @Version
    @JsonIgnore
    @Column(name="version", nullable = false)
    private long version;

    @Column(name="numero",nullable = false, unique = true)
    private int numero;

    @Column(name = "tipo", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private TipoCamera tipoCamera;

    @JsonIgnore
    @ManyToMany(mappedBy = "camere")
    private List<Prenotazione> prenotazioni= new LinkedList<Prenotazione>();

    //--------------METODI--------------------//

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public TipoCamera getTipoCamera() {
        return tipoCamera;
    }

    public void setTipoCamera(TipoCamera tipoCamera) {
        this.tipoCamera = tipoCamera;
    }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public void setPrenotazioni(List<Prenotazione> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }


}
