package logica;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logica.Ingredientes;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-02-07T10:30:23", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(UnidadMedida.class)
public class UnidadMedida_ { 

    public static volatile SingularAttribute<UnidadMedida, String> informacion;
    public static volatile SingularAttribute<UnidadMedida, Integer> id;
    public static volatile SingularAttribute<UnidadMedida, String> nombre;
    public static volatile ListAttribute<UnidadMedida, Ingredientes> ingredientesList;

}