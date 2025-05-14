package ec.sasf.pruebaOracle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.sasf.pruebaOracle.entity.RolesOracle;
import jakarta.transaction.Transactional;

@Repository
public interface RolesOracleRepository extends JpaRepository<RolesOracle, Long> {

    @Transactional
    @Procedure(procedureName = "insert_rol")
    void insertRol(@Param("p_nombre") String nombre);
}


