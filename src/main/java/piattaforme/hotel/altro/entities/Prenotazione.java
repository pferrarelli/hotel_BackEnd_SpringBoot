package piattaforme.hotel.altro.entities;

import piattaforme.hotel.altro.support.enums.TipoServizio;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="prenotazione")
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private int id;

    @Column(name="codice", nullable = false, unique = true)
    private int codicePrenotazione;

    @Temporal(TemporalType.DATE)
    @Column(name="check_in", nullable = false)
    private Date checkin;

    @Temporal(TemporalType.DATE)
    @Column(name="check_out", nullable = false)
    private Date checkout;

    @Column(name = "prezzo_totale", nullable = false)
    private float prezzoTotale;

    @Column(name="tipo_servizio", nullable=false)
    @Enumerated(EnumType.ORDINAL)
    private TipoServizio tipoServizio;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;


    @ManyToMany
    @JoinTable(name="prenotazione_camera",
            joinColumns = {@JoinColumn(name= "id_prenotazione", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name="id_camera",referencedColumnName = "id")})
    List<Camera> camere = new LinkedList<Camera>();

    //--------------METODI--------------------//

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCheckin() {
        return checkin;
    }

    public void setCheckin(Date checkin) {
        this.checkin = checkin;
    }

    public Date getCheckout() {
        return checkout;
    }

    public void setCheckout(Date checkout) {
        this.checkout = checkout;
    }

    public float getPrezzoTotale() {
        return prezzoTotale;
    }

    public void setPrezzoTotale(float prezzoTotale) {
        this.prezzoTotale = prezzoTotale;
    }

    public TipoServizio getTipoServizio() {
        return tipoServizio;
    }

    public void setTipoServizio(TipoServizio tipoServizio) {
        this.tipoServizio = tipoServizio;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Camera> getCamere() {
        return camere;
    }

    public void setCamere(List<Camera> camere) {
        this.camere = camere;
    }

    public int getCodicePrenotazione() {
        return codicePrenotazione;
    }

    public void setCodicePrenotazione(int codicePrenotazione) {
        this.codicePrenotazione = codicePrenotazione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prenotazione that = (Prenotazione) o;
        return id == that.id &&
                codicePrenotazione == that.codicePrenotazione &&
                Float.compare(that.prezzoTotale, prezzoTotale) == 0 &&
                Objects.equals(checkin, that.checkin) &&
                Objects.equals(checkout, that.checkout) &&
                tipoServizio == that.tipoServizio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, checkin, checkout, prezzoTotale, tipoServizio);
    }
}
