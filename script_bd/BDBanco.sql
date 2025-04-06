CREATE DATABASE IF NOT EXISTS Banco;
USE Banco;

CREATE TABLE Provincias (
idProvincia_P CHAR(4),
nombreProvincia VARCHAR(30) NOT NULL,
CONSTRAINT PK_Provincias PRIMARY KEY (idProvincia_P)
);

CREATE TABLE Localidades (
idLocalidad_L CHAR(4),
idProvincia_L CHAR(4) NOT NULL,
nombreLocalidad VARCHAR(30) NOT NULL,
CONSTRAINT PK_Localidades PRIMARY KEY (idLocalidad_L),
CONSTRAINT FK_Localidades_Provincias FOREIGN KEY (idProvincia_L) REFERENCES Provincias (idProvincia_P)
);

CREATE TABLE tiposDeMovimientos (
idTipoMovimiento INT AUTO_INCREMENT,
nombre VARCHAR(40) NOT NULL,
CONSTRAINT PK_tiposDeMovimientos PRIMARY KEY (idTipoMovimiento)
);

CREATE TABLE tiposDeCuentas (
idTipoCuenta INT AUTO_INCREMENT,
nombreCuenta VARCHAR(40) NOT NULL,
CONSTRAINT PK_tiposDeCuentas PRIMARY KEY (idTipoCuenta)
);

CREATE TABLE Usuarios (
idUsuario INT AUTO_INCREMENT,
nombreUsuario VARCHAR(20) NOT NULL,
contraseña VARCHAR(20) NOT NULL,
tipoUsuario ENUM('administrador', 'cliente') NOT NULL DEFAULT 'cliente',
estado BOOLEAN NOT NULL DEFAULT 1,
CONSTRAINT PK_Usuarios PRIMARY KEY (idUsuario)
);


CREATE TABLE Clientes (
dni CHAR(8) NOT NULL,
cuil CHAR(11) UNIQUE NOT NULL,
nombre VARCHAR(20) NOT NULL,
apellido VARCHAR(30) NOT NULL,
sexo VARCHAR(10),
nacionalidad VARCHAR(20),
fechaNacimiento DATE,
direccion VARCHAR(50),
idLocalidad_C CHAR(4),
correo VARCHAR(50) UNIQUE,
telefono VARCHAR(15),
idUsuario_C INT NOT NULL,
contraseña VARCHAR(20) NOT NULL,
estado BOOLEAN NOT NULL DEFAULT 1,
CONSTRAINT PK_Clientes PRIMARY KEY (dni),
CONSTRAINT FK_Clientes_Localidades FOREIGN KEY (idLocalidad_C) REFERENCES Localidades (idLocalidad_L),
CONSTRAINT FK_Clientes_Usuarios FOREIGN KEY (idUsuario_C) REFERENCES Usuarios (idUsuario)
);

CREATE TABLE Cuentas (
nroCuenta INT NOT NULL,
dni_Cu CHAR(8),
fechaCreacion DATE NOT NULL,
idTipoCuenta_Cu INT,
cbu char(22) UNIQUE NOT NULL,
saldo DECIMAL(15, 2) DEFAULT 0,
estado BOOLEAN NOT NULL DEFAULT 1,
CONSTRAINT PK_Cuentas PRIMARY KEY (nroCuenta),
CONSTRAINT FK_Cuentas_Clientes FOREIGN KEY (dni_Cu) REFERENCES Clientes(dni),
CONSTRAINT FK_Cuentas_tiposDeCuentas FOREIGN KEY (idTipoCuenta_Cu) REFERENCES  tiposDeCuentas(idTipoCuenta)
);

CREATE TABLE Prestamos (
idPrestamo INT AUTO_INCREMENT,
dni_Pr CHAR(8),
nroCuenta_Pr INT NOT NULL,
fechaPrestamo DATE NOT NULL,
importeConIntereses DECIMAL(15, 2) NOT NULL,
importePedidoCliente DECIMAL(15, 2) NOT NULL,
plazoPago INT NOT NULL,
cantidadCuotas INT NOT NULL,
montoPorMes DECIMAL(15, 2) NOT NULL,
estado ENUM('pendiente', 'aprobado', 'rechazado') NOT NULL,
CONSTRAINT PK_Prestamos PRIMARY KEY (idPrestamo),
CONSTRAINT FK_Prestamos_Cuentas FOREIGN KEY (nroCuenta_Pr) REFERENCES Cuentas (nroCuenta),
CONSTRAINT FK_Prestamos_Clientes FOREIGN KEY (dni_Pr) REFERENCES Clientes(dni)
);

CREATE TABLE Cuotas (
idCuota INT AUTO_INCREMENT,
idPrestamo INT,
numeroCuota INT NOT NULL,
fechaVencimiento DATE NOT NULL,
monto DECIMAL(15, 2) NOT NULL,
fechaPago Date NULL,
estado ENUM('pendiente', 'cobrado') NOT NULL,
CONSTRAINT PK_Cuotas PRIMARY KEY (idCuota),
CONSTRAINT FK_Cuotas_Prestamos FOREIGN KEY (idPrestamo) REFERENCES Prestamos(idPrestamo)
);

CREATE TABLE Movimientos (
idMovimiento INT AUTO_INCREMENT,
nroCuenta_M INT,
fecha DATE NOT NULL,
detalle VARCHAR(50),
importe DECIMAL(15, 2) NOT NULL,
idTipoMovimiento_M INT,
CONSTRAINT PK_Movimientos PRIMARY KEY (idMovimiento),
CONSTRAINT FK_Movimientos_Cuentas FOREIGN KEY (nroCuenta_M) REFERENCES Cuentas(nroCuenta),
CONSTRAINT FK_Movimientos_tiposDeMovimiento FOREIGN KEY (idTipoMovimiento_M) REFERENCES tiposDeMovimientos (idTipoMovimiento)
);

CREATE TABLE Tranferencias (
idTransferencia INT AUTO_INCREMENT,
nroCuentaOrigen INT NOT NULL ,
CbuDestino  char(22) NOT NULL,
monto DECIMAL(15, 2) NOT NULL,
fecha DATE NOT NULL,
CONSTRAINT PK_Tranferencias PRIMARY KEY (idTransferencia),
CONSTRAINT FK_Transferencias_CuentaOrigen FOREIGN KEY (nroCuentaOrigen) REFERENCES Cuentas(nroCuenta),
CONSTRAINT FK_Transferencias_CuentaDestino FOREIGN KEY (CbuDestino) REFERENCES Cuentas(cbu)
);

										-- INSERTAR
-- Provincias
INSERT INTO Provincias (idProvincia_P, nombreProvincia) VALUES
('P001', 'Buenos Aires'), ('P002', 'Córdoba'), ('P003', 'Santa Fe'), 
('P004', 'Mendoza'), ('P005', 'Salta'),('P006', 'Tucumán'),
('P007', 'Entre Ríos'), ('P008', 'Misiones'), ('P009', 'Corrientes'), 
('P010', 'San Luis'),('P011', 'San Juan'), ('P012', 'La Rioja'), 
('P013', 'Catamarca'), ('P014', 'Neuquén'), ('P015', 'Chubut'),
('P016', 'Formosa'), ('P017', 'Jujuy'),  ('P018', 'La Pampa');


-- Localidades
INSERT INTO Localidades (idLocalidad_L, idProvincia_L, nombreLocalidad) VALUES
-- Buenos Aires
('L001', 'P001', 'La Plata'), ('L002', 'P001', 'Mar del Plata'), ('L003', 'P001', 'Bahía Blanca'), 
-- Córdoba
('L004', 'P002', 'Villa Carlos Paz'), ('L005', 'P002', 'Córdoba Capital'), ('L006', 'P002', 'Río Cuarto'), 
-- Santa Fe
('L007', 'P003', 'Rosario'), ('L008', 'P003', 'Santa Fe Capital'), ('L009', 'P003', 'Rafaela'), 
-- Mendoza
('L010', 'P004', 'Godoy Cruz'), ('L011', 'P004', 'Mendoza Capital'), ('L012', 'P004', 'San Rafael'), 
-- Salta
('L013', 'P005', 'Salta Capital'), ('L014', 'P005', 'Cafayate'), ('L015', 'P005', 'Orán'), 
-- Tucumán
('L016', 'P006', 'San Miguel de Tucumán'), ('L017', 'P006', 'Tafí Viejo'), ('L018', 'P006', 'Concepción'), 
-- Entre Ríos
('L019', 'P007', 'Paraná'), ('L020', 'P007', 'Gualeguaychú'), ('L021', 'P007', 'Concordia'), 
-- Misiones
('L022', 'P008', 'Posadas'), ('L023', 'P008', 'Oberá'), ('L024', 'P008', 'Puerto Iguazú'), 
-- Corrientes
('L025', 'P009', 'Corrientes Capital'), ('L026', 'P009', 'Goya'), ('L027', 'P009', 'Paso de los Libres'), 
-- San Luis
('L028', 'P010', 'San Luis Capital'), ('L029', 'P010', 'Villa Mercedes'), ('L030', 'P010', 'Merlo'), 
-- San Juan
('L031', 'P011', 'San Juan Capital'), ('L032', 'P011', 'Rawson'), ('L033', 'P011', 'Chimbas'), 
-- La Rioja
('L034', 'P012', 'La Rioja Capital'), ('L035', 'P012', 'Chilecito'), ('L036', 'P012', 'Aimogasta'), 
-- Catamarca
('L037', 'P013', 'Catamarca Capital'), ('L038', 'P013', 'Belén'), ('L039', 'P013', 'Tinogasta'), 
-- Neuquén
('L040', 'P014', 'Neuquén Capital'), ('L041', 'P014', 'San Martín de los Andes'), ('L042', 'P014', 'Zapala'), 
-- Chubut
('L043', 'P015', 'Rawson'), ('L044', 'P015', 'Puerto Madryn'), ('L045', 'P015', 'Trelew'), 
-- Formosa
('L046', 'P016', 'Formosa Capital'), ('L047', 'P016', 'Clorinda'), ('L048', 'P016', 'Pirané'), 
-- Jujuy
('L049', 'P017', 'San Salvador de Jujuy'), ('L050', 'P017', 'Palpalá'), ('L051', 'P017', 'Humahuaca'), 
-- La Pampa
('L052', 'P018', 'Santa Rosa'), ('L053', 'P018', 'General Pico'), ('L054', 'P018', 'Toay');


-- Tipos de Movimientos
INSERT INTO tiposDeMovimientos (nombre) VALUES
('Depósito'), ('Extracción'),('Alta Préstamo'),('Pago de Préstamo'), 
('Apertura de Cuenta');


-- Tipos de Cuentas
INSERT INTO tiposDeCuentas (nombreCuenta) VALUES
('Caja de Ahorro'),('Cuenta Corriente');


-- Usuarios
INSERT INTO Usuarios (nombreUsuario, contraseña, tipoUsuario) VALUES
('admin1', 'adminpass1', 'administrador'), ('admin2', 'adminpass2', 'administrador'),
('admin3', 'adminpass3', 'administrador'), ('admin4', 'adminpass4', 'administrador'),
('admin5', 'adminpass5', 'administrador'), ('admin6', 'adminpass6', 'administrador'),

('cliente1', 'clipass1', 'cliente'), ('cliente2', 'clipass2', 'cliente'),
('cliente3', 'clipass3', 'cliente'), ('cliente4', 'clipass4', 'cliente'),
('cliente5', 'clipass5', 'cliente'), ('cliente6', 'clipass6', 'cliente'),
('cliente7', 'clipass7', 'cliente'), ('cliente8', 'clipass8', 'cliente'),
('cliente9', 'clipass9', 'cliente'), ('cliente10', 'clipass10', 'cliente'),
('cliente11', 'clipass11', 'cliente'), ('cliente12', 'clipass12', 'cliente');


-- Clientes
INSERT INTO Clientes (dni, cuil, nombre, apellido, sexo, nacionalidad, fechaNacimiento, direccion, idLocalidad_C, correo, telefono, idUsuario_C, contraseña) VALUES
('30789101', '17307891017', 'Gabriel', 'Molina', 'Otro', 'Argentina', '1991-12-02', 'Calle Rivadavia 321', 'L019', 'gabriel.molina@gmail.com', '1155990011', 7, 'clipass1'),
('30891012', '18308910128', 'Sofía', 'Ramírez', 'Mujer', 'Argentina', '1993-06-27', 'Boulevard Oroño 654', 'L023', 'sofia.ramirez@gmail.com', '1166001122', 8, 'clipass2'),
('30910123', '19309101239', 'Julián', 'Álvarez', 'Hombre', 'Argentina', '1989-11-11', 'Calle Santa Fe 789', 'L027', 'julian.alvarez@gmail.com', '1166112233', 9, 'clipass3'),
('31001234', '20310012340', 'Martina', 'Vega', 'Mujer', 'Argentina', '1994-04-05', 'Calle Mendoza 456', 'L028', 'martina.vega@gmail.com', '1177223344', 10, 'clipass4'),

('31012345', '21310123451', 'Agustín', 'Sánchez', 'Hombre', 'Argentina', '1997-02-18', 'Calle Corrientes 789', 'L032', 'agustin.sanchez@gmail.com', '1188334455', 11, 'clipass5'),
('31023456', '22310234562', 'Micaela', 'Ríos', 'Mujer', 'Argentina', '1992-12-22', 'Av. Callao 333', 'L036', 'micaela.rios@gmail.com', '1188445566', 12, 'clipass6'),
('31034567', '23310345673', 'Leandro', 'Cáceres', 'Otro', 'Argentina', '1996-07-10', 'Calle Güemes 321', 'L037', 'leandro.caceres@gmail.com', '1199556677', 13, 'clipass7'),
('31045678', '24310456784', 'Camila', 'Torres', 'Mujer', 'Argentina', '1995-01-25', 'Calle Maipú 101', 'L041', 'camila.torres@gmail.com', '1199667788', 14, 'clipass8'),
('31056789', '25310567895', 'Ezequiel', 'Castro', 'Hombre', 'Argentina', '1993-03-05', 'Av. Libertador 1234', 'L045', 'ezequiel.castro@gmail.com', '1100778899', 15, 'clipass9'),

('31067891', '26310678916', 'Valeria', 'Nuñez', 'Mujer', 'Argentina', '1991-10-09', 'Calle Chacabuco 234', 'L046', 'valeria.nunez@gmail.com', '1100889900', 16, 'clipass10'),
('31078910', '27310789107', 'Maximiliano', 'Ortiz', 'Hombre', 'Argentina', '1999-01-01', 'Av. Roca 555', 'L050', 'maximiliano.ortiz@gmail.com', '1100990011', 17, 'clipass11'),
('31089101', '28310891018', 'Florencia', 'Paz', 'Otro', 'Argentina', '1988-06-18', 'Calle Urquiza 456', 'L054', 'florencia.paz@gmail.com', '1111001122', 18, 'clipass12');


INSERT INTO Cuentas (nroCuenta, dni_Cu, fechaCreacion, idTipoCuenta_Cu, cbu, saldo, estado) VALUES 
(10004, '30789101', '2024-06-06', 1, '0000000000006789101232', 0, 1),
(10005, '30789101', '2024-06-06', 1, '0000000000006789101231', 60000.00, 1),
(10007, '30789101', '2024-07-07', 1, '0000000000007891012345', 70000.00, 1),
(10008, '30891012', '2024-08-08', 2, '0000000000008910123456', 80000.00, 1),
(10009, '30910123', '2024-09-09', 2, '0000000000009101234567', 10000.00, 1),
(10010, '31001234', '2024-10-10', 2, '0000000000001012345678', 11000.00, 1),

(10011, '31012345', '2024-11-11', 1, '0000000000001023456789', 22000.00, 1),
(10012, '31023456', '2024-12-12', 2, '0000000000001034567890', 33000.00, 1),
(10013, '31034567', '2024-01-13', 1, '0000000000001045678901', 44000.00, 1),
(10014, '31045678', '2024-02-14', 2, '0000000000001056789012', 55000.00, 1),
(10015, '31056789', '2024-03-15', 1, '0000000000001067890123', 66000.00, 1),

(10016, '31067891', '2024-04-16', 2, '0000000000001078901234', 77000.00, 1),
(10017, '31078910', '2024-05-17', 1, '0000000000001089012345', 88000.00, 1),
(10018, '31089101', '2024-06-18', 2, '0000000000001090123456', 99000.00, 1),
(10019, '30891012', '2024-08-08', 2, '0000000000008910123459', 80000.00, 0);


INSERT INTO Prestamos (dni_Pr, nroCuenta_Pr, fechaPrestamo, importeConIntereses, importePedidoCliente, plazoPago, cantidadCuotas, montoPorMes, estado) VALUES
('30789101', 10007, '2024-07-21', 84000.00, 70000.00, 24, 24, 3500.00, 'pendiente'),
('30891012', 10008, '2024-08-22', 99000.00, 80000.00, 36, 36, 2750.00, 'rechazado'),
('30910123', 10009, '2024-09-23', 120000.00, 90000.00, 6, 6, 20000, 'aprobado'),
('31001234', 10010, '2024-10-24', 10000.00, 1000.00, 60, 60, 166.67, 'pendiente'),
('31012345', 10011, '2024-11-25', 18000.00, 2000.00, 12, 12, 1500.00, 'aprobado'),
('31023456', 10012, '2024-12-26', 48000.00, 3000.00, 24, 24, 2000.00, 'pendiente'),
('31034567', 10013, '2024-01-27', 36000.00, 4000.00, 36, 36, 1000.00, 'rechazado'),
('31045678', 10014, '2024-02-28', 45000.00, 5000.00, 48, 48, 937.50, 'aprobado'),
('31056789', 10015, '2024-03-29', 60000.00, 50000.00, 60, 60, 1000.00, 'pendiente'),
('31067891', 10016, '2024-04-01', 108000.00, 6000.00, 36, 36, 3000.00, 'rechazado'),
('31078910', 10017, '2024-05-02', 96000.00, 7000.00, 48, 48, 2000.00, 'aprobado'),
('31089101', 10018, '2024-06-03', 180000.00, 8000.00, 60, 60, 3000.00, 'pendiente');


INSERT INTO Cuotas (idPrestamo, numeroCuota, fechaVencimiento, monto, estado) VALUES 
(3, 1, '2024-04-17', 20000, 'pendiente'),
(3, 2, '2024-05-15', 20000, 'pendiente'),
(3, 3, '2024-06-15', 20000, 'pendiente'),
(3, 4, '2024-07-15', 20000, 'pendiente'),
(3, 5, '2024-08-15', 20000, 'pendiente'),
(3, 6, '2024-09-15', 20000, 'pendiente'),
(5, 1, '2024-09-15', 1500.00, 'pendiente'),
(5, 2, '2024-10-15', 1500.00, 'pendiente'),
(5, 3, '2024-11-15', 1500.00, 'pendiente'),
(5, 4, '2024-12-15', 1500.00, 'pendiente'),
(5, 5, '2025-01-15', 1500.00, 'pendiente'),
(5, 6, '2025-02-15', 1500.00, 'pendiente'),
(5, 7, '2025-03-15', 1500.00, 'pendiente'),
(5, 8, '2025-04-15', 1500.00, 'pendiente'),
(5, 9, '2025-05-15', 1500.00, 'pendiente'),
(5, 10, '2025-06-15', 1500.00, 'pendiente'),
(5, 11, '2025-07-15', 1500.00, 'pendiente'),
(5, 12, '202-08-15', 1500.00, 'pendiente');


INSERT INTO Movimientos (nroCuenta_M, fecha, detalle, importe, idTipoMovimiento_M) VALUES 
(10004, '2024-04-08', 'Deposito de dinero', 1000.00, 1),
(10005, '2024-05-09', 'Pago de préstamo', 500.00, 5),

(10007, '2024-07-11', 'Retiro de efectivo', 1500.00, 2),
(10008, '2024-08-12', 'Alta de un préstamo', 2000.00, 3),
(10009, '2024-09-13', 'Deposito de dinero', 1000.00, 1),
(10010, '2024-10-14', 'Pago de préstamo', 500.00, 4),

(10011, '2024-11-15', 'Alta de una cuenta', 5000.00, 5),
(10012, '2024-12-16', 'Retiro de efectivo', 1500.00, 2),
(10013, '2024-01-17', 'Alta de un préstamo', 2000.00, 3),
(10014, '2024-02-18', 'Deposito de dinero', 1000.00, 1),
(10015, '2024-03-19', 'Pago de préstamo', 500.00, 4),

(10016, '2024-04-20', 'Deposito de dinero', 2000.00, 1),
(10017, '2024-05-21', 'Retiro de efectivo', 1000.00, 2),
(10018, '2024-06-22', 'Pago de préstamo', 500.00, 4);


INSERT INTO Tranferencias (nroCuentaOrigen, CbuDestino, monto, fecha) VALUES 
(10004, '0000000000006789101232', 4000.00, '2024-04-13'),
(10005, '0000000000006789101231', 5000.00, '2024-05-14'),
(10007, '0000000000007891012345', 7000.00, '2024-04-16'),
(10008, '0000000000008910123456', 8000.00, '2024-05-17'),
(10009, '0000000000009101234567', 9000.00, '2024-06-18'),
(10010, '0000000000001012345678', 1000.00, '2024-07-19'),
(10011, '0000000000001034567890', 2000.00, '2024-08-20'),
(10012, '0000000000001023456789', 3000.00, '2024-09-21'),
(10013, '0000000000001056789012', 4000.00, '2024-10-22'),
(10014, '0000000000001045678901', 5000.00, '2024-11-23'),
(10015, '0000000000001078901234', 6000.00, '2024-12-24'),
(10016, '0000000000001067890123', 7000.00, '2024-01-22'),
(10017, '0000000000001089012345', 8000.00, '2024-02-23'),
(10018, '0000000000001090123456', 9000.00, '2024-03-24');


-- PARA VERIFICAR

SELECT * FROM Usuarios;
SELECT * FROM Clientes;
SELECT * FROM Cuentas;
SELECT * FROM Prestamos;
SELECT * FROM Cuotas;
SELECT * FROM Tranferencias;
SELECT * FROM Movimientos;
SELECT * FROM tiposDeMovimientos;

-- CONSULTAS DEL TP

SELECT c.dni, c.cuil, c.nombre, c.apellido, c.sexo, c.nacionalidad, c.fechaNacimiento, c.direccion, l.nombreLocalidad AS localidad, c.correo, 
c.telefono, u.idUsuario, u.nombreUsuario, u.contraseña, 
(SELECT COUNT(*) FROM Cuentas WHERE dni_Cu = c.dni AND estado = 1) AS cantidadCuentas 
FROM Clientes c 
INNER JOIN Localidades l ON c.idLocalidad_C = l.idLocalidad_L 
INNER JOIN Usuarios u ON c.idUsuario_C = u.idUsuario 
WHERE u.idUsuario = 3 AND c.estado = 1;

SELECT dni, cuil, nombre, apellido, sexo, nacionalidad,
fechaNacimiento, c.direccion, l.nombreLocalidad AS localidad, correo, 
telefono, idUsuario_C, u.nombreUsuario AS nombreUsuario, c.contraseña AS contraseña, 
(SELECT COUNT(*) FROM Cuentas WHERE dni_Cu = dni AND estado = 1) AS cantidadCuentas 
FROM Clientes c 
INNER JOIN Localidades l ON idLocalidad_C = idLocalidad_L 
INNER JOIN Usuarios u ON idUsuario_C = idUsuario 
WHERE c.estado = 1 AND nombreUsuario like 'cliente2' ;

SELECT COUNT(*) AS cantidad FROM Cuentas cu
INNER JOIN Clientes c ON c.dni = dni_Cu
INNER JOIN Usuarios u ON idUsuario_C = idUsuario
WHERE cu.estado = 1 AND nombreUsuario like 'cliente2';


-- Para eliminar la base de datos
USE Banco;
DROP DATABASE Banco;