package hoang.luan.store.model;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(length = 1000)
    private String description;
    private  String url;
    private long price;
    private Integer statussell;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private  Category category;

}
