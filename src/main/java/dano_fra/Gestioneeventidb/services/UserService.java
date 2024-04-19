package dano_fra.Gestioneeventidb.services;

import dano_fra.Gestioneeventidb.entities.Evento;
import dano_fra.Gestioneeventidb.entities.User;
import dano_fra.Gestioneeventidb.enums.ruolo;
import dano_fra.Gestioneeventidb.exceptions.BadRequestException;
import dano_fra.Gestioneeventidb.payloads.UserDTO;
import dano_fra.Gestioneeventidb.payloads.UserResponseDTO;
import dano_fra.Gestioneeventidb.repositories.EventoDAO;
import dano_fra.Gestioneeventidb.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private EventoService eventoService;
    @Autowired
    private EventoDAO eventoDAO;

    public UserResponseDTO save(UserDTO newUser) throws BadRequestException {
        Optional<User> existingUser = this.userDAO.findByEmail(newUser.email());
        if (existingUser.isPresent()) {
            throw new BadRequestException("L'email " + newUser.email() + " è già in uso!");
        }
        User user = new User();
        user.setNome(newUser.nome());
        user.setCognome(newUser.cognome());
        user.setEmail(newUser.email());
        user.setPassword(bcrypt.encode(newUser.password()));
        user.setRuoloUtente(ruolo.USER);
        this.userDAO.save(user);
        return new UserResponseDTO(user.getEmail());
    }

    public Page<User> getUser(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.userDAO.findAll(pageable);
    }

    public User findById(long userId) {
        return this.userDAO.findById(userId).orElseThrow(
                () -> new BadRequestException("Utente non trovato")
        );
    }

    public void findByIdAndDelete(long userId) {
        User user = this.findById(userId);
        this.userDAO.delete(user);
    }

    public User findByEmail(String email) {
        return userDAO.findByEmail(email).orElseThrow(() -> new BadRequestException("Utente con email " + email + " non trovato!"));
    }

    public User findByIdAndUpdate(long id, UserDTO newUser) {
        User user = this.findById(id);
        user.setNome(newUser.nome());
        user.setCognome(newUser.cognome());
        user.setEmail(newUser.email());
        user.setPassword(bcrypt.encode(newUser.password()));
        return this.userDAO.save(user);
    }

    public User findByIdAndAddUtente(long userId, long eventoId) {
        User user = this.findById(userId);
        Evento evento = eventoService.findById(userId);
        if (Objects.isNull(user)) {
            throw new BadRequestException(String.valueOf(eventoId));
        }
        if (Objects.isNull(evento)) {
            throw new BadRequestException(String.valueOf(eventoId));
        }
        if (evento.getUsers().contains(user)) {
            throw new BadRequestException("Utente già presente nel evento");
        }
        if (evento.getUsers().size() >= evento.getMax_partecipanti()) {
            throw new BadRequestException("Evento pieno");
        }
        evento.getUsers().add(user);
        user.getEventi().add(evento);

        eventoDAO.save(evento);
        return userDAO.save(user);
    }
}
