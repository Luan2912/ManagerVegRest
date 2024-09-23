package hoang.luan.store.service;

import hoang.luan.store.model.CartItem;
import hoang.luan.store.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;
    public List<CartItem> getAll(Integer id){
        return cartItemRepository.findAllByCartId(id);
    }
    public void removeCartItem(CartItem cartItem){
        cartItemRepository.delete(cartItem);
    }
    public void removeAllCartItem(Integer id){
        List<CartItem> cartItems = cartItemRepository.findAllByCartId(id);
        cartItemRepository.deleteAll(cartItems);
    }
}
