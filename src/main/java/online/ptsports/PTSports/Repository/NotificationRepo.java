package online.ptsports.PTSports.Repository;


import online.ptsports.PTSports.Entity.Notification;
import online.ptsports.PTSports.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
    public interface NotificationRepo extends JpaRepository<Notification, Integer> {
    @Query("SELECT n FROM Notification n WHERE n.user.userId = :userId")
    List<Notification> findByUserId(@Param("userId") Integer userId);


//    Notification deleteById(Integer userId);



}


