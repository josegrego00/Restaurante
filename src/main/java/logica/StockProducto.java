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
@Table(name = "STOCK_PRODUCTO")
@NamedQueries({
    @NamedQuery(name = "StockProducto.findAll", query = "SELECT s FROM StockProducto s"),
    @NamedQuery(name = "StockProducto.findById", query = "SELECT s FROM StockProducto s WHERE s.id = :id"),
    @NamedQuery(name = "StockProducto.findByCantidadExistente", query = "SELECT s FROM StockProducto s WHERE s.cantidadExistente = :cantidadExistente")})
public class StockProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "CANTIDAD_EXISTENTE")
    private int cantidadExistente;
    @JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Producto idProducto;

    public StockProducto() {
    }

    public StockProducto(Integer id) {
        this.id = id;
    }

    public StockProducto(Integer id, int cantidadExistente) {
        this.id = id;
        this.cantidadExistente = cantidadExistente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCantidadExistente() {
        return cantidadExistente;
    }

    public void setCantidadExistente(int cantidadExistente) {
        this.cantidadExistente = cantidadExistente;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
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
        if (!(object instanceof StockProducto)) {
            return false;
        }
        StockProducto other = (StockProducto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logica.StockProducto[ id=" + id + " ]";
    }
    
}
