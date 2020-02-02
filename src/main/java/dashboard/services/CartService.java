package dashboard.services;

import java.util.List;

public interface CartService {

    List getCart(Long userId);

    List addToCart(Long productId, int quantity);

    List updateCart(Long productId, int quantity);

    List deleteFromCart(Long productId);
}
