package hoang.luan.store.repository;

import hoang.luan.store.model.CalendarStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarStaffRepository extends JpaRepository<CalendarStaff,Integer> {
    List<CalendarStaff> findByUserId(Integer id);
}
