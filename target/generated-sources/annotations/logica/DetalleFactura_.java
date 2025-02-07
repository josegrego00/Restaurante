package logica;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-02-07T10:30:23", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(DetalleFactura.class)
public class DetalleFactura_ { 

    public static volatile SingularAttribute<DetalleFactura, Integer> idFactura;
    public static volatile SingularAttribute<DetalleFactura, Integer> id;
    public static volatile SingularAttribute<DetalleFactura, Integer> idProducto;
    public static volatile SingularAttribute<DetalleFactura, Integer> cantidad;
    public static volatile SingularAttribute<DetalleFactura, Double> precioTotal;

}