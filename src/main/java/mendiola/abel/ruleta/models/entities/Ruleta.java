package mendiola.abel.ruleta.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "personas", schema = "universidad")
public class Ruleta implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String arregloNumeroColor[];

    @Column(name = "estaAbierta")
    private Boolean estaAbierta;

    @Column(name = "valorApuestas")
    private Double valorApuestas;

    @Column(name = "fecha_alta")
    private Date fechaAlta;

    @Column(name = "fecha_modificacion")
    private Date fechaModificacion;

    public Ruleta(Integer id, Boolean estaAbierta, Double valorApuestas) {
        this.id = id;
        this.estaAbierta = estaAbierta;
        this.valorApuestas = valorApuestas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ruleta ruleta = (Ruleta) o;
        return Objects.equals(id, ruleta.id) && Arrays.equals(arregloNumeroColor, ruleta.arregloNumeroColor) && Objects.equals(estaAbierta, ruleta.estaAbierta) && Objects.equals(valorApuestas, ruleta.valorApuestas) && Objects.equals(fechaAlta, ruleta.fechaAlta) && Objects.equals(fechaModificacion, ruleta.fechaModificacion);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, estaAbierta, valorApuestas, fechaAlta, fechaModificacion);
        result = 31 * result + Arrays.hashCode(arregloNumeroColor);
        return result;
    }

    @PrePersist
    private void antesPersistir()
    {
        this.fechaAlta = new Date();
    }

    @PreUpdate
    private void antesActualizar()
    {
        this.fechaModificacion = new Date();
    }

}
