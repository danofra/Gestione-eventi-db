package dano_fra.Gestioneeventidb.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public record UserDTO(
        @NotEmpty(message = "Nome non può essere vuoto")
        @NotNull(message = "Nome non può essere nullo")
        String nome,
        @NotEmpty(message = "Cognome non può essere vuoto")
        @NotNull(message = "Cognome non può essere nullo")
        String cognome,
        @NotEmpty(message = "L'email non può essere vuota")
        @NotEmpty(message = "L'email è obbligatoria")
        @Email(message = "L'email inserita non è valida")
        String email,
        @NotEmpty(message = "La password non può essere vuota")
        String password,
        String ruoloUtente
) {
}
