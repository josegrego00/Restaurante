package logica;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logica.Ingredientes;
import logica.Receta;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-01-29T11:09:54", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(RecetaDetalle.class)
public class RecetaDetalle_ { 

    public static volatile SingularAttribute<RecetaDetalle, Double> cantidadReceta;
    public static volatile SingularAttribute<RecetaDetalle, Double> precioCorrespondiente;
    public static volatile SingularAttribute<RecetaDetalle, Receta> idReceta;
    public static volatile SingularAttribute<RecetaDetalle, Integer> id;
    public static volatile SingularAttribute<RecetaDetalle, Ingredientes> idIngrediente;

}