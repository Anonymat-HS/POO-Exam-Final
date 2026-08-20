# POO-Exam-Final — API REST de Gestion de Stock

API REST de gestion de stock développée avec Spring Boot 4.1.0, Java 21, PostgreSQL et Spring Data JDBC.

## Prérequis

- Java 21+
- Maven 3.8+
- PostgreSQL 14+
- Git

## Installation

### 1. Cloner le dépôt

```bash
git clone https://github.com/Anonymat-HS/POO-Exam-Final.git
cd POO-Exam-Final
```

### 2. Configurer la base de données

Créer la base de données et les tables :

```bash
psql -U postgres -f src/main/resources/database.sql
```

Insérer les données de test :

```bash
psql -U postgres -d stock_management -f src/main/resources/insert.sql
```

### 3. Configurer les variables d'environnement

Copier le fichier `.env.example` en `.env` et renseigner les identifiants de connexion :

```bash
cp .env.example .env
```

Le fichier `.env` doit contenir :

```
DB_URL=jdbc:postgresql://localhost:5432/stock_management
DB_USERNAME=nom_de_l_utilisateur
DB_PASSWORD=mdp
```

### 4. Compiler et lancer

```bash
mvn clean install
mvn spring-boot:run
```

L'API démarre sur `http://localhost:8080`.

## Architecture du Projet

```
src/main/java/org/example/pooexamfinal/
├── PooExamFinalApplication.java          # Point d'entrée Spring Boot
├── config/
│   └── JdbcConfig.java                   # Conversion MovementType ↔ PostgreSQL
├── model/
│   ├── Product.java                      # Entité produit
│   ├── StockMovement.java                # Entité mouvement de stock
│   └── MovementType.java                 # Enum IN, OUT
├── dto/
│   └── ProductResponse.java              # DTO de réponse pour les produits
├── repository/
│   └── ProductRepository.java            # Accès données produits (Spring Data JDBC)
├── service/
│   └── ProductService.java               # Logique métier produits
├── controller/                           # Controllers REST (à compléter)
└── exception/
    ├── ProductNotFoundException.java     # Exception 404 produit
    └── GlobalExceptionHandler.java       # Gestion globale des erreurs
```

## Schéma de la Base de Données

### Table `products`

| Colonne     | Type             | Contraintes                        |
|-------------|------------------|------------------------------------|
| id          | VARCHAR(36)      | PRIMARY KEY, NOT EMPTY             |
| name        | VARCHAR(255)     | NOT NULL, NOT EMPTY                |
| description | TEXT             | Nullable                           |
| unit_price  | NUMERIC(12, 2)   | NOT NULL, >= 0                     |

### Table `stock_movements`

| Colonne       | Type                        | Contraintes                                    |
|---------------|-----------------------------|------------------------------------------------|
| id            | VARCHAR(36)                 | PRIMARY KEY, NOT EMPTY                         |
| created_at    | TIMESTAMP WITH TIME ZONE    | NOT NULL, DEFAULT CURRENT_TIMESTAMP            |
| movement_type | movement_type (enum)        | NOT NULL, valeurs : IN, OUT                    |
| quantity      | INTEGER                     | NOT NULL, > 0                                  |
| product_id    | VARCHAR(36)                 | NOT NULL, FK → products(id), ON DELETE RESTRICT |

### Index

- `idx_stock_movements_product_id` sur `stock_movements(product_id)`
- `idx_stock_movements_movement_type` sur `stock_movements(movement_type)`
- `idx_stock_movements_created_at` sur `stock_movements(created_at)`

## Endpoints API

| Méthode | Endpoint                       | Description                                                                                                      |
|---------|--------------------------------|------------------------------------------------------------------------------------------------------------------|
| GET     | `/stock-movements`             | Retourne tous les mouvements de stock. Paramètre query optionnel `type` (`IN` ou `OUT`) pour filtrer par type. Sans paramètre, tous les mouvements sont retournés. |
| GET     | `/products/{id}/stock-movements` | Retourne tous les mouvements associés au produit identifié par `id`. Retourne 404 si le produit n'existe pas.   |
| POST    | `/stock-movements`             | Crée un mouvement de stock. Body : `{ "productId": "...", "movementType": "IN" ou "OUT", "quantity": 50 }`. Vérifie que le produit existe, que le type est valide et que la quantité est > 0. |
| GET     | `/products/{id}/stock`         | Calcule et retourne le stock actuel du produit : `SUM(IN) - SUM(OUT)`. Retourne 404 si le produit n'existe pas. |

## Format des Erreurs

Toutes les erreurs retournent un JSON avec la structure suivante :

```json
{
  "timestamp": "2026-08-20T12:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Produit non trouvé avec l'id : prod-999"
}
```

| Code HTTP | Signification                                    |
|-----------|--------------------------------------------------|
| 400       | Données invalides, type de mouvement invalide    |
| 404       | Produit inexistant                               |
| 500       | Erreur interne du serveur                        |

## Données de Test

Le fichier `insert.sql` insère 3 produits et 6 mouvements de stock :

| ID       | Produit                              | Prix     |
|----------|--------------------------------------|----------|
| prod-001 | Laptop Dell XPS 13                   | 1299.99  |
| prod-002 | Souris Logitech MX Master 3          | 99.90    |
| prod-003 | Clavier Mecanique Keychron K2        | 89.00    |

| ID     | Type | Quantité | Produit  |
|--------|------|----------|----------|
| mov-001| IN   | 10       | prod-001 |
| mov-002| OUT  | 3        | prod-001 |
| mov-003| IN   | 25       | prod-002 |
| mov-004| OUT  | 5        | prod-002 |
| mov-005| IN   | 15       | prod-003 |
| mov-006| OUT  | 1        | prod-003 |
