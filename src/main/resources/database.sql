CREATE DATABASE stock_management;

\c stock_management;

CREATE TYPE "MovementType" AS ENUM ('IN', 'OUT');

CREATE TABLE products (
                          id          VARCHAR(36) PRIMARY KEY,
                          name        VARCHAR(255) NOT NULL,
                          description TEXT,
                          unitPrice   NUMERIC(12, 2) NOT NULL,
                          CONSTRAINT chk_product_id_not_empty CHECK (LENGTH(TRIM(id)) > 0),
                          CONSTRAINT chk_product_name_not_empty CHECK (LENGTH(TRIM(name)) > 0),
                          CONSTRAINT chk_product_unit_price_positive CHECK (unitPrice >= 0)
);

CREATE TABLE stockMovements (
                                id            VARCHAR(36) PRIMARY KEY,
                                createdAt     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                movementType  "MovementType" NOT NULL,
                                quantity      INTEGER NOT NULL,
                                productId     VARCHAR(36) NOT NULL,
                                CONSTRAINT chk_stock_movement_id_not_empty CHECK (LENGTH(TRIM(id)) > 0),
                                CONSTRAINT chk_stock_movement_quantity_positive CHECK (quantity > 0),
                                CONSTRAINT fk_stock_movement_product FOREIGN KEY (productId) REFERENCES products(id) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE INDEX idx_stock_movements_product_id ON stockMovements(productId);
CREATE INDEX idx_stock_movements_movement_type ON stockMovements(movementType);
CREATE INDEX idx_stock_movements_created_at ON stockMovements(createdAt);