package ec.sasf.pruebaOracle.repository;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.ParameterMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import ec.sasf.pruebaOracle.dto.UsuarioActualizacionResponse;
import ec.sasf.pruebaOracle.dto.UsuarioDTO;

@Repository
@Primary
public class UsuariosOracleRepositoryImpl implements UsuariosOracleRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public UsuarioActualizacionResponse actualizarUsuarioDesdeJson(String usuarioJson) {
        StoredProcedureQuery q = em
                .createStoredProcedureQuery("fn_actualizar_usuario_json2")
                .registerStoredProcedureParameter("p_usuario_json", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_message", String.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("p_status", String.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("p_usuario_out", String.class, ParameterMode.OUT); // CLOB
                                                                                                     // interpretado
                                                                                                     // como String

        q.setParameter("p_usuario_json", usuarioJson);
        q.execute();

        String message = (String) q.getOutputParameterValue("p_message");
        String statusStr = (String) q.getOutputParameterValue("p_status");
        String usuarioOutStr = (String) q.getOutputParameterValue("p_usuario_out");

        int status;
        try {
            status = Integer.parseInt(statusStr);
        } catch (NumberFormatException e) {
            status = 500; // Por si llega algo inesperado
        }

        UsuarioDTO dato = null;
        try {
            if (usuarioOutStr != null && !usuarioOutStr.trim().isEmpty()) {
                dato = objectMapper.readValue(usuarioOutStr, UsuarioDTO.class);
            }
        } catch (Exception e) {
            // Si el JSON es inválido, lo registras o devuelves null
            dato = null;
        }

        return new UsuarioActualizacionResponse(message, status, dato);
    }

    @Override
    public Long insertUsuarioConRolYEdadYRetornarId(String nombre, String correo, String password, Integer edad,
            Long rolId) {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("insert_usuario_con_rol_y_edad")
                .registerStoredProcedureParameter("p_nombre", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_correo", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_password", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_edad", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_rol_id", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_id_out", Long.class, ParameterMode.OUT);

        query.setParameter("p_nombre", nombre);
        query.setParameter("p_correo", correo);
        query.setParameter("p_password", password);
        query.setParameter("p_edad", edad);
        query.setParameter("p_rol_id", rolId);

        query.execute();

        return (Long) query.getOutputParameterValue("p_id_out");
    }

    @Override
    public Map<String, Object> obtenerInfoUsuarioProc(Long idUsuario) {
        StoredProcedureQuery sp = em
                .createStoredProcedureQuery("sp_info_usuario")
                .registerStoredProcedureParameter("p_id_usuario", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_nombre", String.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("p_correo", String.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("p_num_roles", Integer.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("p_edad", Integer.class, ParameterMode.OUT)
                .setParameter("p_id_usuario", idUsuario);

        sp.execute();

        Map<String, Object> out = new HashMap<>();
        out.put("nombre", sp.getOutputParameterValue("p_nombre"));
        out.put("correo", sp.getOutputParameterValue("p_correo"));
        out.put("numRoles", sp.getOutputParameterValue("p_num_roles"));
        out.put("edad", sp.getOutputParameterValue("p_edad"));
        return out;
    }
}
