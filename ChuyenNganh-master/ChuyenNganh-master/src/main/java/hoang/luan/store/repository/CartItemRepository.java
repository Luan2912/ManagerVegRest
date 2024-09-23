package hoang.luan.store.repository;

import hoang.luan.store.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Integer> {
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId")
    public List<CartItem> findAllByCartId(@Param("cartId") Integer cartId);

}
