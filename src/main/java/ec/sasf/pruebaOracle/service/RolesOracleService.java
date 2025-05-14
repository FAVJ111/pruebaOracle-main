package ec.sasf.pruebaOracle.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.sasf.pruebaOracle.repository.RolesOracleRepository;
import jakarta.transaction.Transactional;

@Service
public class RolesOracleService {

    @Autowired
    private RolesOracleRepository rolesOracleRepository;

    // Método para insertar un nuevo rol
    @Transactional
    public Map<String, String> insertRol(String nombre) {
        try {
            rolesOracleRepository.insertRol(nombre); 
            return Map.of("mensaje", "Rol registrado con éxito");
        } catch (Exception e) {
            return Map.of("mensaje", "Error al registrar el rol: " + e.getMessage());
        }
    }
}

