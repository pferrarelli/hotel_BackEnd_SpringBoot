package piattaforme.hotel.altro.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private int id;

    @Version
    @JsonIgnore
    @Column(name="version", nullable = false)
    private long version;

    @Basic
    @Column(name="nome", length = 50, nullable = false)
    private String nome;

    @Basic
    @Column(name="cognome", length=50, nullable = false)
    private String cognome;

    @Basic
    @Column(name = "cellulare", length = 20, nullable = false)
    private String cellulare;

    @Basic
    @Column(name = "indirizzo", nullable=false, length = 40)
    private String indirizzo;

    @Basic
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Prenotazione> prenotazioni=new LinkedList<Prenotazione>();

    //--------------METODI--------------------//

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public void setPrenotazioni(List<Prenotazione> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return id == cliente.id &&
                Objects.equals(nome, cliente.nome) &&
                Objects.equals(cognome, cliente.cognome) &&
                Objects.equals(cellulare, cliente.cellulare) &&
                Objects.equals(indirizzo, cliente.indirizzo) &&
                Objects.equals(email, cliente.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cognome, cellulare, indirizzo, email);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", cellulare='" + cellulare + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
