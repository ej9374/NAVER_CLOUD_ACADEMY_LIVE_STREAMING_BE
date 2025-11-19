package naver_cloud.live.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class VodUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Vod vod;

    @Column
    private String vodUrl;

    @Enumerated(EnumType.STRING)
    @Column
    private QualityType qualityType;
}
