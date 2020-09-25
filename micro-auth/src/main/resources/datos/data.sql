
 ---------------------------------------------- ADMINISTRADOR  ----------------------------------------------
 ---------------------------------------------- ADMINISTRADOR  ----------------------------------------------
 ---------------------------------------------- ADMINISTRADOR  ----------------------------------------------
 ---------------------------------------------- ADMINISTRADOR  ----------------------------------------------
 ---------------------------------------------- ADMINISTRADOR  ----------------------------------------------
 ---------------------------------------------- ADMINISTRADOR  ---------------------------------------------- 
 ---------------------------------------------- ADMINISTRADOR  ----------------------------------------------
 ---------------------------------------------- ADMINISTRADOR  ----------------------------------------------
 ---------------------------------------------- ADMINISTRADOR  ----------------------------------------------
 

-- DATOS USUARIOS ADMINISTRADORES  -- PASSWORD ES: gilberto
INSERT INTO usuario_administrador (id,apellido1,apellido2,correo,enabled,fecha_alta,nombre,password,ultima_actualizacion,username) VALUES 
(1,'Apellido1','Apellido2','gilberto@outlook.com.mx',true,'2019-02-17 00:00:00','gilberto','$2a$10$wSefsEr0yXA/T859ChS0y.xN6339lfExoQYVPxLRosV6fkX/Q9M5a','2019-02-17 00:00:00','gilbertoAdmin');

INSERT INTO usuario_administrador (id,apellido1,apellido2,correo,enabled,fecha_alta,nombre,password,ultima_actualizacion,username) VALUES 
(1,'Apellido1','Apellido2','demo@outlook.com.mx',true,'2019-02-17 00:00:00','Nombre','$2a$10$oZktcDLV9DqU8XJ3W83Jk..nOEHJ2wxrOUaFae9URXZ.mLGnaHAHe','2019-02-17 00:00:00','demodemo');


  
-- DATOS OAUTH CLIENT DETAILS -- PASSWORD ES: 123
INSERT INTO oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove) VALUES 
('template', 'template_resource', '$2a$10$u/tfnwocKdzo8jplduK3BeWJcQ6rINhjnXLSomFWMX6KiltJqr3tO', 'read,write', 'password', 'http://localhost', NULL, 6000 , 0, NULL, 'false');
   
-- PERMISOS -- AUTORITIES
DELETE FROM usuario_administrador_permiso;
DELETE FROM permiso_administrador;

-- GENERAL 
insert into permiso_administrador values (1,'Permite recibir las notificaciones de todo movimiento dentro de la aplicación' , 'web:administracion:notificacion:master');

-- MANTENIMIENTO 
insert into permiso_administrador values (2,'Permite ver el contenido de swagger' , 'web:administracion:mantenimiento:eureka:swagger');
insert into permiso_administrador values (3,'Permite ver el contenido de swagger' , 'web:administracion:mantenimiento:auth:swagger');
insert into permiso_administrador values (4,'Permite ver el contenido de swagger' , 'web:administracion:mantenimiento:gateway:swagger');
insert into permiso_administrador values (5,'Permite ver el contenido de swagger' , 'web:administracion:mantenimiento:correo:swagger');
insert into permiso_administrador values (6,'Permite ver el contenido de swagger' , 'web:administracion:mantenimiento:usuarios:swagger');
insert into permiso_administrador values (7,'Permite ver el contenido de swagger' , 'web:administracion:mantenimiento:admin:swagger');
insert into permiso_administrador values (8,'Permite ver el contenido de swagger' , 'web:administracion:mantenimiento:publico:swagger');
 

-- USUARIOS PUBLICOS
insert into permiso_administrador values (9,'Muestra a los permisos publicos'   , 'web:administracion:permisos:publicos:mostrar');
insert into permiso_administrador values (10,'Muestra a los usuarios publicos'   , 'web:administracion:usuarios:publicos:mostrar');
insert into permiso_administrador values (11,'Crea un usuario público'   , 'web:administracion:usuarios:publicos:crear');
insert into permiso_administrador values (12,'Edita un usuario público'  , 'web:administracion:usuarios:publicos:actualizar');
insert into permiso_administrador values (13,'Borrar un usuario público' , 'web:administracion:usuarios:publicos:borrar');

-- USUARIOS ADMINISTRADORES
insert into permiso_administrador values (14,'Muestra a los permisos administradores'   , 'web:administracion:permisos:admin:mostrar');
insert into permiso_administrador values (15,'Muestra a los usuarios administradores'   , 'web:administracion:usuarios:admin:mostrar');
insert into permiso_administrador values (16,'Crea un usuario administradores'   , 'web:administracion:usuarios:admin:crear');
insert into permiso_administrador values (17,'Edita un usuario administradores'  , 'web:administracion:usuarios:admin:actualizar');
insert into permiso_administrador values (18,'Borrar un usuario administradores' , 'web:administracion:usuarios:admin:borrar');

-- LOG
insert into permiso_administrador values (19,'Muestra el log del sistema' , 'web:administracion:log:todos');

-- SESIONES
insert into permiso_administrador values (20,'Muestra las sesiones activas' , 'web:administracion:sesiones:mostrar');
insert into permiso_administrador values (21,'Cierra la sesion de un usuario' , 'web:administracion:sesiones:cerrar');
 
-- CLIENTE DETALLE
insert into permiso_administrador values (22,'Muestra las sesiones activas' , 'web:administracion:cliente:detalle:todos'); 

-- CLIENTE TOKEN
insert into permiso_administrador values (23,'Muestra las sesiones activas' , 'web:administracion:cliente:token:todos');  
 
-- BD
insert into permiso_administrador values (24,'Muestra el dashboard de la base de datos' , 'web:administracion:basedatos:todos'); 

-- DASHBOARD
insert into permiso_administrador values (25,'Muestra la sección de Dashboard' , 'web:administracion:dashboard:todos');
 

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
insert into usuario_administrador_permiso values (1,17);
insert into usuario_administrador_permiso values (1,18);
insert into usuario_administrador_permiso values (1,20); 
insert into usuario_administrador_permiso values (1,21); 
insert into usuario_administrador_permiso values (1,22); 
insert into usuario_administrador_permiso values (1,23); 
insert into usuario_administrador_permiso values (1,24); 
insert into usuario_administrador_permiso values (1,25);  
  