
INSERT INTO unidad_medida (id,etiqueta,etiqueta_corta,fecha_actualizacion,fecha_alta) VALUES (1, 'Kilogramo', 'KG', '2019-08-29 04:57:33','2019-08-29 04:57:33');

INSERT INTO unidad_venta (id,etiqueta,etiqueta_corta,fecha_actualizacion,fecha_alta) VALUES (1, 'Kilogramo', 'KG', '2019-08-29 04:57:33','2019-08-29 04:57:33');

INSERT INTO categoria (id,etiqueta,fecha_actualizacion,fecha_alta,padre_id) VALUES (1,'Iluminacion','2019-08-29 04:57:33','2019-08-29 04:57:33',null);

INSERT INTO marca (id,etiqueta,fecha_actualizacion,fecha_alta) VALUES (1,'Lenovo','2019-08-29 04:57:33','2019-08-29 04:57:33');

INSERT INTO tipo_asentamiento (id,etiqueta,version) VALUES (1,'Colonia',0); 

INSERT INTO tipo_entidad (id,clave,etiqueta,version) VALUES (1,'009','CDMX',0); 

INSERT INTO tipo_municipio (id,clave,etiqueta,version,tipo_entidad_id) VALUES (1,'009','ALvaro Obregon',0,1);

INSERT INTO tipo_vialidad(id,etiqueta, version) VALUES (1,'CALLE',0);
  