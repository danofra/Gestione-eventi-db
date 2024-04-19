package dano_fra.Gestioneeventidb.services;

import dano_fra.Gestioneeventidb.entities.Evento;
import dano_fra.Gestioneeventidb.exceptions.BadRequestException;
import dano_fra.Gestioneeventidb.payloads.EventoDTO;
import dano_fra.Gestioneeventidb.repositories.EventoDAO;
import dano_fra.Gestioneeventidb.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EventoService {
    @Autowired
    private EventoDAO eventoDAO;
    @Autowired
    private UserDAO userDAO;
 

    public Evento save(EventoDTO newEvento) {
        Evento evento = new Evento();
        evento.setNome(newEvento.nome());
        evento.setLuogo(newEvento.luogo());
        evento.setDescrizione(newEvento.descrizione());
        evento.setData(newEvento.data());
        evento.setMax_partecipanti(newEvento.max_partecipanti());
        return eventoDAO.save(evento);
    }

    public Page<Evento> getEvento(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.eventoDAO.findAll(pageable);
    }

    public Evento findById(long eventoId) {
        return this.eventoDAO.findById(eventoId).orElseThrow(() -> new BadRequestException("Evento non trovato"));
    }

    public void findByIdAndDelete(long eventoId) {
        Evento evento = this.findById(eventoId);
        this.eventoDAO.delete(evento);
    }

    public Evento findByIdAndUpdate(long eventoId, EventoDTO newEvento) {
        Evento evento = this.findById(eventoId);
        evento.setNome(newEvento.nome());
        evento.setLuogo(newEvento.luogo());
        evento.setDescrizione(newEvento.descrizione());
        evento.setData(newEvento.data());
        evento.setMax_partecipanti(newEvento.max_partecipanti());
        return this.eventoDAO.save(evento);
    }

}
