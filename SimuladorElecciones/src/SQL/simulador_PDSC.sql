-- -----------------------------------------------------
-- Drop de tablas
-- -----------------------------------------------------
DROP TRIGGER usuarioEleccionTrigger;
DROP TABLE UsuarioEleccionMap;
DROP TABLE Usuario;
DROP TABLE Voto;
DROP TABLE Circunscripcion;
DROP TABLE Candidatura;
DROP TABLE Eleccion;
DROP TABLE TipoEleccion;


-- -----------------------------------------------------
-- Creacion de tablas
-- -----------------------------------------------------
CREATE TABLE TipoEleccion (
    id                      INTEGER not null,
    nombre                  VARCHAR(20) not null,
    PRIMARY KEY (id),
    UNIQUE (nombre)
);

CREATE TABLE Eleccion (
    id                      INTEGER not null,
    fecha                   DATE not null,
    tipo_eleccion           INTEGER,
    PRIMARY KEY (id)
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
    id                      INTEGER not null,
    nombre                  VARCHAR(20) not null,
    correo_electronico      VARCHAR(30) not null,
    clave                   VARCHAR(20) not null,
    PRIMARY KEY (id),
    UNIQUE(nombre),
    UNIQUE(correo_electronico)
);

CREATE TABLE UsuarioEleccionMap (
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
CREATE TRIGGER usuarioEleccionTrigger BEFORE DELETE ON UsuarioEleccionMap
    FOR EACH ROW BEGIN
        DELETE
        FROM Eleccion
        WHERE id = OLD.id_eleccion
            AND 1 >= ( SELECT COUNT(*)
                        FROM UsuarioEleccionMap M
                        WHERE M.id_eleccion = OLD.id_eleccion );
    END; //
DELIMITER;

-- -----------------------------------------------------
-- Inserts de tablas
-- -----------------------------------------------------
INSERT INTO TipoEleccion(id, nombre) VALUES (1, "CongresoDiputados");
INSERT INTO TipoEleccion(id, nombre) VALUES (2, "Autonomicas");
INSERT INTO TipoEleccion(id, nombre) VALUES (3, "Municipales");
INSERT INTO TipoEleccion(id, nombre) VALUES (4, "ParlamentoEuropeo");
