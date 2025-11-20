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

    @Column
    private String channelId;

    @Column
    private String edgeDomain;

    @Column
    private String channelName;

    @Column
    private Boolean isActive;
}
