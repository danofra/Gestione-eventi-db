package dano_fra.Gestioneeventidb.payloads;

import java.time.LocalDateTime;

public record ErrorsResponseDTO(
        String message,
        LocalDateTime timestamp
) {
}
