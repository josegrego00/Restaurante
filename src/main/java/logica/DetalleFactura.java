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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author josepino
 */
@Entity
@Table(name = "DETALLE_FACTURA")
@NamedQueries({
    @NamedQuery(name = "DetalleFactura.findAll", query = "SELECT d FROM DetalleFactura d"),
    @NamedQuery(name = "DetalleFactura.findById", query = "SELECT d FROM DetalleFactura d WHERE d.id = :id"),
    @NamedQuery(name = "DetalleFactura.findByIdFactura", query = "SELECT d FROM DetalleFactura d WHERE d.idFactura = :idFactura"),
    @NamedQuery(name = "DetalleFactura.findByIdProducto", query = "SELECT d FROM DetalleFactura d WHERE d.idProducto = :idProducto"),
    @NamedQuery(name = "DetalleFactura.findByCantidad", query = "SELECT d FROM DetalleFactura d WHERE d.cantidad = :cantidad"),
    @NamedQuery(name = "DetalleFactura.findByPrecioTotal", query = "SELECT d FROM DetalleFactura d WHERE d.precioTotal = :precioTotal")})
public class DetalleFactura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "ID_FACTURA")
    private int idFactura;
    @Basic(optional = false)
    @Column(name = "ID_PRODUCTO")
    private int idProducto;
    @Basic(optional = false)
    @Column(name = "CANTIDAD")
    private int cantidad;
    @Basic(optional = false)
    @Column(name = "PRECIO_TOTAL")
    private double precioTotal;

    public DetalleFactura() {
    }

    public DetalleFactura(Integer id) {
        this.id = id;
    }

    public DetalleFactura(Integer id, int idFactura, int idProducto, int cantidad, double precioTotal) {
        this.id = id;
        this.idFactura = idFactura;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
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

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
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
        if (!(object instanceof DetalleFactura)) {
            return false;
        }
        DetalleFactura other = (DetalleFactura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logica.DetalleFactura[ id=" + id + " ]";
    }
    
}
