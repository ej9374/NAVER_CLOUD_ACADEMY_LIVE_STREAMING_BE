package naver_cloud.live.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VodUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Vod vod;

    @Enumerated(EnumType.STRING)
    @Column
    private QualityType qualityType;
    // 720p, 480p

    @Column
    private String url;
}
