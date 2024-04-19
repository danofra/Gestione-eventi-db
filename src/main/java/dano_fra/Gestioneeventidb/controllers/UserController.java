package dano_fra.Gestioneeventidb.controllers;

import dano_fra.Gestioneeventidb.entities.User;
import dano_fra.Gestioneeventidb.enums.ruolo;
import dano_fra.Gestioneeventidb.exceptions.BadRequestException;
import dano_fra.Gestioneeventidb.payloads.UserDTO;
import dano_fra.Gestioneeventidb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserDTO user(@RequestBody @Validated UserDTO body, BindingResult resul) {
        if (resul.hasErrors()) {
            throw new BadRequestException(resul.getAllErrors());
        }
        User user = new User();
        user.setNome(body.nome());
        user.setCognome(body.cognome());
        user.setEmail(body.email());
        user.setPassword(body.password());
        user.setRuoloUtente(ruolo.valueOf(body.ruoloUtente()));
        userService.save(body);
        return body;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy) {
        return this.userService.getUser(page, size, sortBy);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public User findById(@PathVariable long userId) {
        return this.userService.findById(userId);
    }

    @PutMapping("/{dipendenteId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public User findAndUpdate(@PathVariable int dipendenteId, @RequestBody UserDTO body) {
        return userService.findByIdAndUpdate(dipendenteId, body);
    }

    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User currentAuthenticatedUser) {
        return currentAuthenticatedUser;
    }

    @PutMapping("/me")
    public User updateProfile(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody UserDTO updatedUser) {
        return this.userService.findByIdAndUpdate(currentAuthenticatedUser.getId(), updatedUser);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal User currentAuthenticatedUser) {
        this.userService.findByIdAndDelete(currentAuthenticatedUser.getId());
    }

}
