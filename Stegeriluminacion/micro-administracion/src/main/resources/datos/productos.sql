-- DATOS DE PRUEBA

Select 1;

INSERT INTO producto (id, activo, activo_publico, costo, descripcion, etiqueta, fecha_actualizacion, fecha_alta, uuid, categoria_id, marca_id, unidad_medida_id, unidad_venta_id) VALUES (1,true,true,500,'Focos normales','Foco','2019-08-09 00:00:00','2019-08-09 00:00:00','AS',1,1,1,1);
 
INSERT INTO direccion_vivienda
(id, ageb, calle1, calle2, calle3, calle_principal, codigo_postal, edificio, edificio2, etiqueta, fecha_actualizacion, fecha_alta, letra_exterior, letra_interior, manzana, numero_exterior, numero_interior, tipo_asentamiento_id, tipo_municipio_id, tipo_vialidad1_id, tipo_vialidad2_id, tipo_vialidad3_id, tipo_vialidad_principal_id)
VALUES
(1,'eab','calle1','calle2','calle3','calle principal','57900','edificio 1','edificio 2','etiqueta','2019-08-09 00:00:00.000000','2019-08-09 00:00:00.000000','letra exterior','letra interior','manzana','numero exterior','numero interior',1,1,1,1,1,1);

INSERT INTO proveedor (id,activo,etiqueta,fecha_actualizacion,fecha_alta,direccion_id) VALUES (1,1,'Proveedor Ejemplo','2019-08-09 00:00:00.000000','2019-08-09 00:00:00.000000',1);
 
INSERT INTO inventario (id,costo,costo_mayoreo,existencias,fecha_actualizacion,fecha_alta,inv_maximo,inv_minimo,producto_id,proveedor_id)
VALUES (1,100,160,10,'2019-08-09 00:00:00.000000','2019-08-09 00:00:00.000000',1,2,1,1);
 