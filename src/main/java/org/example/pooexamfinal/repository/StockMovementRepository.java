package org.example.pooexamfinal.repository;

import org.example.pooexamfinal.model.MovementType;
import org.example.pooexamfinal.model.StockMovement;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface StockMovementRepository extends ListCrudRepository<StockMovement, String> {
    List<StockMovement> findByProductId(String productId);

    @Query("""
            SELECT id, created_at, movement_type, quantity, product_id
            FROM stock_movements
            WHERE movement_type = CAST(:movementType AS movement_type)
            """)
    List<StockMovement> findByMovementType(@Param("movementType") MovementType movementType);

    @Modifying
    @Query("""
            INSERT INTO stock_movements (id, created_at, movement_type, quantity, product_id)
            VALUES (:id, :createdAt, CAST(:movementType AS movement_type), :quantity, :productId)
            """)
    void insert(@Param("id") String id,
                @Param("createdAt") Instant createdAt,
                @Param("movementType") MovementType movementType,
                @Param("quantity") int quantity,
                @Param("productId") String productId);
}