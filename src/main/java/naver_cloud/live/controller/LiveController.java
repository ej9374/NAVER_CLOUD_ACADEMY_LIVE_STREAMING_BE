package naver_cloud.live.controller;

import lombok.RequiredArgsConstructor;
import naver_cloud.live.dto.LiveInfoResponse;
import naver_cloud.live.dto.LiveResponse;
import naver_cloud.live.entity.QualityType;
import naver_cloud.live.global.SuccessResponse;
import naver_cloud.live.service.LiveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/live")
@RequiredArgsConstructor
public class LiveController {

    private final LiveService liveService;

    /**
     * 관리자용 - 방송시작
     * todo 최소한의 인가
     */
    @PostMapping("/start")
    public ResponseEntity<SuccessResponse<Void>> start(
            @RequestParam String channelId
    ) {
        liveService.liveStart(channelId);
        return SuccessResponse.ok("방송을 성공적으로 켰습니다.");
    }

    /**
     * 관리자용 - 방송 off
     */
    @PostMapping("/end")
    public ResponseEntity<SuccessResponse<Void>> end(
            @RequestParam String channelId
    ) {
        liveService.liveEnd(channelId);
        return SuccessResponse.ok("방송을 성공적으로 껐습니다.");
    }

    /**
     * 시청자용 - 현재 켜진 라이브 정보 조회
     */
    @GetMapping("/info")
    public ResponseEntity<SuccessResponse<List<LiveInfoResponse>>> getLiveInfo(){
        List<LiveInfoResponse> response = liveService.getLiveInfo();
        return SuccessResponse.onSuccess("라이브중인 방송을 조회했습니다.",HttpStatus.OK, response);
    }

    /**
     * 시청자용 - 시청할 방송 조회(채널아이디, 화질)
     * @Param channelId, QualityType
     */
    @GetMapping("/channel")
    public ResponseEntity<SuccessResponse<LiveResponse>> liveVideo(
            @RequestParam String channelId,
            @RequestParam QualityType qualityType
    ){
        LiveResponse response = liveService.getLiveVideoUrl(channelId, qualityType);
        return SuccessResponse.onSuccess("해당 화질의 링크를 조회했습니다.",HttpStatus.OK, response);
    }
}
