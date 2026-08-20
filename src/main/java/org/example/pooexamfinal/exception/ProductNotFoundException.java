package org.example.pooexamfinal.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String id) {
        super("Produit non trouvé avec l'id : " + id);
    }
}
