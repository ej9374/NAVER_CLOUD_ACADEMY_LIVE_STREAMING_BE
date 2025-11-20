package naver_cloud.live.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/live")
    public String livePage() {
        return "live";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }
}