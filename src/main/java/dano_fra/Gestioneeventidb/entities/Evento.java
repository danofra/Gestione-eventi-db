package dano_fra.Gestioneeventidb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "evento")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String descrizione;
    private String data;
    private String luogo;
    private int max_partecipanti;
    @ManyToMany(mappedBy = "eventi")
    @JsonIgnore
    private Set<User> users;


}
