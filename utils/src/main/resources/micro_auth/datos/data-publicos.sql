
-- PASSWORD ES: gilberto
INSERT INTO usuario_publico (id,apellido1,apellido2,correo,enabled,fecha_alta,nombre,password,ultima_actualizacion,username,limit_request, time_unit_token, time_unit_request, token_expiration) 
VALUES  (1,'Colin','Velasco','jose.gilberto.colin1@outlook.com',true,'2019-02-17 00:00:00','José Gilberto','$2a$10$wSefsEr0yXA/T859ChS0y.xN6339lfExoQYVPxLRosV6fkX/Q9M5a','2019-02-17 00:00:00',
'gilbertoPublico', 10 ,'MINUTES' , 'MINUTES',60 );

-- PERMISOS PUBLICOS -- AUTORITIES
DELETE FROM usuario_publico_permiso_publico;
DELETE FROM permiso_publico;
 
-- DASHBOARD
INSERT INTO permiso_publico values ( 1, 'Muestra la sección de DASHBBOARD' , 'web:publico:dashboard:mostrar' );
 
-- PERMISOS PUBLICOS --- USUARIOS PUBLICOS
insert into usuario_publico_permiso_publico values (1,1); 
 
