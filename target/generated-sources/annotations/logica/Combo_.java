package logica;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logica.ComboDetalle;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-01-29T11:09:54", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Combo.class)
public class Combo_ { 

    public static volatile ListAttribute<Combo, ComboDetalle> comboDetalleList;
    public static volatile SingularAttribute<Combo, Double> precio;
    public static volatile SingularAttribute<Combo, Integer> id;
    public static volatile SingularAttribute<Combo, String> nombre;

}