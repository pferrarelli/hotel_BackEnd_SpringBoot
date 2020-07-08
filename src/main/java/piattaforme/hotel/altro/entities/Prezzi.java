package piattaforme.hotel.altro.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="prezzi")
public class Prezzi {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="prezzoDoppia", nullable = false)
    private float prezzoDoppia;

    @Column(name="prezzoMatrimoniale", nullable = false)
    private float prezzoMatrimoniale;

    @Column(name="prezzoSingola", nullable = false)
    private float prezzoSingola;

    @Column(name="prezzoTripla", nullable = false)
    private float prezzoTripla;

    //Prezzo TipoServizio BED non aggiunto perché il prezzo è nullo

    @Column(name="prezzoBandB", nullable = false)
    private float prezzoBandB;

    @Column(name="prezzoMezzaPensione", nullable = false)
    private float prezzoMezzaPensione;

    @Column(name="prezzoPensioneCompleta", nullable = false)
    private float prezzoPensioneCompleta;

    //--------------METODI--------------------//

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrezzoDoppia() {
        return prezzoDoppia;
    }

    public void setPrezzoDoppia(float prezzoDoppia) {
        this.prezzoDoppia = prezzoDoppia;
    }

    public float getPrezzoMatrimoniale() {
        return prezzoMatrimoniale;
    }

    public void setPrezzoMatrimoniale(float prezzoMatrimoniale) {
        this.prezzoMatrimoniale = prezzoMatrimoniale;
    }

    public float getPrezzoSingola() {
        return prezzoSingola;
    }

    public void setPrezzoSingola(float prezzoSingola) {
        this.prezzoSingola = prezzoSingola;
    }

    public float getPrezzoTripla() {
        return prezzoTripla;
    }

    public void setPrezzoTripla(float prezzoTripla) {
        this.prezzoTripla = prezzoTripla;
    }

    public float getPrezzoBandB() {
        return prezzoBandB;
    }

    public void setPrezzoBandB(float prezzoBandB) {
        this.prezzoBandB = prezzoBandB;
    }

    public float getPrezzoMezzaPensione() {
        return prezzoMezzaPensione;
    }

    public void setPrezzoMezzaPensione(float prezzoMezzaPensione) {
        this.prezzoMezzaPensione = prezzoMezzaPensione;
    }

    public float getPrezzoPensioneCompleta() {
        return prezzoPensioneCompleta;
    }

    public void setPrezzoPensioneCompleta(float prezzoPensioneCompleta) {
        this.prezzoPensioneCompleta = prezzoPensioneCompleta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prezzi prezzi = (Prezzi) o;
        return id == prezzi.id &&
                Float.compare(prezzi.prezzoDoppia, prezzoDoppia) == 0 &&
                Float.compare(prezzi.prezzoMatrimoniale, prezzoMatrimoniale) == 0 &&
                Float.compare(prezzi.prezzoSingola, prezzoSingola) == 0 &&
                Float.compare(prezzi.prezzoTripla, prezzoTripla) == 0 &&
                Float.compare(prezzi.prezzoBandB, prezzoBandB) == 0 &&
                Float.compare(prezzi.prezzoMezzaPensione, prezzoMezzaPensione) == 0 &&
                Float.compare(prezzi.prezzoPensioneCompleta, prezzoPensioneCompleta) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, prezzoDoppia, prezzoMatrimoniale, prezzoSingola, prezzoTripla, prezzoBandB, prezzoMezzaPensione, prezzoPensioneCompleta);
    }
}