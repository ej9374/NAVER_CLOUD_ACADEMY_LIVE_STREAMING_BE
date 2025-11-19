package naver_cloud.live.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LiveInfoResponse {
    String channelId;
    String name;
    String thumbnailUrl;
    Boolean isActive;
}
