package ec.sasf.pruebaOracle.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ec.sasf.pruebaOracle.entity.UsuariosOracle;
import ec.sasf.pruebaOracle.repository.UsuariosOracleRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuariosOracleService {

    @Autowired
    private UsuariosOracleRepository usuariosOracleRepository;

    // Método para insertar un nuevo usuario
    // @Transactional
    public Map<String, String> insertUsuario(
            String nombre,
            String correo,
            String password,
            Integer edad,
            Long rolId) {
        try {
            usuariosOracleRepository.insertUsuarioConRolYEdad(nombre, correo, password, edad, rolId);
            return Map.of("mensaje", "Usuario registrado con éxito");
        } catch (DataAccessException e) {
            return Map.of("mensaje", "Error al registrar el usuario: " + e.getRootCause().getMessage());
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
    public ResponseEntity<?> actualizarUsuario(Long id, String nombre, String correo, String password) {
        int filasActualizadas = usuariosOracleRepository.actualizarUsuario(id, nombre, correo, password);

        if (filasActualizadas == 0) {
            return ResponseEntity.status(404).body(
                    Map.of("mensaje", "Error al actualizar el usuario"));
        }

        UsuariosOracle usuario = usuariosOracleRepository.findById(id).orElse(null);

        return ResponseEntity.ok(
                Map.of("mensaje", "Usuario actualizado correctamente", "usuario", usuario));
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
