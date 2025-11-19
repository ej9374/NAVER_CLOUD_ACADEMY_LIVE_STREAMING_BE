package naver_cloud.live.entity;

import jakarta.persistence.*;

@Entity
public class VodThumbnail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Vod vod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ThumbnailType thumbnailType;

    @Column(nullable = false)
    private String url;
}
