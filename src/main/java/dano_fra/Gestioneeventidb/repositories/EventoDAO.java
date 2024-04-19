package dano_fra.Gestioneeventidb.repositories;

import dano_fra.Gestioneeventidb.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoDAO extends JpaRepository<Evento, Long> {
    
}
