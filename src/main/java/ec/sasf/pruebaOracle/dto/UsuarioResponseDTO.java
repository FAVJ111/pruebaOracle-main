package ec.sasf.pruebaOracle.dto;

import ec.sasf.pruebaOracle.entity.UsuariosOracle;
import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String correo;
    private Integer edad;

    public UsuarioResponseDTO(UsuariosOracle usuario) {
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();
        this.correo = usuario.getCorreo();
        this.edad = usuario.getEdad();
    }
}
