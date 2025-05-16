package ec.sasf.pruebaOracle.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long idUsuario;
    private String nombre;
    private String correo;
    private String password;
    private Integer edad;
    private Long rolId;
}
