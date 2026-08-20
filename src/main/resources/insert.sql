INSERT INTO products (id, name, description, unitPrice) VALUES
('prod-001', 'Laptop Dell XPS 13', 'Ultrabook 13" Intel i7 16GB RAM', 1299.99),
('prod-002', 'Souris Logitech MX Master 3', 'Souris ergonomique sans fil', 99.90),
('prod-003', 'Clavier Mecanique Keychron K2', 'Clavier 75% switches Brown', 89.00);

INSERT INTO stockMovements (id, movementType, quantity, productId) VALUES
('mov-001', 'IN', 10, 'prod-001'),
('mov-002', 'OUT', 3, 'prod-001'),
('mov-003', 'IN', 25, 'prod-002'),
('mov-004', 'OUT', 5, 'prod-002'),
('mov-005', 'IN', 15, 'prod-003'),
('mov-006', 'OUT', 1, 'prod-003');