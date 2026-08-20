CREATE DATABASE stock_management;

\c stock_management;

CREATE TABLE products (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    unit_price NUMERIC(12, 2) NOT NULL,
    CONSTRAINT chk_product_id_not_empty CHECK (LENGTH(TRIM(id)) > 0),
    CONSTRAINT chk_product_name_not_empty CHECK (LENGTH(TRIM(name)) > 0),
    CONSTRAINT chk_product_unit_price_positive CHECK (unit_price >= 0)
);

CREATE TABLE stock_movements (
    id VARCHAR(36) PRIMARY KEY,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    movement_type VARCHAR(3) NOT NULL CHECK (movement_type IN ('IN', 'OUT')),
    quantity INTEGER NOT NULL,
    product_id VARCHAR(36) NOT NULL,
    CONSTRAINT chk_stock_movement_id_not_empty CHECK (LENGTH(TRIM(id)) > 0),
    CONSTRAINT chk_stock_movement_quantity_positive CHECK (quantity > 0),
    CONSTRAINT fk_stock_movement_product FOREIGN KEY (product_id) REFERENCES products(id) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE INDEX idx_stock_movements_product_id ON stock_movements(product_id);
CREATE INDEX idx_stock_movements_movement_type ON stock_movements(movement_type);
CREATE INDEX idx_stock_movements_created_at ON stock_movements(created_at);