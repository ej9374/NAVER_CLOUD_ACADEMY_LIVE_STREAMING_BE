package naver_cloud.live.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VodResponse {
    private String fileName;
    private String fileUrl;
    private String date;
}
