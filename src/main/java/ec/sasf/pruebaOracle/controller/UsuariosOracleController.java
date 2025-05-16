package ec.sasf.pruebaOracle.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ec.sasf.pruebaOracle.dto.UsuarioDTO;
import ec.sasf.pruebaOracle.service.UsuariosOracleService;

@RestController
@RequestMapping("/usuarios")
public class UsuariosOracleController {

    @Autowired
    private UsuariosOracleService usuariosOracleService;

    // Endpoint para insertar un nuevo usuario
    @PostMapping
    public ResponseEntity<?> insertUsuario(@RequestBody UsuarioDTO dto) {
        return usuariosOracleService.insertUsuario(dto);
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
    public ResponseEntity<?> actualizarUsuario(
        @PathVariable Long id,
        @RequestBody UsuarioDTO usuarioDTO
    ) {
        return usuariosOracleService.actualizarUsuario(id, usuarioDTO);
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
