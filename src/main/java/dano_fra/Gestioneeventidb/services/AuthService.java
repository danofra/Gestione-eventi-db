package dano_fra.Gestioneeventidb.services;

import dano_fra.Gestioneeventidb.entities.User;
import dano_fra.Gestioneeventidb.exceptions.UnauthorizedException;
import dano_fra.Gestioneeventidb.payloads.UserLoginDTO;
import dano_fra.Gestioneeventidb.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String authwnticateDipAndToken(UserLoginDTO payload) {
        User dipendente = this.userService.findByEmail(payload.email());
        if (bcrypt.matches(payload.password(), dipendente.getPassword())) {
            return jwtTools.createToken(dipendente);
        } else {
            throw new UnauthorizedException("Credenziali non valide! Effettua di nuovo il login!");
        }
    }
}
