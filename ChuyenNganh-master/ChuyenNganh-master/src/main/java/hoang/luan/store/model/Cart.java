package hoang.luan.store.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Cart {
    @Id
    private  Integer id;
    @OneToOne
    private DiningTable diningTable;
    @OneToMany(mappedBy = "cart" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<CartItem> cartItems;
}
