package naver_cloud.live.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String channelId;

    @Column(unique = true, nullable = false)
    private String edgeDomain;

    @Column(unique = true, nullable = false)
    private String channelName;

    @Column(nullable = false)
    private Boolean isActive;
}
