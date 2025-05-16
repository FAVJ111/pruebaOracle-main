package ec.sasf.pruebaOracle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioActualizacionResponse {
    private String message;
    private int status;
    private UsuarioDTO dato;
}
