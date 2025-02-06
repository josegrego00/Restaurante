/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import persistencia.ControladoraPersistencia;

public class ControladoraLogica {

    private ControladoraPersistencia controladoraPersistencia = null;
    private Ingredientes ingredientes = null;
    private UnidadMedida unidadMedida = null;
    private StockIngredientes stockIngredientes = null;
    private Receta receta = null;
    private Producto producto = null;

    public ControladoraLogica() {
        controladoraPersistencia = new ControladoraPersistencia();

    }

    public void crearIngredinete(Ingredientes i) {

    }

    public List<UnidadMedida> traerUnidadesMedidas() {
        return controladoraPersistencia.traerUnidadesPersistencia();
    }

    public void crearIngredinete(String nombreIngrediente, String precioIngrediente, String unidadMedida, String cantidadInicial) {
        stockIngredientes = new StockIngredientes();
        ingredientes = new Ingredientes();
        ingredientes.setNombre(nombreIngrediente);
        ingredientes.setPrecio(Double.parseDouble(precioIngrediente));
        for (UnidadMedida um : traerUnidadesMedidas()) {
            if (um.getNombre().equals(unidadMedida)) {
                ingredientes.setIdUnidadMedida(um);
                break;
            }
        }//ingredientes.setStockIngredientesList(stockIngredientesList);
        controladoraPersistencia.crearIngrediente(ingredientes);
        stockIngredientes.setCantidadExistente(Double.parseDouble(cantidadInicial));
        stockIngredientes.setIdIngrediente(ingredientes);
        controladoraPersistencia.llenarInventarioInicial(stockIngredientes);

    }

    public List<Ingredientes> traerIngredientes() {
        return controladoraPersistencia.traerIngredientes();
    }

    public List<Receta> traerRecetas() {
        return controladoraPersistencia.traerRecetas();
    }

    public void crearReceta(String nombreReceta) {
        receta = new Receta();
        receta.setNombreReceta(nombreReceta);

        controladoraPersistencia.crearReceta(receta);
    }

    public Ingredientes traerIngrediente(String ingrediente) {
        for (Ingredientes ingredientes : controladoraPersistencia.traerIngredientes()) {
            if (ingredientes.getNombre().equals(ingrediente)) {
                return ingredientes;
            }
        }
        return null;
    }

    public Receta traerReceta(String nombreReceta) {
        for (Receta receta : controladoraPersistencia.traerRecetas()) {
            if (receta.getNombreReceta().equals(nombreReceta)) {
                return receta;
            }
        }
        return null;
    }

    public void crearDetalleReceta(double Cantidad, double costoCorrespondiente, Ingredientes ingrediente, Receta receta) {
        RecetaDetalle recetaDetalle = new RecetaDetalle();
        recetaDetalle.setCantidadReceta(Cantidad);
        recetaDetalle.setPrecioCorrespondiente(costoCorrespondiente);
        recetaDetalle.setIdIngrediente(ingrediente);
        recetaDetalle.setIdReceta(receta);
        controladoraPersistencia.crearDetalleReceta(recetaDetalle);
    }

    public List<Producto> traerProductos() {
        return controladoraPersistencia.traerProductos();
    }

    public void crearProducto(String nombreProducto, String tipoProducto, Receta receta, double precio) {
        producto = new Producto();
        producto.setNombreProducto(nombreProducto);
        producto.setTipoProducto(tipoProducto);
        if (receta != null) {
            producto.setIdReceta(receta.getId());
        }
        producto.setPrecio(precio);
        controladoraPersistencia.crearProducto(producto);
    }

    public int traerUltimaFactura() {
        List<Factura> listaFacturas = controladoraPersistencia.traerFacturas();
        if (!listaFacturas.isEmpty()) {
            Factura nFactura = listaFacturas.get(listaFacturas.size() - 1);
            return (nFactura.getId() + 1);
        }
        return 0;
    }

    public Producto traerProducto(int valor) {
        return controladoraPersistencia.traerProducto(valor);
    }

    public void crearFactura(LocalDateTime fecha, double totaFactura) {
        Factura factura = new Factura();
        factura.setFechaFactura(convertToDate(fecha));
        factura.setTotalFactura(totaFactura);
        controladoraPersistencia.crearFactura(factura);
    }

    public static Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public Factura traerFactura(int idFactura) {
        return controladoraPersistencia.traerFactura(idFactura);
    }

    public void crearDetalleFactura(Factura factura, Producto producto, double cantidadComprada, double totalCompra) {
        DetalleFactura detalleFactura= new DetalleFactura();
        detalleFactura.setIdFactura(factura.getId());
        detalleFactura.setIdProducto(producto.getId());
        detalleFactura.setCantidad((int) cantidadComprada);
        detalleFactura.setPrecioTotal(totalCompra);
        controladoraPersistencia.crearDetalleFactura(detalleFactura);
    }

}
