package ec.sasf.pruebaOracle.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "roles_oracle")
public class RolesOracle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rol_oracle_seq")
    @SequenceGenerator(name = "rol_oracle_seq", sequenceName = "rol_oracle_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    @ManyToMany(mappedBy = "roles")
    private List<UsuariosOracle> usuarios;

    public RolesOracle() {}

    public RolesOracle(Long id, String nombre, List<UsuariosOracle> usuarios) {
        this.id = id;
        this.nombre = nombre;
        this.usuarios = usuarios;
    }

}

