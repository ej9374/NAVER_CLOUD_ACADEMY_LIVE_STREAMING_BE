package naver_cloud.live.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@RestController
@RequestMapping("/proxy")
public class ProxyController {

    @GetMapping("/vod/**")
    public void proxyVod(HttpServletRequest request, HttpServletResponse response) {
        try {
            // /proxy/vod/ 이후의 경로 추출
            String path = request.getRequestURI().substring("/proxy/vod/".length());
            String vodUrl = "https://kr.object.ncloudstorage.com/live-storage/vod/" + path;

            log.info("Proxying VOD request: {} -> {}", request.getRequestURI(), vodUrl);

            // URL 연결
            URL url = new URL(vodUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Range 헤더 지원 (비디오 탐색을 위해 필요)
            String range = request.getHeader("Range");
            if (range != null) {
                connection.setRequestProperty("Range", range);
            }

            // 연결
            int responseCode = connection.getResponseCode();
            log.info("Object Storage response code: {}", responseCode);

            // 응답 헤더 복사
            response.setContentType(connection.getContentType() != null ? connection.getContentType() : "video/mp4");

            if (range != null) {
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                String contentRange = connection.getHeaderField("Content-Range");
                if (contentRange != null) {
                    response.setHeader("Content-Range", contentRange);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
            }

            int contentLength = connection.getContentLength();
            if (contentLength > 0) {
                response.setContentLength(contentLength);
            }

            response.setHeader("Accept-Ranges", "bytes");

            // 스트림 복사
            try (InputStream input = connection.getInputStream();
                 OutputStream output = response.getOutputStream()) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                output.flush();
            }

        } catch (Exception e) {
            log.error("Error proxying VOD request: {}", e.getMessage(), e);
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to proxy video: " + e.getMessage());
            } catch (IOException ioException) {
                log.error("Failed to send error response", ioException);
            }
        }
    }

    @GetMapping("/live/**")
    public void proxyLive(HttpServletRequest request, HttpServletResponse response) {
        try {
            // /proxy/live/ 이후의 경로 추출
            String path = request.getRequestURI().substring("/proxy/live/".length());

            log.info("Proxying Live request: {} -> {}", request.getRequestURI(), path);

            // URL 연결
            URL url = new URL(path); // 전체 URL이 path에 포함되어 있음
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 연결
            int responseCode = connection.getResponseCode();
            log.info("Live Stream response code: {}", responseCode);

            // 응답 헤더 복사
            String contentType = connection.getContentType();
            if (contentType != null) {
                response.setContentType(contentType);
            } else {
                response.setContentType("application/x-mpegURL"); // HLS default
            }

            response.setStatus(HttpServletResponse.SC_OK);

            int contentLength = connection.getContentLength();
            if (contentLength > 0) {
                response.setContentLength(contentLength);
            }

            // 스트림 복사
            try (InputStream input = connection.getInputStream();
                 OutputStream output = response.getOutputStream()) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                output.flush();
            }

        } catch (Exception e) {
            log.error("Error proxying Live request: {}", e.getMessage(), e);
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to proxy live stream: " + e.getMessage());
            } catch (IOException ioException) {
                log.error("Failed to send error response", ioException);
            }
        }
    }
}