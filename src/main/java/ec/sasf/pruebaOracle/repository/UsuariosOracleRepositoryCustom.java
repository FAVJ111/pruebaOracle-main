package ec.sasf.pruebaOracle.repository;

import java.util.Map;

import ec.sasf.pruebaOracle.dto.UsuarioActualizacionResponse;

public interface UsuariosOracleRepositoryCustom {
    Map<String, Object> obtenerInfoUsuarioProc(Long idUsuario);

    Long insertUsuarioConRolYEdadYRetornarId(String nombre, String correo, String password, Integer edad, Long rolId);

    UsuarioActualizacionResponse actualizarUsuarioDesdeJson(String usuarioJson);
}
