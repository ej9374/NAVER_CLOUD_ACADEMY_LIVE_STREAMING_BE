package naver_cloud.live.service;

import lombok.RequiredArgsConstructor;
import naver_cloud.live.dto.LiveResponse;
import naver_cloud.live.dto.VodInfoResponse;
import naver_cloud.live.entity.QualityType;
import naver_cloud.live.entity.Vod;
import naver_cloud.live.entity.VodUrl;
import naver_cloud.live.repository.ChannelRepository;
import naver_cloud.live.repository.VodRepository;
import naver_cloud.live.repository.VodUrlRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VodService {

    private final VodRepository vodRepository;
    private final VodUrlRepository vodUrlRepository;
    private final ChannelRepository channelRepository;

    public List<VodInfoResponse> getVodInfo(){
        List<Vod> videos = vodRepository.findAllOrderByIdDesc();
        return videos.stream()
                .map(v -> {
                    String originalUrl = vodUrlRepository.findByVod_VodIdAndQualityType(v.getVodId(), QualityType.P720).getUrl();
                    // Object Storage URL을 프록시 URL로 변환
                    String proxyUrl = convertToProxyUrl(originalUrl);
                    return VodInfoResponse.builder()
                            .vodId(v.getVodId())
                            .name(v.getVodName())
                            .vodUrl(proxyUrl)
                            .build();
                })
                .toList();
    }

    private String convertToProxyUrl(String originalUrl) {
        // https://kr.object.ncloudstorage.com/live-storage/vod/파일명.mp4
        // -> /proxy/vod/파일명.mp4
        if (originalUrl.contains("/vod/")) {
            String fileName = originalUrl.substring(originalUrl.lastIndexOf("/vod/") + 5);
            return "/proxy/vod/" + fileName;
        }
        return originalUrl;
    }

    public void saveVod(String vodId, String vodName, String vodUrl, String channelId) {
        Vod vod = Vod.builder()
                .vodId(vodId)
                .vodName(vodName)
                .channel(channelRepository.findByChannelId(channelId))
                .build();
        vodRepository.save(vod);

        VodUrl vu = VodUrl.builder()
                .vod(vod)
                .qualityType(QualityType.P720)
                .url(vodUrl)
                .build();
        vodUrlRepository.save(vu);
    }
}
