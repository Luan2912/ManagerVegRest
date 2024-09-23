package hoang.luan.store.repository;


import hoang.luan.store.model.DiningTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiningTableRepository extends JpaRepository<DiningTable,Integer> {
}
