package naver_cloud.live.repository;

import naver_cloud.live.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    Channel findByChannelId(String channelId);

    List<Channel> findByIsActive(Boolean isActive);
}
