USE stockeate;
/*buzos*/
INSERT INTO producto_entity VALUES(1, '9WuQ4PnYcm', 'amarillo', 'https://i.imgur.com/x49RFJf.jpeg', true, 'buzo nike', 'xl');
INSERT INTO producto_entity VALUES(2, 'VPcNCFthT8', 'amarillo', 'https://i.imgur.com/AORQ5Qr.jpeg', true, 'buzo adidas', 'm');
INSERT INTO producto_entity VALUES(3, 'xOPBDW30DR', 'azul', 'https://i.imgur.com/tKiq8Ln.jpeg', true, 'buzo puma', 's');
INSERT INTO producto_entity VALUES(4, 'F76Jctbq6V', 'azul', 'https://i.imgur.com/m6SvWkV.jpeg', true, 'buzo reebok', 'xl');
INSERT INTO producto_entity VALUES(5, 'FXHGFPHBBE', 'blanco', 'https://i.imgur.com/zIYY6h0.jpeg', true, 'buzo nike', 'm');
/*pantalones*/
INSERT INTO producto_entity VALUES(6, 'R89luoiJcF', 'amarillo', 'https://i.imgur.com/gbt7XUc.jpeg', true, 'pantalon adidas', 's');
INSERT INTO producto_entity VALUES(7, 'bawDRJjsml', 'amarillo', 'https://i.imgur.com/tMfR5fo.jpeg', true, 'pantalon nike', 'xl');
INSERT INTO producto_entity VALUES(8, 'HXVxNlUp8z', 'azul', 'https://i.imgur.com/CYcwUNY.jpeg', true, 'pantalon puma', 'm');
INSERT INTO producto_entity VALUES(9, 'gqqpZpJZTq', 'azul', 'https://i.imgur.com/F67ma02.jpeg', true, 'pantalon reebok', 's');
/*remeras*/
INSERT INTO producto_entity VALUES(10, 'wjXhaP3hhN', 'amarillo', 'https://i.imgur.com/2IOgikL.jpeg', true, 'remera nike', 'm');
INSERT INTO producto_entity VALUES(11, '52jn8ilyUR', 'amarillo', 'https://i.imgur.com/2QkZm8B.jpeg', true, 'remera adidas', 's');
INSERT INTO producto_entity VALUES(12, 'ex2WHxAf2X', 'azul', 'https://i.imgur.com/y2eQ94V.jpeg', true, 'remera puma', 'xl');
INSERT INTO producto_entity VALUES(13, 'hI80Q73QUK', 'azul', 'https://i.imgur.com/SYc5T4D.jpeg', true, 'remera reebok', 'm');
/*zapatillas*/
INSERT INTO producto_entity VALUES(14, 'p0fdbNeMGy', 'amarillo', 'https://i.imgur.com/2wDl7g6.jpeg', true, 'zapatilla adidas', '42');
INSERT INTO producto_entity VALUES(15, 'CsxkCbH4Ic', 'amarillo', 'https://i.imgur.com/3duX7ez.jpeg', true, 'zapatilla nike', '41');
INSERT INTO producto_entity VALUES(16, 'Va3B4acelF', 'azul', 'https://i.imgur.com/2AaI7xj.jpeg', true, 'zapatilla puma', '43');
INSERT INTO producto_entity VALUES(17, '5vP2nChiq1', 'azul', 'https://i.imgur.com/IKTQSrL.jpeg', true, 'zapatilla reebok', '42');
/*tiendas*/
INSERT INTO tienda_entity VALUES(1, "Rosario", "400", "Avenida Guemes 1223", true, "Santa Fe");
INSERT INTO tienda_entity VALUES(2, "Cordoba", "600", "Calle Colon 2345", true, "Cordoba");
INSERT INTO tienda_entity VALUES(3, "Mendoza", "500", "Avenida San Martin 1342", true, "Mendoza");
INSERT INTO tienda_entity VALUES(4, "Buenos Aires", "700", "Calle Florida 123", true, "Buenos Aires");
INSERT INTO tienda_entity VALUES(5, "La Plata", "300", "Calle 13 456", true, "Buenos Aires");
INSERT INTO tienda_entity VALUES(6, "Mar del Plata", "350", "Avenida Independencia 567", true, "Buenos Aires");

/*stock para tienda rosario*/
INSERT INTO stock_entity VALUES(1, 50, 1, 1, 1);  -- Producto 1
INSERT INTO stock_entity VALUES(2, 45, 1, 2, 1);  -- Producto 2
INSERT INTO stock_entity VALUES(3, 30, 1, 3, 1);  -- Producto 3
INSERT INTO stock_entity VALUES(4, 25, 1, 4, 1);  -- Producto 4
INSERT INTO stock_entity VALUES(5, 60, 1, 5, 1);  -- Producto 5
INSERT INTO stock_entity VALUES(6, 40, 1, 6, 1);  -- Producto 6
INSERT INTO stock_entity VALUES(7, 55, 1, 7, 1);  -- Producto 7
INSERT INTO stock_entity VALUES(8, 35, 1, 8, 1);  -- Producto 8
INSERT INTO stock_entity VALUES(9, 45, 1, 9, 1);  -- Producto 9
INSERT INTO stock_entity VALUES(10, 30, 1, 10, 1);-- Producto 10
INSERT INTO stock_entity VALUES(11, 50, 1, 11, 1);-- Producto 11
INSERT INTO stock_entity VALUES(12, 55, 1, 12, 1);-- Producto 12
INSERT INTO stock_entity VALUES(13, 60, 1, 13, 1);-- Producto 13
INSERT INTO stock_entity VALUES(14, 20, 1, 14, 1);-- Producto 14
INSERT INTO stock_entity VALUES(15, 30, 1, 15, 1);-- Producto 15
INSERT INTO stock_entity VALUES(16, 40, 1, 16, 1);-- Producto 16
INSERT INTO stock_entity VALUES(17, 25, 1, 17, 1);-- Producto 17

/*stock para tienda Cordoba */
INSERT INTO stock_entity VALUES(18, 10, 1, 1, 2);  -- Producto 1
INSERT INTO stock_entity VALUES(19, 20, 1, 3, 2);  -- Producto 3
INSERT INTO stock_entity VALUES(20, 15, 1, 5, 2);  -- Producto 5
INSERT INTO stock_entity VALUES(21, 25, 1, 7, 2);  -- Producto 7
INSERT INTO stock_entity VALUES(22, 18, 1, 9, 2);  -- Producto 9
INSERT INTO stock_entity VALUES(23, 12, 1, 12, 2); -- Producto 12

/*stock para tienda Mendoza */
INSERT INTO stock_entity VALUES(24, 100, 1, 4, 3); -- Producto 4
INSERT INTO stock_entity VALUES(25, 80, 1, 6, 3);  -- Producto 6
INSERT INTO stock_entity VALUES(26, 70, 1, 14, 3); -- Producto 14

/*stock para tienda Buenos Aires */
INSERT INTO stock_entity VALUES(27, 60, 1, 2, 4);  -- Producto 2
INSERT INTO stock_entity VALUES(28, 45, 1, 4, 4);  -- Producto 4
INSERT INTO stock_entity VALUES(29, 55, 1, 6, 4);  -- Producto 6
INSERT INTO stock_entity VALUES(30, 65, 1, 10, 4); -- Producto 10
INSERT INTO stock_entity VALUES(31, 30, 1, 16, 4); -- Producto 16

/*stock para tienda La plata */
INSERT INTO stock_entity VALUES(32, 40, 1, 1, 5);  -- Producto 1
INSERT INTO stock_entity VALUES(33, 30, 1, 2, 5);  -- Producto 2
INSERT INTO stock_entity VALUES(34, 20, 1, 3, 5);  -- Producto 3
INSERT INTO stock_entity VALUES(35, 25, 1, 4, 5);  -- Producto 4
INSERT INTO stock_entity VALUES(36, 45, 1, 5, 5);  -- Producto 5
INSERT INTO stock_entity VALUES(37, 60, 1, 6, 5);  -- Producto 6
INSERT INTO stock_entity VALUES(38, 50, 1, 7, 5);  -- Producto 7
INSERT INTO stock_entity VALUES(39, 35, 1, 8, 5);  -- Producto 8
INSERT INTO stock_entity VALUES(40, 70, 1, 9, 5);  -- Producto 9
INSERT INTO stock_entity VALUES(41, 65, 1, 10, 5); -- Producto 10

/*stock para tienda mardel plata */
INSERT INTO stock_entity VALUES(49, 40, 1, 2, 6);  -- Producto 2
INSERT INTO stock_entity VALUES(50, 50, 1, 4, 6);  -- Producto 4
INSERT INTO stock_entity VALUES(51, 45, 1, 9, 6);  -- Producto 9
INSERT INTO stock_entity VALUES(52, 55, 1, 13, 6); -- Producto 13

/*Usuarios*/
INSERT INTO usuario_entity (apellido,contrasenia,habilitado,nombre,nombre_usuario,codigo_tienda,rol) VALUES('Admin', '$2a$10$.sSTLjDW1CsixKiR0GADau0lxwzXb3vT/NbFBOoK88DcZi4oNfIKS', true, 'Admin', 'Admin', null,'ADMIN');
INSERT INTO usuario_entity (apellido,contrasenia,habilitado,nombre,nombre_usuario,codigo_tienda,rol) VALUES('Usuario', '$2a$10$9PotIsyblAhDaHcOsqzEPOfYplkzeyX2UBF/jGcivIP1S3tqCzQV2', true, 'Usuario', 'Usuario', 400,'USUARIO');
INSERT INTO usuario_entity (apellido,contrasenia,habilitado,nombre,nombre_usuario,codigo_tienda,rol) VALUES('Wu', '$2a$10$9PotIsyblAhDaHcOsqzEPOfYplkzeyX2UBF/jGcivIP1S3tqCzQV2', true, 'Elisa', 'ElisaWu', 600,'USUARIO');
INSERT INTO usuario_entity (apellido,contrasenia,habilitado,nombre,nombre_usuario,codigo_tienda,rol) VALUES('Leal', '$2a$10$9PotIsyblAhDaHcOsqzEPOfYplkzeyX2UBF/jGcivIP1S3tqCzQV2', true, 'Lydia', 'Lydi4', 500,'USUARIO');
INSERT INTO usuario_entity (apellido,contrasenia,habilitado,nombre,nombre_usuario,codigo_tienda,rol) VALUES('Mir', '$2a$10$9PotIsyblAhDaHcOsqzEPOfYplkzeyX2UBF/jGcivIP1S3tqCzQV2', true, 'Guillermo', 'Guillermin', 700,'USUARIO');
INSERT INTO usuario_entity (apellido,contrasenia,habilitado,nombre,nombre_usuario,codigo_tienda,rol) VALUES('Barrero', '$2a$10$9PotIsyblAhDaHcOsqzEPOfYplkzeyX2UBF/jGcivIP1S3tqCzQV2', true, 'Norberto', 'Ba_Norber', 700,'USUARIO');
INSERT INTO usuario_entity (apellido,contrasenia,habilitado,nombre,nombre_usuario,codigo_tienda,rol) VALUES('Vico', '$2a$10$9PotIsyblAhDaHcOsqzEPOfYplkzeyX2UBF/jGcivIP1S3tqCzQV2', true, 'Paulina', 'Pauly034', 300,'USUARIO');
INSERT INTO usuario_entity (apellido,contrasenia,habilitado,nombre,nombre_usuario,codigo_tienda,rol) VALUES('Riquelme', '$2a$10$9PotIsyblAhDaHcOsqzEPOfYplkzeyX2UBF/jGcivIP1S3tqCzQV2', true, 'Sonia', 'SoniaBe21', 350,'USUARIO');
INSERT INTO usuario_entity (apellido,contrasenia,habilitado,nombre,nombre_usuario,codigo_tienda,rol) VALUES('Mari', '$2a$10$9PotIsyblAhDaHcOsqzEPOfYplkzeyX2UBF/jGcivIP1S3tqCzQV2', true, 'Antonio', 'Antony', 300,'USUARIO');
INSERT INTO usuario_entity (apellido,contrasenia,habilitado,nombre,nombre_usuario,codigo_tienda,rol) VALUES('Iñigo', '$2a$10$9PotIsyblAhDaHcOsqzEPOfYplkzeyX2UBF/jGcivIP1S3tqCzQV2', true, 'Elvira', 'Elvira', 300,'USUARIO');
INSERT INTO usuario_entity (apellido,contrasenia,habilitado,nombre,nombre_usuario,codigo_tienda,rol) VALUES('Picazo', '$2a$10$9PotIsyblAhDaHcOsqzEPOfYplkzeyX2UBF/jGcivIP1S3tqCzQV2', true, 'Daniela', 'Dany3p', 400,'USUARIO');
INSERT INTO usuario_entity (apellido,contrasenia,habilitado,nombre,nombre_usuario,codigo_tienda,rol) VALUES('Pelaez', '$2a$10$9PotIsyblAhDaHcOsqzEPOfYplkzeyX2UBF/jGcivIP1S3tqCzQV2', true, 'Luciano', 'Lucho', 350,'USUARIO');
INSERT INTO usuario_entity (apellido,contrasenia,habilitado,nombre,nombre_usuario,codigo_tienda,rol) VALUES('Jurado', '$2a$10$9PotIsyblAhDaHcOsqzEPOfYplkzeyX2UBF/jGcivIP1S3tqCzQV2', false, 'Fabiola', 'Fabiolis', 600,'USUARIO');
INSERT INTO usuario_entity (apellido,contrasenia,habilitado,nombre,nombre_usuario,codigo_tienda,rol) VALUES('Cuenca', '$2a$10$9PotIsyblAhDaHcOsqzEPOfYplkzeyX2UBF/jGcivIP1S3tqCzQV2', false, 'Juan', 'Juaner', 350,'USUARIO');
INSERT INTO usuario_entity (apellido,contrasenia,habilitado,nombre,nombre_usuario,codigo_tienda,rol) VALUES('Vegas', '$2a$10$9PotIsyblAhDaHcOsqzEPOfYplkzeyX2UBF/jGcivIP1S3tqCzQV2', false, 'Aaron', 'Vegaron', 300,'USUARIO');
INSERT INTO usuario_entity (apellido,contrasenia,habilitado,nombre,nombre_usuario,codigo_tienda,rol) VALUES('Villegas', '$2a$10$9PotIsyblAhDaHcOsqzEPOfYplkzeyX2UBF/jGcivIP1S3tqCzQV2', false, 'Ibai', 'VillaBai', 500,'USUARIO');
INSERT INTO usuario_entity (apellido,contrasenia,habilitado,nombre,nombre_usuario,codigo_tienda,rol) VALUES('Chaparro', '$2a$10$9PotIsyblAhDaHcOsqzEPOfYplkzeyX2UBF/jGcivIP1S3tqCzQV2', false, 'Erik', 'Erik_', 500,'USUARIO');


