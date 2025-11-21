package naver_cloud.live.global;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import naver_cloud.live.dto.VodResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3Client {

    private final AmazonS3 amazonS3;

    @Value("${ncloud.backup.bucket-name}")
    private String bucketName;

    public List<VodResponse> getFileList(String folderName) {

        String prefix = folderName.endsWith("/") ? folderName : folderName + "/";

        try {
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                    .withBucketName(bucketName)
                    .withPrefix(prefix)
                    .withDelimiter("/")
                    .withMaxKeys(300);

            ObjectListing objectListing = amazonS3.listObjects(listObjectsRequest);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            List<VodResponse> fileList = new ArrayList<>();
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                if (!objectSummary.getKey().equals(prefix)) {

                    String key = objectSummary.getKey();
                    String fileUrl = amazonS3.getUrl(bucketName, key).toString();
                    Date lastModified = objectSummary.getLastModified();
                    String dateStr = sdf.format(lastModified);
                    VodResponse vod = VodResponse.builder()
                            .fileName(key)
                            .fileUrl(fileUrl)
                            .date(dateStr)
                            .build();
                    fileList.add(vod);
                }
            }
            fileList.sort(Comparator.comparing(VodResponse::getDate));
            return fileList;
        } catch (AmazonS3Exception e) {
            log.error("S3 조회 에러 발생: " + e.getErrorMessage());
            return Collections.emptyList();
        }
    }
}
