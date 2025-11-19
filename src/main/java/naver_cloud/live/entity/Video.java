package naver_cloud.live.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Channel channel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QualityType qualityType;

    @Column(nullable = false)
    private String url;
}
