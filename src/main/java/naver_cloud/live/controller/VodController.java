package naver_cloud.live.controller;

import lombok.RequiredArgsConstructor;
import naver_cloud.live.dto.LiveResponse;
import naver_cloud.live.dto.VodInfoResponse;
import naver_cloud.live.entity.QualityType;
import naver_cloud.live.global.SuccessResponse;
import naver_cloud.live.service.VodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vods")
@RequiredArgsConstructor
public class VodController {

    private final VodService vodService;

    @GetMapping("/info")
    public ResponseEntity<SuccessResponse<List<VodInfoResponse>>> vodInfo() {
        List<VodInfoResponse> response = vodService.getVodInfo();
        return SuccessResponse.onSuccess("시청할 수 있는 영상을 조회했습니다.", HttpStatus.OK, response);
    }

    @PostMapping("/save")
    public ResponseEntity<SuccessResponse<Void>> vodSave(
            @RequestParam String vodId,
            @RequestParam String vodName,
            @RequestParam String vodUrl,
            @RequestParam String channelId
    ) {
        vodService.saveVod(vodId, vodName, vodUrl, channelId);
        return SuccessResponse.ok("vod 영상을 저장했습니다.");
    }
}
