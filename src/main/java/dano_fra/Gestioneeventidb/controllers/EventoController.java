package dano_fra.Gestioneeventidb.controllers;

import dano_fra.Gestioneeventidb.entities.Evento;
import dano_fra.Gestioneeventidb.exceptions.BadRequestException;
import dano_fra.Gestioneeventidb.exceptions.CorrectDeleteEvento;
import dano_fra.Gestioneeventidb.payloads.EventoDTO;
import dano_fra.Gestioneeventidb.services.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/evento")
public class EventoController {
    @Autowired
    private EventoService eventoService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public EventoDTO evento(@RequestBody @Validated EventoDTO body, BindingResult resul) {
        if (resul.hasErrors()) {
            throw new BadRequestException(resul.getAllErrors());
        }
        Evento evento = new Evento();
        evento.setNome(body.nome());
        evento.setDescrizione(body.descrizione());
        evento.setData(body.data());
        evento.setLuogo(body.luogo());
        evento.setMax_partecipanti(body.max_partecipanti());
        eventoService.save(body);
        return body;
    }

    @GetMapping("")
    public Page<Evento> getAllEvento(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "id") String sortBy) {
        return this.eventoService.getEvento(page, size, sortBy);
    }

    @GetMapping("/{eventoId}")
    @ResponseStatus(HttpStatus.OK)
    public Evento findById(@PathVariable long eventoId) {
        return eventoService.findById(eventoId);
    }

    @PutMapping("/{eventoId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Evento findAndUpdate(@PathVariable int eventoId, @RequestBody EventoDTO body) {
        return eventoService.findByIdAndUpdate(eventoId, body);
    }

    @DeleteMapping("/{eventoId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findAndDelete(@PathVariable long eventoId) throws CorrectDeleteEvento {
        eventoService.findByIdAndDelete(eventoId);
        throw new CorrectDeleteEvento();
    }


}
