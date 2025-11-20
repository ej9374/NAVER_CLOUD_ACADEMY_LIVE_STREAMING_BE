package naver_cloud.live.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import naver_cloud.live.dto.LiveInfoResponse;
import naver_cloud.live.dto.LiveResponse;
import naver_cloud.live.entity.Channel;
import naver_cloud.live.entity.QualityType;
import naver_cloud.live.entity.ThumbnailType;
import naver_cloud.live.repository.ChannelRepository;
import naver_cloud.live.repository.ThumbnailRepository;
import naver_cloud.live.repository.VideoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LiveService {

    private final ChannelRepository channelRepository;
    private final ThumbnailRepository thumbnailRepository;
    private final VideoRepository videoRepository;

    public void liveStart(String channelId) {
        Channel channel = channelRepository.findByChannelId(channelId);
        if (channel == null)
            throw new EntityNotFoundException("해당 채널이 존재하지 않습니다.");
        channel.setIsActive(true);
        channelRepository.save(channel);
    }

    public void liveEnd(String channelId) {
        Channel channel = channelRepository.findByChannelId(channelId);
        if (channel == null)
            throw new EntityNotFoundException("해당 채널이 존재하지 않습니다.");
        channel.setIsActive(false);
        channelRepository.save(channel);
    }

    public List<LiveInfoResponse> getLiveInfo() {
        List<Channel> channels = channelRepository.findByIsActive(true);
        return channels.stream()
                .map(c -> LiveInfoResponse.builder()
                        .channelId(c.getChannelId())
                        .name(c.getChannelName())
                        .thumbnailUrl(thumbnailRepository.findByChannel_IdAndThumbnailType(c.getId(), ThumbnailType.ORIGIN).getUrl())
                        .isActive(c.getIsActive())
                        .build())
                .toList();
    }

    public LiveResponse getLiveVideoUrl(String channelId, QualityType qualityType) {
        String videoUrl = videoRepository.findByChannel_ChannelIdAndQualityType(channelId, qualityType).getUrl();
        return new LiveResponse(videoUrl);
    }
}
