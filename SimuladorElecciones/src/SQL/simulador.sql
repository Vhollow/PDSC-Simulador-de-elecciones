-- -----------------------------------------------------
-- Script SQL para la base de datos del simulador
-- Autor: Daniel González Alonso
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Drop de tablas
-- -----------------------------------------------------
DROP TRIGGER Usuario_Eleccion_Trigger;
DROP TABLE Usuario_Eleccion_Map;
DROP TABLE Usuario;
DROP TABLE Voto;
DROP TABLE Circunscripcion;
DROP TABLE Candidatura;
DROP TABLE Eleccion;
DROP TABLE Tipo_Eleccion;


-- -----------------------------------------------------
-- Creacion de tablas
-- -----------------------------------------------------
CREATE TABLE Tipo_Eleccion (
    id                      INTEGER not null,
    nombre                  VARCHAR(20) not null,
    PRIMARY KEY (id),
    UNIQUE (nombre)
);

CREATE TABLE Eleccion (
    id                      INTEGER not null AUTO_INCREMENT,
    fecha                   DATE not null,
    tipo_eleccion           INTEGER,
    PRIMARY KEY (id),
    FOREIGN KEY (tipo_eleccion)
        REFERENCES Tipo_Eleccion(id)
            ON DELETE CASCADE
);

CREATE TABLE Candidatura (
    id_eleccion             INTEGER not null,
    nombre_corto            VARCHAR(5) not null,
    nombre_largo            VARCHAR(20) not null,
    color                   INTEGER,
    PRIMARY KEY (id_eleccion, nombre_corto),
    FOREIGN KEY (id_eleccion)
        REFERENCES Eleccion(id)
            ON DELETE CASCADE
);

CREATE TABLE Circunscripcion (
    id_eleccion             INTEGER not null,
    nombre                  VARCHAR(50) not null,
    numero_representantes   INTEGER,
    voto_nulo               INTEGER,
    voto_en_blanco          INTEGER,
    abstencion              INTEGER,
    minimo_representacion   INTEGER,
    PRIMARY KEY (id_eleccion, nombre),
    FOREIGN KEY (id_eleccion)
        REFERENCES Eleccion(id)
            ON DELETE CASCADE
);

CREATE TABLE Voto (
    id_eleccion             INTEGER not null,
    nombre_candidatura      VARCHAR(5) not null,
    nombre_circunscripcion  VARCHAR(50) not null,
    conteo                  INTEGER,
    PRIMARY KEY (id_eleccion, nombre_candidatura, nombre_circunscripcion),
    FOREIGN KEY (id_eleccion)
        REFERENCES Eleccion(id)
            ON DELETE CASCADE,
    FOREIGN KEY (id_eleccion, nombre_circunscripcion)
        REFERENCES Candidatura(id_eleccion, nombre_corto)
            ON DELETE CASCADE,
    FOREIGN KEY (id_eleccion, nombre_circunscripcion)
        REFERENCES Circunscripcion(id_eleccion, nombre)
            ON DELETE CASCADE
);

CREATE TABLE Usuario (
    id                      INTEGER not null AUTO_INCREMENT,
    nombre                  VARCHAR(20) not null,
    correo_electronico      VARCHAR(30) not null,
    clave                   VARCHAR(20) not null,
    PRIMARY KEY (id),
    UNIQUE(nombre),
    UNIQUE(correo_electronico)
);

CREATE TABLE Usuario_Eleccion_Map (
    id_usuario              INTEGER not null,
    id_eleccion             INTEGER not null,
    nuevo                   BIT,
    PRIMARY KEY (id_usuario, id_eleccion),
    FOREIGN KEY (id_usuario)
        REFERENCES Usuario(id)
            ON DELETE CASCADE,
    FOREIGN KEY (id_eleccion)
        REFERENCES Eleccion(id)
            ON DELETE CASCADE
);

-- -----------------------------------------------------
-- Triggers
-- -----------------------------------------------------
DELIMITER //
CREATE TRIGGER Usuario_Eleccion_Trigger BEFORE DELETE ON Usuario_Eleccion_Map
    FOR EACH ROW BEGIN
        DELETE
        FROM Eleccion
        WHERE id = OLD.id_eleccion
            AND 1 >= ( SELECT COUNT(*)
                        FROM Usuario_Eleccion_Map M
                        WHERE M.id_eleccion = OLD.id_eleccion );
    END //
DELIMITER ;

-- -----------------------------------------------------
-- Inserts de tablas
-- -----------------------------------------------------
INSERT INTO Tipo_Eleccion(id, nombre) VALUES (1, "Congreso Diputados");
INSERT INTO Tipo_Eleccion(id, nombre) VALUES (2, "Autonomicas");
INSERT INTO Tipo_Eleccion(id, nombre) VALUES (3, "Municipales");
INSERT INTO Tipo_Eleccion(id, nombre) VALUES (4, "Parlamento Europeo");
