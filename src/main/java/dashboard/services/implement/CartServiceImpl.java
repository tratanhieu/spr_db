package dashboard.services.implement;

import dashboard.dto.cart.CartForm;
import dashboard.repositories.CartMapper;
import dashboard.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    CartMapper cartMapper;

    @Override
    public List getCart(Long userId) {
        return cartMapper.getCart(userId);
    }

    @Override
    public List addToCart(Long productId, int quantity) {
        CartForm cartForm = new CartForm();
        cartForm.setProductId(productId);
        cartForm.setUserId(1L);
        cartForm.setProductName("abcxyz");
        cartForm.setPrice(5555L);
        cartForm.setQuantity(quantity);
        cartForm.setSaleOff(15);
        cartForm.setTotalPrice();
        cartMapper.addToCart(cartForm);
        return cartMapper.getCart(cartForm.getUserId());
    }

    @Override
    public List updateCart(Long productId, int quantity) {
        CartForm cartForm = new CartForm();
        cartForm.setProductId(productId);
        cartForm.setUserId(1L);
        cartForm.setQuantity(quantity);
        cartForm.setTotalPrice();
        cartMapper.updateCart(cartForm);
        return cartMapper.getCart(cartForm.getUserId());
    }

    @Override
    public List deleteFromCart(Long productId) {
        cartMapper.deleteFromCart(1L, productId);
        return cartMapper.getCart(1L);
    }
}
