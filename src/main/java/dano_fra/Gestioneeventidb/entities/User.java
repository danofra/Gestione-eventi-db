package dano_fra.Gestioneeventidb.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dano_fra.Gestioneeventidb.enums.ruolo;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"password", "ruoloUtente"})
@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private ruolo ruoloUtente;
    @ManyToMany
    @JoinTable(
            name = "evento_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "evento_id")
    )
    private Set<Evento> eventi;

    public User(String nome, String cognome, String email, String password, ruolo ruoloUtente) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.ruoloUtente = ruoloUtente;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.ruoloUtente.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //fatto da intellij
    @Override
    public String getUsername() {
        return "";
    }
}
