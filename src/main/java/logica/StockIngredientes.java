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
@Table(name = "STOCK_INGREDIENTES")
@NamedQueries({
    @NamedQuery(name = "StockIngredientes.findAll", query = "SELECT s FROM StockIngredientes s"),
    @NamedQuery(name = "StockIngredientes.findById", query = "SELECT s FROM StockIngredientes s WHERE s.id = :id"),
    @NamedQuery(name = "StockIngredientes.findByCantidadExistente", query = "SELECT s FROM StockIngredientes s WHERE s.cantidadExistente = :cantidadExistente")})
public class StockIngredientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "CANTIDAD_EXISTENTE")
    private double cantidadExistente;
    @JoinColumn(name = "ID_INGREDIENTE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Ingredientes idIngrediente;

    public StockIngredientes() {
    }

    public StockIngredientes(Integer id) {
        this.id = id;
    }

    public StockIngredientes(Integer id, double cantidadExistente) {
        this.id = id;
        this.cantidadExistente = cantidadExistente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getCantidadExistente() {
        return cantidadExistente;
    }

    public void setCantidadExistente(double cantidadExistente) {
        this.cantidadExistente = cantidadExistente;
    }

    public Ingredientes getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Ingredientes idIngrediente) {
        this.idIngrediente = idIngrediente;
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
        if (!(object instanceof StockIngredientes)) {
            return false;
        }
        StockIngredientes other = (StockIngredientes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logica.StockIngredientes[ id=" + id + " ]";
    }
    
}
