package logica;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logica.Producto;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-01-29T11:09:54", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(StockProducto.class)
public class StockProducto_ { 

    public static volatile SingularAttribute<StockProducto, Integer> id;
    public static volatile SingularAttribute<StockProducto, Producto> idProducto;
    public static volatile SingularAttribute<StockProducto, Integer> cantidadExistente;

}