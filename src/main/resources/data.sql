-- Insertamos algunas recetas de ejemplo
INSERT INTO receta (nombre, descripcion, ingredientes, instrucciones, tiempo_preparacion, dificultad, participante, votos)
VALUES
('Tacos', 'Deliciosos tacos de carne con salsa', 'Carne, Tortillas, Cebolla, Cilantro, Salsa', 'Asar la carne, calentar tortillas, agregar ingredientes', 20, 'baja', 'Chef Juan', 0),
('Paella', 'Paella con mariscos y azafrán', 'Arroz, Mariscos, Azafrán, Caldo de pescado', 'Cocinar el arroz y los mariscos juntos', 60, 'media', 'Chef María', 0),
('Pizza Margarita', 'Pizza clásica con tomate y albahaca', 'Harina, Tomate, Queso, Albahaca', 'Preparar la masa, agregar ingredientes, hornear', 40, 'baja', 'Chef Pedro', 0);

-- Insertamos votos para las recetas
INSERT INTO voto (receta_id, valor) VALUES (1, 1); -- Voto positivo para Tacos
INSERT INTO voto (receta_id, valor) VALUES (2, -2); -- Voto negativo para Paella
INSERT INTO voto (receta_id, valor) VALUES (3, 1); -- Voto positivo para Pizza Margarita
