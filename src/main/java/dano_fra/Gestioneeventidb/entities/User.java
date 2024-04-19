package dano_fra.Gestioneeventidb.entities;

import dano_fra.Gestioneeventidb.enums.ruolo;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private ruolo ruoloUtente;
    @ManyToMany(mappedBy = "partecipanti")
    private List<Evento> eventiPartecipati;

    public User(String nome, String cognome, String email, String password, ruolo ruoloUtente) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.ruoloUtente = ruoloUtente;
    }
}
