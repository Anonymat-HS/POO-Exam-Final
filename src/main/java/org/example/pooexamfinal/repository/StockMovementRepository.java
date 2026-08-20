package org.example.pooexamfinal.repository;

import org.example.pooexamfinal.model.MovementType;
import org.example.pooexamfinal.model.StockMovement;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMovementRepository extends ListCrudRepository<StockMovement, String> {
    List<StockMovement> findByProductId(String productId);

    List<StockMovement> findByMovementType(MovementType movementType);
}