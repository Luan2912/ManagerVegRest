package hoang.luan.store.repository;

import hoang.luan.store.model.FeedBack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack,Integer> {
//    @Query("SELECT c FROM FeedBack c WHERE c.stars=:star")
//    public List<FeedBack>getFeedBackByStar(Integer star);
Page<FeedBack> getFeedBackByStars(Integer star, Pageable pageable);
    @Query("SELECT fb FROM FeedBack fb ORDER BY fb.id DESC")
    public List<FeedBack> findFeedBackNew();


}
