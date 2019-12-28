-- DATOS USUARIOS PUBLICOS

Select 1;

-- PASSWORD ES: gilberto
INSERT INTO usuario_publico (id,apellido1,apellido2,correo,enabled,fecha_alta,nombre,password,ultima_actualizacion,username, telefono_celular,limit_request, time_unit, token_expiration) VALUES  (1,'Colin','Velasco','jose.gilberto.colin@outlook.com.mx',1,'2019-02-17 00:00:00','José Gilberto','$2a$10$wSefsEr0yXA/T859ChS0y.xN6339lfExoQYVPxLRosV6fkX/Q9M5a','2019-02-17 00:00:00','gilbertoPublico','5577750820',100,'MINUTES',30);

  
-- DATOS USUARIOS ADMINISTRADORES  -- PASSWORD ES: gilberto
INSERT INTO usuario_administrador (id,apellido1,apellido2,correo,enabled,fecha_alta,nombre,password,ultima_actualizacion,username) VALUES (1,'Colin','Velasco','jose.gilberto.colin@outlook.com.mx',1,'2019-02-17 00:00:00','José Gilberto','$2a$10$wSefsEr0yXA/T859ChS0y.xN6339lfExoQYVPxLRosV6fkX/Q9M5a','2019-02-17 00:00:00','gilbertoAdmin');
 

-- DATOS OAUTH CLIENT DETAILS -- PASSWORD ES: 123
INSERT INTO oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove) VALUES 
('stegerilumination', 'stegerilumination_resource', '$2a$10$u/tfnwocKdzo8jplduK3BeWJcQ6rINhjnXLSomFWMX6KiltJqr3tO', 'read,write', 'password', 'http://localhost', NULL, 6000 , 0, NULL, 'false');
 
 INSERT INTO oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove) VALUES 
('stegerilumination_publico', 'stegerilumination_publico_resource', '$2a$10$u/tfnwocKdzo8jplduK3BeWJcQ6rINhjnXLSomFWMX6KiltJqr3tO', 'read,write', 'password', 'http://localhost', NULL, 6000 , 0, NULL, 'false');
 
 
DELETE FROM usuario_administrador_permiso;
DELETE FROM permiso_administrador; 

 
-- PERMISOS -- AUTORITIES
insert into permiso_administrador values (1,'Permite recibir las notificaciones de todo movimiento dentro de la aplicación' , 'web:administracion:notificacion:master');
insert into permiso_administrador values (2,'Permite ver el contenido de swagger' , 'web:administracion:mantenimiento:swagger');

insert into permiso_administrador values (3,'Muestra a los permisos publicos'   , 'web:administracion:permisos:publicos:mostrar');
insert into permiso_administrador values (4,'Muestra a los usuarios publicos'   , 'web:administracion:usuarios:publicos:mostrar');
insert into permiso_administrador values (5,'Crea un usuario público'   , 'web:administracion:usuarios:publicos:crear');
insert into permiso_administrador values (6,'Edita un usuario público'  , 'web:administracion:usuarios:publicos:actualizar');
insert into permiso_administrador values (7,'Borrar un usuario público' , 'web:administracion:usuarios:publicos:borrar');

insert into permiso_administrador values (8,'Muestra a los permisos administradores'   , 'web:administracion:permisos:admin:mostrar');
insert into permiso_administrador values (9,'Muestra a los usuarios administradores'   , 'web:administracion:usuarios:admin:mostrar');
insert into permiso_administrador values (10,'Crea un usuario administradores'   , 'web:administracion:usuarios:admin:crear');
insert into permiso_administrador values (11,'Edita un usuario administradores'  , 'web:administracion:usuarios:admin:actualizar');
insert into permiso_administrador values (12,'Borrar un usuario administradores' , 'web:administracion:usuarios:admin:borrar');

insert into permiso_administrador values (13,'Muestra el log del sistema' , 'web:administracion:log:mostrar');

insert into permiso_administrador values (14,'Muestra las sesiones activas' , 'web:administracion:sesiones:mostrar');
insert into permiso_administrador values (15,'Cierra la sesion de un usuario' , 'web:administracion:sesiones:cerrar');

insert into permiso_administrador values (16,'Muestra el trackeo de acciones de usuarios del sistema' , 'web:administracion:tracker:mostrar');




insert into usuario_administrador_permiso values (1,1);
insert into usuario_administrador_permiso values (1,2);
insert into usuario_administrador_permiso values (1,3);
insert into usuario_administrador_permiso values (1,4);
insert into usuario_administrador_permiso values (1,5);
insert into usuario_administrador_permiso values (1,6);
insert into usuario_administrador_permiso values (1,7);
insert into usuario_administrador_permiso values (1,8);
insert into usuario_administrador_permiso values (1,9);
insert into usuario_administrador_permiso values (1,10);
insert into usuario_administrador_permiso values (1,11);
insert into usuario_administrador_permiso values (1,12);
insert into usuario_administrador_permiso values (1,13);
insert into usuario_administrador_permiso values (1,14);
insert into usuario_administrador_permiso values (1,15);
insert into usuario_administrador_permiso values (1,16);

-- LOGGER
INSERT INTO log (id,accion,apartado,fecha_alta,tipo_usuario,usuario) VALUES (1,'Alta de la plantilla','Sistema',now(),'publico','gilbertoAdmin');


-- PERMISOS PUBLICOS -- AUTORITIES
INSERT INTO permiso_publico values ( 1, 'Descripción' , 'web:publico:apartado1:subapartado:permisodealgo' );
INSERT INTO permiso_publico values ( 2, 'Descripción' , 'web:publico:apartado1:subapartado:permisodealgo2' );
INSERT INTO permiso_publico values ( 3, 'Descripción' , 'web:publico:apartado1:subapartado:permisodealgo3' );
INSERT INTO permiso_publico values ( 4, 'Descripción' , 'web:publico:apartado2:subapartado:permisodealgo' );





