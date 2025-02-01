package persistencia;

import java.util.List;
import logica.Ingredientes;
import logica.Receta;
import logica.StockIngredientes;
import logica.UnidadMedida;

public class ControladoraPersistencia {

    private UnidadMedidaJpaController unidadMedidaJpaController = null;
    private IngredientesJpaController ingredientesJpaController = null;
    private StockIngredientesJpaController stockIngredientesJpaController=null;
    private RecetaJpaController recetaJpaController=null;

    public ControladoraPersistencia() {
        unidadMedidaJpaController = new UnidadMedidaJpaController();
        ingredientesJpaController = new IngredientesJpaController();
        stockIngredientesJpaController= new StockIngredientesJpaController();
        recetaJpaController= new RecetaJpaController();
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

}
