-- DATOS USUARIOS PUBLICOS

Select 1;
-- PASSWORD ES: gilberto
INSERT INTO usuario_publico (id,apellido1,apellido2,correo,enabled,fecha_alta,nombre,password,ultima_actualizacion,username, telefono_celular,limit_request, time_unit, token_expiration) VALUES  (1,'Colin','Velasco','jose.gilberto.colin@outlook.com.mx',1,'2019-02-17 00:00:00','José Gilberto','$2a$10$wSefsEr0yXA/T859ChS0y.xN6339lfExoQYVPxLRosV6fkX/Q9M5a','2019-02-17 00:00:00','gilbertoPublico','5577750820',100,'MINUTES',30);

  
-- DATOS USUARIOS ADMINISTRADORES  -- PASSWORD ES: gilberto
INSERT INTO usuario_administrador (id,apellido1,apellido2,correo,enabled,fecha_alta,nombre,password,ultima_actualizacion,username) VALUES (1,'Colin','Velasco','jose.gilberto.colin@outlook.com.mx',1,'2019-02-17 00:00:00','José Gilberto','$2a$10$wSefsEr0yXA/T859ChS0y.xN6339lfExoQYVPxLRosV6fkX/Q9M5a','2019-02-17 00:00:00','gilbertoAdmin');
 

-- DATOS OAUTH CLIENT DETAILS -- PASSWORD ES: 123

--INSERT INTO oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove) VALUES 
--('mobile', 'mobilesourceid', '$2a$10$u/tfnwocKdzo8jplduK3BeWJcQ6rINhjnXLSomFWMX6KiltJqr3tO', 'read,write', 'password', 'http://localhost', NULL, 0, 0, NULL, 'false');
  
--INSERT INTO oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove) VALUES 
--('administradores', 'administradoressourceid', '$2a$10$u/tfnwocKdzo8jplduK3BeWJcQ6rINhjnXLSomFWMX6KiltJqr3tO', 'read,write', 'password', 'http://localhost', NULL, 3000, 0, NULL, 'false');

--INSERT INTO oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove) VALUES 
--('publicos', 'publicossourceid', '$2a$10$u/tfnwocKdzo8jplduK3BeWJcQ6rINhjnXLSomFWMX6KiltJqr3tO', 'read,write', 'password', 'http://localhost', NULL, 6000 , 0, NULL, 'false');

INSERT INTO oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove) VALUES 
('stegerilumination', 'stegerilumination_resource', '$2a$10$u/tfnwocKdzo8jplduK3BeWJcQ6rINhjnXLSomFWMX6KiltJqr3tO', 'read,write', 'password', 'http://localhost', NULL, 6000 , 0, NULL, 'false');


-- PERMISOS -- AUTORITIES
insert into permiso_administrador values (1,'swagger admin' , 'proyecto:web:swagger:admin');
insert into usuario_administrador_permiso values (1,1);


-- LOGGER
INSERT INTO log (id,accion,apartado,fecha_alta,tipo_usuario,usuario) VALUES (1,'Alta de la plantilla','Sistema',now(),'publico','gilbertoAdmin');






