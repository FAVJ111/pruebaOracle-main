package ec.sasf.pruebaOracle.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "usuarios_oracle")
public class UsuariosOracle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_oracle_seq")
    @SequenceGenerator(name = "usuario_oracle_seq", sequenceName = "usuario_oracle_seq", allocationSize = 1)
    private Long id;

    private String nombre;

    @Column(unique = true, nullable = false)
    private String correo;

    @Column(nullable = false)
    private String password;

     private Integer edad;

    @ManyToMany(fetch = FetchType.LAZY)  // Cambio de EAGER a LAZY
    @JoinTable(
        name = "usuario_oracle_roles",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private List<RolesOracle> roles;
}

