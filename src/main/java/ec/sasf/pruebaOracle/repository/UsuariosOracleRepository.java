package ec.sasf.pruebaOracle.repository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.sasf.pruebaOracle.entity.UsuariosOracle;

@Repository
public interface UsuariosOracleRepository extends JpaRepository<UsuariosOracle, Long>, UsuariosOracleRepositoryCustom  {

        @Modifying
        @Transactional
        @Query(value = "CALL insert_usuario_con_rol_y_edad(:nombre, :correo, :password, :edad, :rolId)", nativeQuery = true)
        void insertUsuarioConRolYEdad(
                        @Param("nombre") String nombre,
                        @Param("correo") String correo,
                        @Param("password") String password,
                        @Param("edad") Integer edad,
                        @Param("rolId") Long rolId);

        // --- LISTAR TODOS ---
        @Query(value = "SELECT * FROM TABLE(fn_listar_usuarios())", nativeQuery = true)
        List<Map<String, Object>> listarUsuarios();

        // --- BUSCAR POR FILTRO ---
        @Query(value = "SELECT * FROM TABLE(fn_buscar_usuarios_por_filtro(:filtro))", nativeQuery = true)
        List<Map<String, Object>> buscarUsuariosPorFiltro(@Param("filtro") String filtro);

        // --- OBTENER POR ID ---
        @Query(value = "SELECT * FROM TABLE(fn_obtener_usuario_por_id(:id_usuario))", nativeQuery = true)
        Map<String, Object> obtenerUsuarioPorId(@Param("id_usuario") Long id);

        // --- ACTUALIZAR ---
        @Modifying
        @Transactional
        @Query("UPDATE UsuariosOracle u SET u.nombre = :nombre, u.correo = :correo, u.password = :password WHERE u.id = :id")
        int actualizarUsuario(@Param("id") Long id,
                        @Param("nombre") String nombre,
                        @Param("correo") String correo,
                        @Param("password") String password);

        // --- ELIMINAR ---
        @Modifying
        @Transactional
        @Query(value = "CALL sp_eliminar_usuario(:id_usuario)", nativeQuery = true)
        void eliminarUsuario(
                        @Param("id_usuario") Long id);
}
