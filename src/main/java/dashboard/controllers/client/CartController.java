package dashboard.controllers.client;

import dashboard.commons.ValidationUtils;
import dashboard.dto.cart.CartForm;
import dashboard.exceptions.customs.ResourceNotFoundException;
import dashboard.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client/api/cart")

@Transactional
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("")
    public ResponseEntity getAll () {
        return ResponseEntity.ok(cartService.getCart(1L));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody CartForm cartForm) {
        ValidationUtils.validate(cartForm);
        return ResponseEntity.ok(cartService.addToCart(cartForm.getProductId(), cartForm.getQuantity()));
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody CartForm cartForm) {
        ValidationUtils.validate(cartForm);
        return ResponseEntity.ok(cartService.updateCart(cartForm.getProductId(), cartForm.getQuantity()));
    }

    @DeleteMapping(value = "{productId}")
    public ResponseEntity delete(@PathVariable(name = "productId") Long productId) throws ResourceNotFoundException {
        return ResponseEntity.ok(cartService.deleteFromCart(productId));
    }
}
