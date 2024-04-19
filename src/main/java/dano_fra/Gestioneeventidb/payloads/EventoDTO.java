package dano_fra.Gestioneeventidb.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record EventoDTO(
        @NotNull(message = "Nome non può essere nullo")
        @NotEmpty(message = "Nome non può essere vuoto")
        String nome,
        @NotNull(message = "Descrizione non può essere nullo")
        @NotEmpty(message = "Descrizione non può essere vuota")
        String descrizione,
        @NotNull(message = "Data non può essere nullo")
        @NotEmpty(message = "Data non può essere vuota")
        String data,
        @NotNull(message = "Luogo non può essere nullo")
        @NotEmpty(message = "Luogo non può essere vuoto")
        String luogo,
        @NotNull(message = "Max partecipanti non può essere nullo")
        @NotEmpty(message = "Max partecipanti non può essere vuoto")
        int max_partecipanti
) {
}
