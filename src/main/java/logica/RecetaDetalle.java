/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author josepino
 */
@Entity
@Table(name = "RECETA_DETALLE")
@NamedQueries({
    @NamedQuery(name = "RecetaDetalle.findAll", query = "SELECT r FROM RecetaDetalle r"),
    @NamedQuery(name = "RecetaDetalle.findById", query = "SELECT r FROM RecetaDetalle r WHERE r.id = :id"),
    @NamedQuery(name = "RecetaDetalle.findByCantidadReceta", query = "SELECT r FROM RecetaDetalle r WHERE r.cantidadReceta = :cantidadReceta"),
    @NamedQuery(name = "RecetaDetalle.findByPrecioCorrespondiente", query = "SELECT r FROM RecetaDetalle r WHERE r.precioCorrespondiente = :precioCorrespondiente")})
public class RecetaDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "CANTIDAD_RECETA")
    private double cantidadReceta;
    @Basic(optional = false)
    @Column(name = "PRECIO_CORRESPONDIENTE")
    private double precioCorrespondiente;
    @JoinColumn(name = "ID_INGREDIENTE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Ingredientes idIngrediente;
    @JoinColumn(name = "ID_RECETA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Receta idReceta;

    public RecetaDetalle() {
    }

    public RecetaDetalle(Integer id) {
        this.id = id;
    }

    public RecetaDetalle(double cantidadReceta, double precioCorrespondiente, Ingredientes idIngrediente, Receta idReceta) {
        this.cantidadReceta = cantidadReceta;
        this.precioCorrespondiente = precioCorrespondiente;
        this.idIngrediente = idIngrediente;
        this.idReceta = idReceta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getCantidadReceta() {
        return cantidadReceta;
    }

    public void setCantidadReceta(double cantidadReceta) {
        this.cantidadReceta = cantidadReceta;
    }

    public double getPrecioCorrespondiente() {
        return precioCorrespondiente;
    }

    public void setPrecioCorrespondiente(double precioCorrespondiente) {
        this.precioCorrespondiente = precioCorrespondiente;
    }

    public Ingredientes getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Ingredientes idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public Receta getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Receta idReceta) {
        this.idReceta = idReceta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecetaDetalle)) {
            return false;
        }
        RecetaDetalle other = (RecetaDetalle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logica.RecetaDetalle[ id=" + id + " ]";
    }

}
