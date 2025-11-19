package naver_cloud.live.repository;

import naver_cloud.live.entity.Thumbnail;
import naver_cloud.live.entity.ThumbnailType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThumbnailRepository extends JpaRepository<Thumbnail, Long> {
    List<Thumbnail> findByChannel_Id(Long id);

    Thumbnail findByChannel_IdAndThumbnailType(Long id, ThumbnailType thumbnailType);
}
