package ec.sasf.pruebaOracle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ec.sasf.pruebaOracle.service.RolesOracleService;

@RestController
@RequestMapping("/roles")
public class RolesOracleController {

    @Autowired
    private RolesOracleService rolesOracleService;

    // Endpoint para insertar un nuevo rol
    @PostMapping
    public ResponseEntity<Map<String, String>> insertRol(@RequestParam String nombre) {
        Map<String, String> response = rolesOracleService.insertRol(nombre);

        if (response.get("mensaje").contains("Error")) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED); 
    }
}
