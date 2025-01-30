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
@Table(name = "COMBO_DETALLE")
@NamedQueries({
    @NamedQuery(name = "ComboDetalle.findAll", query = "SELECT c FROM ComboDetalle c"),
    @NamedQuery(name = "ComboDetalle.findById", query = "SELECT c FROM ComboDetalle c WHERE c.id = :id"),
    @NamedQuery(name = "ComboDetalle.findByIdProducto", query = "SELECT c FROM ComboDetalle c WHERE c.idProducto = :idProducto"),
    @NamedQuery(name = "ComboDetalle.findByCantidad", query = "SELECT c FROM ComboDetalle c WHERE c.cantidad = :cantidad"),
    @NamedQuery(name = "ComboDetalle.findByPrecio", query = "SELECT c FROM ComboDetalle c WHERE c.precio = :precio")})
public class ComboDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "ID_PRODUCTO")
    private int idProducto;
    @Basic(optional = false)
    @Column(name = "CANTIDAD")
    private int cantidad;
    @Basic(optional = false)
    @Column(name = "PRECIO")
    private double precio;
    @JoinColumn(name = "ID_COMBO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Combo idCombo;

    public ComboDetalle() {
    }

    public ComboDetalle(Integer id) {
        this.id = id;
    }

    public ComboDetalle(Integer id, int idProducto, int cantidad, double precio) {
        this.id = id;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Combo getIdCombo() {
        return idCombo;
    }

    public void setIdCombo(Combo idCombo) {
        this.idCombo = idCombo;
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
        if (!(object instanceof ComboDetalle)) {
            return false;
        }
        ComboDetalle other = (ComboDetalle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logica.ComboDetalle[ id=" + id + " ]";
    }
    
}
