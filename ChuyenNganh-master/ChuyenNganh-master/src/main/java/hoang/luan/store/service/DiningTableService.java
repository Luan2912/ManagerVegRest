package hoang.luan.store.service;

import hoang.luan.store.model.Cart;
import hoang.luan.store.model.DiningTable;
import hoang.luan.store.repository.CartRepository;
import hoang.luan.store.repository.DiningTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiningTableService {
    @Autowired
    private DiningTableRepository diningTableRepository;
    @Autowired
    private CartRepository  cartRepository;

    public void setTable(Integer quantity){
        List<DiningTable> diningTables = diningTableRepository.findAll();
        Integer size= diningTables.size();

        if(size<quantity){
            for (int i = size+1; i <= quantity; i++) {

                DiningTable table = new DiningTable();
                table.setId(i);
                table.setStatus(1);
                DiningTable diningTable = diningTableRepository.save(table);
                Cart cart = new Cart();
                cart.setId(i);
                cart.setDiningTable(diningTable);
                cartRepository.save(cart);
            }
        }if(size>quantity){
            for (int i =quantity +1 ; i<= size ; i++){
                cartRepository.delete(diningTables.get(i-1).getCart());
                diningTableRepository.delete(diningTables.get(i-1));

            }
        }

    }
    public List<DiningTable> viewTable(){
        return diningTableRepository.findAll();
    }
    public DiningTable getTable(Integer id){
        DiningTable diningTable = diningTableRepository.getById(id);
        return diningTable ;
    }


}
