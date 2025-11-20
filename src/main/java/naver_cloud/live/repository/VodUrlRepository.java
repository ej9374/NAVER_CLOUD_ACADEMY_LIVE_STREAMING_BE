package naver_cloud.live.repository;

import naver_cloud.live.entity.QualityType;
import naver_cloud.live.entity.VodUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VodUrlRepository extends JpaRepository<VodUrl, Long> {

    VodUrl findByVod_VodIdAndQualityType(String vodId, QualityType qualityType);
}
