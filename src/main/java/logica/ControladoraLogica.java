/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.util.List;
import persistencia.ControladoraPersistencia;

public class ControladoraLogica {

    private ControladoraPersistencia controladoraPersistencia = null;
    private Ingredientes ingredientes = null;
    private UnidadMedida unidadMedida = null;
    private StockIngredientes stockIngredientes = null;
    private Receta receta = null;

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
        RecetaDetalle recetaDetalle= new RecetaDetalle();
        recetaDetalle.setCantidadReceta(Cantidad);
        recetaDetalle.setPrecioCorrespondiente(costoCorrespondiente);
        recetaDetalle.setIdIngrediente(ingrediente);
        recetaDetalle.setIdReceta(receta);
        controladoraPersistencia.crearDetalleReceta(recetaDetalle);
    }

}
