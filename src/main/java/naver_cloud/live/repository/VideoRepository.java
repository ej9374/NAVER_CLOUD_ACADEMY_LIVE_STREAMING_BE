package naver_cloud.live.repository;

import naver_cloud.live.entity.QualityType;
import naver_cloud.live.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    Video findByChannel_ChannelIdAndQualityType(String channelId, QualityType qualityType);
}
