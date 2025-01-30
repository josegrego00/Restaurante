/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author josepino
 */
@Entity
@Table(name = "INGREDIENTES")
@NamedQueries({
    @NamedQuery(name = "Ingredientes.findAll", query = "SELECT i FROM Ingredientes i"),
    @NamedQuery(name = "Ingredientes.findById", query = "SELECT i FROM Ingredientes i WHERE i.id = :id"),
    @NamedQuery(name = "Ingredientes.findByNombre", query = "SELECT i FROM Ingredientes i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "Ingredientes.findByPrecio", query = "SELECT i FROM Ingredientes i WHERE i.precio = :precio")})
public class Ingredientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "PRECIO")
    private double precio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idIngrediente")
    private List<RecetaDetalle> recetaDetalleList;
    @JoinColumn(name = "ID_UNIDAD_MEDIDA", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private UnidadMedida idUnidadMedida;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idIngrediente")
    private List<StockIngredientes> stockIngredientesList;

    public Ingredientes() {
    }

    public Ingredientes(Integer id) {
        this.id = id;
    }

    public Ingredientes(Integer id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public List<RecetaDetalle> getRecetaDetalleList() {
        return recetaDetalleList;
    }

    public void setRecetaDetalleList(List<RecetaDetalle> recetaDetalleList) {
        this.recetaDetalleList = recetaDetalleList;
    }

    public UnidadMedida getIdUnidadMedida() {
        return idUnidadMedida;
    }

    public void setIdUnidadMedida(UnidadMedida idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public List<StockIngredientes> getStockIngredientesList() {
        return stockIngredientesList;
    }

    public void setStockIngredientesList(List<StockIngredientes> stockIngredientesList) {
        this.stockIngredientesList = stockIngredientesList;
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
        if (!(object instanceof Ingredientes)) {
            return false;
        }
        Ingredientes other = (Ingredientes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logica.Ingredientes[ id=" + id + " ]";
    }
    
}
