package ec.sasf.pruebaOracle.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ec.sasf.pruebaOracle.dto.UsuarioActualizacionResponse;
import ec.sasf.pruebaOracle.dto.UsuarioDTO;
import ec.sasf.pruebaOracle.dto.UsuarioResponseDTO;
import ec.sasf.pruebaOracle.entity.UsuariosOracle;
import ec.sasf.pruebaOracle.repository.UsuariosOracleRepository;
import ec.sasf.pruebaOracle.repository.UsuariosOracleRepositoryCustom;
import jakarta.transaction.Transactional;

@Service
public class UsuariosOracleService {

    @Autowired
    private UsuariosOracleRepository usuariosOracleRepository;

    @Autowired
    private UsuariosOracleRepositoryCustom customRepo;

    @Autowired
    private ObjectMapper objectMapper;

    // Método para insertar un nuevo usuario
    public ResponseEntity<?> insertUsuario(UsuarioDTO dto) {
        try {
            Long nuevoId = usuariosOracleRepository.insertUsuarioConRolYEdadYRetornarId(
                    dto.getNombre(),
                    dto.getCorreo(),
                    dto.getPassword(),
                    dto.getEdad(),
                    dto.getRolId());

            UsuariosOracle nuevoUsuario = usuariosOracleRepository.findById(nuevoId).orElse(null);

            if (nuevoUsuario == null) {
                return ResponseEntity.status(500)
                        .body(Map.of("mensaje", "Usuario insertado pero no se pudo recuperar"));
            }

            UsuarioResponseDTO usuarioDTO = new UsuarioResponseDTO(nuevoUsuario);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "mensaje", "Usuario registrado con éxito",
                            "usuario", usuarioDTO));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al registrar el usuario: " + e.getRootCause().getMessage()));
        }
    }

    // Obtener todos los usuarios
    public ResponseEntity<?> obtenerTodos() {
        List<Map<String, Object>> usuarios = usuariosOracleRepository.listarUsuarios();
        if (usuarios.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("mensaje", "No se encontraron usuarios"));
        }
        return ResponseEntity.ok(Map.of("mensaje", "Usuarios encontrados", "usuarios", usuarios));
    }

    // Listar usuarios por filtro
    public ResponseEntity<?> listarPorFiltro(String filtro) {
        List<Map<String, Object>> usuarios = usuariosOracleRepository.buscarUsuariosPorFiltro(filtro);

        if (usuarios.isEmpty()) {
            return ResponseEntity.status(404).body(
                    Map.of("mensaje", "No se encontraron usuarios con ese filtro"));
        }

        return ResponseEntity.ok(
                Map.of("mensaje", "Usuarios encontrados", "usuarios", usuarios));
    }

    // Obtener usuario por ID
    public ResponseEntity<?> obtenerPorId(Long id) {
        Map<String, Object> usuario = usuariosOracleRepository.obtenerUsuarioPorId(id);

        if (usuario == null) {
            return ResponseEntity.status(404).body(
                    Map.of("mensaje", "Usuario no encontrado"));
        }

        return ResponseEntity.ok(
                Map.of("mensaje", "Usuario encontrado", "usuario", usuario));
    }

    // Actualizar usuario
    public ResponseEntity<?> actualizarUsuario(Long id, UsuarioDTO dto) {
        try {
            // Aseguramos que el ID se asigne al DTO antes de convertirlo a JSON
            dto.setIdUsuario(id);
            String jsonIn = objectMapper.writeValueAsString(dto);

            // Llamar al repositorio/procedimiento
            UsuarioActualizacionResponse resp = customRepo.actualizarUsuarioDesdeJson(jsonIn);

            // Obtener status directamente como int
            int code = resp.getStatus();

            HttpStatus status = switch (code) {
                case 200 -> HttpStatus.OK;
                case 404 -> HttpStatus.NOT_FOUND;
                case 400 -> HttpStatus.BAD_REQUEST;
                default -> HttpStatus.INTERNAL_SERVER_ERROR;
            };

            return ResponseEntity.status(status).body(resp);

        } catch (JsonProcessingException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error serializando JSON de entrada");
        }
    }

    // Eliminar usuario
    public ResponseEntity<?> eliminarUsuario(Long id) {
        try {
            usuariosOracleRepository.eliminarUsuario(id);
            return ResponseEntity.ok(
                    Map.of("mensaje", "Usuario eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of("mensaje", "Error al eliminar el usuario: " + e.getMessage()));
        }
    }

    public ResponseEntity<?> obtenerInfoUsuario(Long id) {
        Map<String, Object> info = usuariosOracleRepository.obtenerInfoUsuarioProc(id);
        if (info.get("nombre") == null) {
            return ResponseEntity.status(404)
                    .body(Map.of("mensaje", "Usuario no encontrado"));
        }
        return ResponseEntity.ok(Map.of("mensaje", "OK", "data", info));
    }
}
