package naver_cloud.live.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChannelListResponse {
    private String channelId;
    private String channelName;
    private Boolean isActive;
}
