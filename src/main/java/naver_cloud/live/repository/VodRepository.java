package naver_cloud.live.repository;

import naver_cloud.live.entity.Vod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VodRepository extends JpaRepository<Vod, Long> {
    Vod findByVodId(String vodId);
}
