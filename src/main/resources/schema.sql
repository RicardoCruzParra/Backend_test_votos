CREATE TABLE IF NOT EXISTS receta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    ingredientes VARCHAR(500),
    instrucciones VARCHAR(1000),
    tiempo_preparacion INT CHECK (tiempo_preparacion > 0),
    dificultad VARCHAR(50),
    participante VARCHAR(100),
    votos INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS voto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    receta_id BIGINT,
    valor INT NOT NULL CHECK (valor IN (1, -2)),
    CONSTRAINT fk_receta FOREIGN KEY (receta_id) REFERENCES receta(id) ON DELETE CASCADE
);
