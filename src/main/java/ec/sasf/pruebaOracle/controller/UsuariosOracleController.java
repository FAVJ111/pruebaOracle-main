package ec.sasf.pruebaOracle.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ec.sasf.pruebaOracle.service.UsuariosOracleService;

@RestController
@RequestMapping("/usuarios")
public class UsuariosOracleController {

    @Autowired
    private UsuariosOracleService usuariosOracleService;

    // Endpoint para insertar un nuevo usuario
    @PostMapping
    public ResponseEntity<Map<String, String>> insertUsuario(
            @RequestParam String nombre,
            @RequestParam String correo,
            @RequestParam String password,
            @RequestParam Integer edad,
            @RequestParam Long rolId
    ) {
        Map<String, String> response = usuariosOracleService.insertUsuario(nombre, correo, password, edad, rolId);

        if (response.get("mensaje").contains("Error")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        return usuariosOracleService.obtenerTodos();
    }

    // Buscar usuarios por filtro
    @GetMapping("/filtro")
    public ResponseEntity<?> listarPorFiltro(@RequestParam String filtro) {
        return usuariosOracleService.listarPorFiltro(filtro);
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        return usuariosOracleService.obtenerPorId(id);
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id,
            @RequestParam String nombre,
            @RequestParam String correo,
            @RequestParam String password) {
        return usuariosOracleService.actualizarUsuario(id, nombre, correo, password);
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        return usuariosOracleService.eliminarUsuario(id);
    }

        @GetMapping("/{id}/info")
    public ResponseEntity<?> infoUsuario(@PathVariable Long id) {
        return usuariosOracleService.obtenerInfoUsuario(id);
    }
}
