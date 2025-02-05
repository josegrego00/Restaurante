package persistencia;

import com.mysql.cj.conf.PropertyKey;
import java.util.List;
import logica.Ingredientes;
import logica.Producto;
import logica.Receta;
import logica.RecetaDetalle;
import logica.StockIngredientes;
import logica.UnidadMedida;

public class ControladoraPersistencia {

    private UnidadMedidaJpaController unidadMedidaJpaController = null;
    private IngredientesJpaController ingredientesJpaController = null;
    private StockIngredientesJpaController stockIngredientesJpaController=null;
    private RecetaJpaController recetaJpaController=null;
    private RecetaDetalleJpaController recetaDetalleJpaController=null;
    private ProductoJpaController productoJpaController=null;
    
    public ControladoraPersistencia() {
        unidadMedidaJpaController = new UnidadMedidaJpaController();
        ingredientesJpaController = new IngredientesJpaController();
        stockIngredientesJpaController= new StockIngredientesJpaController();
        recetaJpaController= new RecetaJpaController();
        recetaDetalleJpaController = new RecetaDetalleJpaController();
        productoJpaController = new ProductoJpaController();
    }

    public void crearIngrediente(Ingredientes ingrediente) {
        ingredientesJpaController.create(ingrediente);
    }

    public List<UnidadMedida> traerUnidadesPersistencia() {
        return unidadMedidaJpaController.findUnidadMedidaEntities();

    }

    public void llenarInventarioInicial(StockIngredientes stockIngredientes) {
        stockIngredientesJpaController.create(stockIngredientes);
    }

    public List<Ingredientes> traerIngredientes() {
       return ingredientesJpaController.findIngredientesEntities();
    }

    public List<Receta> traerRecetas() {
        return recetaJpaController.findRecetaEntities();
    }

    public void crearReceta(Receta receta) {
       recetaJpaController.create(receta);
    }

    public void crearDetalleReceta(RecetaDetalle recetaDetalle) {
        recetaDetalleJpaController.create(recetaDetalle);
    }

    public List<Producto> traerProductos() {
         return productoJpaController.findProductoEntities();
    }

    public void crearProducto(Producto producto) {
        productoJpaController.create(producto);
    }

}
