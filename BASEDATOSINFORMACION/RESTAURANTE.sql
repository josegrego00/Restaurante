
-- CREACCION DE LA TABLA INGREDIENTES
-- === LA MISMA NO TIENE CANTIDAD YA QUE COMO VA A TENER 
-- RELACIONES MANYTOMANY TENDRA UNA TABLA PARA ESO
-- INGREDIENTE

CREATE TABLE INGREDIENTES(
ID INT PRIMARY KEY AUTO_INCREMENT,
NOMBRE VARCHAR(200) NOT NULL,
PRECIO DOUBLE NOT NULL,
ID_UNIDAD_MEDIDA INT NOT NULL -- YA SE CREO LA FOREIGN KEY
);
ALTER TABLE INGREDIENTES
ADD CONSTRAINT FK_INGREDIENTE_UNIDAD_MEDIDA FOREIGN KEY (ID_UNIDAD_MEDIDA) REFERENCES UNIDAD_MEDIDA(ID);

-- CREACION UNIDAD MEDIDA
CREATE TABLE UNIDAD_MEDIDA(
ID INT PRIMARY KEY AUTO_INCREMENT,
NOMBRE VARCHAR(200) UNIQUE NOT NULL,
INFORMACION VARCHAR(200)
);

-- CREACION DE STOCK INGREDIENTES 
-- ESTA TABLA SE DEBE RELACIONAR CON LA DE INGREDIENTES YA QUE LA MISMA 
-- CONTENDRA LO Q EXISTE EN EL SISTEMA
-- TAMBIEN DEBE DE HABER UNA TABLA DONDE SE GUARDE 
-- LA INFORMACION PARA LAS RECETAS.(ESTO TAMBIEN DEBE DE REALIZARSE EN OTRA TABLA)

CREATE TABLE STOCK_INGREDIENTES(
ID INT PRIMARY KEY AUTO_INCREMENT,
ID_INGREDIENTE INT NOT NULL, -- YA SE CREO LA FOREIGN KEY
CANTIDAD_EXISTENTE DOUBLE NOT NULL
);
-- SE LE CONLOCO LA FK PARA QUE TENGA COERENCIA
ALTER TABLE STOCK_INGREDIENTES
ADD CONSTRAINT FK_STOCK_INGRE_INGREDIENTES FOREIGN KEY (ID_INGREDIENTE) REFERENCES INGREDIENTES(ID);

-- RECETA, DEBE DE HABER UNA TABLA DONDE SE INGRESE 
-- O SE DETALLE EL DETALLE RECETA
CREATE TABLE RECETA(
ID INT PRIMARY KEY AUTO_INCREMENT,
NOMBRE_RECETA VARCHAR(200) UNIQUE NOT NULL
);

-- TABLA DONDE SE EXPRESA EL DETALLE DE LA RECETA.
CREATE TABLE RECETA_DETALLE(
ID INT PRIMARY KEY AUTO_INCREMENT,
ID_RECETA INT NOT NULL, -- YA SE ESTABLECIO LA RELACION CON LA FOREIGN KEY
ID_INGREDIENTE INT NOT NULL, -- YA SE ESTABLECION LA RELACION ENTRE ESTA TABLAS
CANTIDAD_RECETA DOUBLE NOT NULL,-- CANTIDAD POR RECETA
PRECIO_CORRESPONDIENTE DOUBLE NOT NULL-- CORRESPONDIENTE AL VALOR EN DINERO DE LA CANTIDAD
);

-- CREACION DE LAS LLAVES FORANEAS

ALTER TABLE RECETA_DETALLE
ADD CONSTRAINT FK_ID_RECETA_DETALLE FOREIGN KEY (ID_RECETA) REFERENCES RECETA(ID);
ALTER TABLE RECETA_DETALLE
ADD CONSTRAINT FK_ID_INGREDIENTE_DETALLE FOREIGN KEY (ID_INGREDIENTE) REFERENCES INGREDIENTES(ID);


-- PRODUCTO, ESTO INDICA QUE PRODUCTO SE CREO Y CON Q RECETA.
-- AQUI INDICA SI EL PRODUCTO ES CREADO O NO.
CREATE TABLE PRODUCTO(
ID INT PRIMARY KEY AUTO_INCREMENT,
NOMBRE_PRODUCTO VARCHAR(200) UNIQUE NOT NULL,
TIPO_PRODUCTO ENUM("CREADO","DIRECTO") NOT NULL,
ID_RECETA INT,
PRECIO DOUBLE NOT NULL
);

-- =====================================================
-- EST ES PARA LOS COMBOS 
-- PARA INDICAR CUANDO ES UN COMBO Y EL DETALLE DE LOS COMBOS 
CREATE TABLE COMBO (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    NOMBRE VARCHAR(200) UNIQUE NOT NULL,
    PRECIO DOUBLE NOT NULL
);

CREATE TABLE COMBO_DETALLE (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    ID_COMBO INT NOT NULL,
    ID_PRODUCTO INT NOT NULL,
    CANTIDAD INT NOT NULL, -- Número de unidades de ese producto en el combo
    PRECIO DOUBLE NOT NULL
);

ALTER TABLE COMBO_DETALLE
ADD CONSTRAINT FK_ID_COMBODETALLE FOREIGN KEY (ID_COMBO) REFERENCES COMBO(ID);

CREATE TABLE STOCK_PRODUCTO (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    ID_PRODUCTO INT NOT NULL, -- CREACION DE FOREIGN KEY LISTA
    CANTIDAD_EXISTENTE INT NOT NULL
);

ALTER TABLE STOCK_PRODUCTO
ADD CONSTRAINT FK_STOCK_PRODUCTOS FOREIGN KEY (ID_PRODUCTO) REFERENCES PRODUCTO(ID);

-- AAQUI CREO LA TABLA FACTURA PARA GUARDAR LO SE SE SOBREENTIENDE FACTURA
CREATE TABLE FACTURA(
ID INT PRIMARY KEY AUTO_INCREMENT,
FECHA_FACTURA DATE NOT NULL,
TOTAL_FACTURA DOUBLE NOT NULL
);

-- CREACION DE MI DETALLE FACTURA
CREATE TABLE DETALLE_FACTURA(
ID INT PRIMARY KEY AUTO_INCREMENT,
ID_FACTURA INT NOT NULL,
ID_PRODUCTO INT NOT NULL,
CANTIDAD INT NOT NULL,
PRECIO_TOTAL DOUBLE NOT NULL
);

