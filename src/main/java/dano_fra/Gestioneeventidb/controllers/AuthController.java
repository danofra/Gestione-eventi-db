package dano_fra.Gestioneeventidb.controllers;

import dano_fra.Gestioneeventidb.exceptions.BadRequestException;
import dano_fra.Gestioneeventidb.payloads.UserDTO;
import dano_fra.Gestioneeventidb.payloads.UserLoginDTO;
import dano_fra.Gestioneeventidb.payloads.UserLoginResponseDTO;
import dano_fra.Gestioneeventidb.payloads.UserResponseDTO;
import dano_fra.Gestioneeventidb.services.AuthService;
import dano_fra.Gestioneeventidb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;


    @RequestMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO payload) {
        return new UserLoginResponseDTO(this.authService.authenticationDipAndToken(payload));
    }

    @RequestMapping("/register")
    public UserResponseDTO saveUser(@RequestBody @Validated UserDTO payload, BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException(result.getAllErrors());
        }
        return new UserResponseDTO(this.userService.save(payload).email());
    }
}
