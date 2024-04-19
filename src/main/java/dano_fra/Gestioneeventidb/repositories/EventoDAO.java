package dano_fra.Gestioneeventidb.repositories;

import dano_fra.Gestioneeventidb.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventoDAO extends JpaRepository<Evento, Long> {
    boolean existsByNome(String nome);

    Optional<Evento> findByNome(String nome);

}
