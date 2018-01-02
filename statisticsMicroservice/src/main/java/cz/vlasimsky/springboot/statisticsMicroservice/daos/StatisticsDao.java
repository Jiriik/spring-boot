package cz.vlasimsky.springboot.statisticsMicroservice.daos;

import cz.vlasimsky.springboot.statisticsMicroservice.entities.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatisticsDao extends JpaRepository<Statistics, Integer> {

    @Query(value = "SELECT * FROM latest_statistics WHERE EMAIL=:email ORDER BY DATE DESC", nativeQuery = true)
    public List<Statistics> getLastStatistics(@Param("email") String email);

}
