package naver_cloud.live.repository;

import naver_cloud.live.entity.Vod;
import naver_cloud.live.entity.VodUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VodRepository extends JpaRepository<Vod, Long> {

    @Query("select v from Vod v order by v.id desc")
    List<Vod> findAllOrderByIdDesc();

    Vod findByVodId(String vodId);
}
