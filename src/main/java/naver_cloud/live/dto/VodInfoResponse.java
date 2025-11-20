package naver_cloud.live.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VodInfoResponse {
    String vodId;
    String name;
    String vodUrl;
}
