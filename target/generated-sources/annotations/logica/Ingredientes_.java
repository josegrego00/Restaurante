package logica;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logica.RecetaDetalle;
import logica.StockIngredientes;
import logica.UnidadMedida;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-01-29T11:09:54", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Ingredientes.class)
public class Ingredientes_ { 

    public static volatile SingularAttribute<Ingredientes, Double> precio;
    public static volatile ListAttribute<Ingredientes, RecetaDetalle> recetaDetalleList;
    public static volatile SingularAttribute<Ingredientes, UnidadMedida> idUnidadMedida;
    public static volatile SingularAttribute<Ingredientes, Integer> id;
    public static volatile SingularAttribute<Ingredientes, String> nombre;
    public static volatile ListAttribute<Ingredientes, StockIngredientes> stockIngredientesList;

}