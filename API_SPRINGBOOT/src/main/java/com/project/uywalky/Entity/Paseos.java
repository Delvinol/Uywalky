package com.project.uywalky.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.lang.model.element.Name;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Paseos")
public class Paseos {

    @Id
    @GeneratedValue
    private Integer idPaseo;

    @OneToOne
    @JoinColumn(name = "id_reserva")
    private Reservas reservas;

    @OneToOne
    @JoinColumn(name = "id_mascota")
    private Mascotas mascotas;

    @ManyToOne
    @JoinColumn(name = "id_paseador")
    private Paseadores paseadores;

    @ManyToOne
    @JoinColumn(name = "id_propietario")
    private Propietarios propietarios;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "calificacion")
    private String calificacion;

    @Column(name = "costo")
    private Integer costo;

    @Column(name = "fecha_paseo")
    private String fecha_paseo;

    @Column(name = "hora_paseo")
    private String hora_paseo;

    @Column(name = "duracion_real")
    private String duracion_real;

    @Column(name = "lugar", nullable = false, length = 25)
    private String lugar;

    @Column(name = "estado", columnDefinition = "TINYINT DEFAULT 1")//Despues agregar opcion para que no sea nulo
    private Integer estado;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updated_at",  columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @JsonIgnore
    @OneToMany(mappedBy = "paseos")
    private List<Comentarios> comentarios;

    @JsonIgnore
    @OneToMany(mappedBy = "paseos")
    private List<Calificaciones> calificaciones;

    @JsonIgnore
    @OneToOne(mappedBy = "paseos")
    private Ubicaciones ubicaciones;

    @Override
    public String toString() {
        return "Paseos{" +
                "idPaseo=" + idPaseo +
                ", reservas=" + reservas +
                ", mascotas=" + mascotas +
                ", paseadores=" + paseadores +
                ", propietarios=" + propietarios +
                ", comentario='" + comentario + '\'' +
                ", calificacion='" + calificacion + '\'' +
                ", costo=" + costo +
                ", fecha_paseo='" + fecha_paseo + '\'' +
                ", hora_paseo='" + hora_paseo + '\'' +
                ", duracion_real='" + duracion_real + '\'' +
                ", lugar='" + lugar + '\'' +
                ", estado=" + estado +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", createdBy=" + createdBy +
                ", updatedBy=" + updatedBy +
                ", comentarios=" + comentarios +
                ", calificaciones=" + calificaciones +
                '}';
    }
}
