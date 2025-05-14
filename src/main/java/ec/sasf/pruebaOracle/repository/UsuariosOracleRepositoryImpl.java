package ec.sasf.pruebaOracle.repository;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.ParameterMode;

import org.springframework.stereotype.Repository;

@Repository
public class UsuariosOracleRepositoryImpl implements UsuariosOracleRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Map<String, Object> obtenerInfoUsuarioProc(Long idUsuario) {
        StoredProcedureQuery sp = em
            .createStoredProcedureQuery("sp_info_usuario")
            .registerStoredProcedureParameter("p_id_usuario", Long.class,    ParameterMode.IN)
            .registerStoredProcedureParameter("p_nombre",     String.class,  ParameterMode.OUT)
            .registerStoredProcedureParameter("p_correo",     String.class,  ParameterMode.OUT)
            .registerStoredProcedureParameter("p_num_roles",  Integer.class, ParameterMode.OUT)
            .registerStoredProcedureParameter("p_edad",       Integer.class, ParameterMode.OUT)
            .setParameter("p_id_usuario", idUsuario);

        sp.execute();

        Map<String,Object> out = new HashMap<>();
        out.put("nombre",   sp.getOutputParameterValue("p_nombre"));
        out.put("correo",   sp.getOutputParameterValue("p_correo"));
        out.put("numRoles", sp.getOutputParameterValue("p_num_roles"));
        out.put("edad",     sp.getOutputParameterValue("p_edad"));
        return out;
    }
}
