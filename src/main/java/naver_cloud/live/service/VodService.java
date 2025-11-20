package naver_cloud.live.service;

import lombok.RequiredArgsConstructor;
import naver_cloud.live.dto.LiveResponse;
import naver_cloud.live.dto.VodInfoResponse;
import naver_cloud.live.entity.QualityType;
import naver_cloud.live.entity.Vod;
import naver_cloud.live.entity.VodUrl;
import naver_cloud.live.repository.VodRepository;
import naver_cloud.live.repository.VodUrlRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VodService {

    private final VodRepository vodRepository;
    private final VodUrlRepository vodUrlRepository;

    public List<VodInfoResponse> getVodInfo(){
        List<Vod> videos = vodRepository.findAll();
        return videos.stream()
                .map(v -> VodInfoResponse.builder()
                        .vodId(v.getVodId())
                        .name(v.getVodName())
                        .vodUrl(vodUrlRepository.findByVod_VodIdAndQualityType(v.getVodId(), QualityType.P720).getUrl())
                        .build())
                .toList();
    }

    public void saveVod(String vodId, String vodName, String vodUrl, String channelId) {
        Vod vod = Vod.builder()
                .vodId(vodId)
                .vodName(vodName)
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
