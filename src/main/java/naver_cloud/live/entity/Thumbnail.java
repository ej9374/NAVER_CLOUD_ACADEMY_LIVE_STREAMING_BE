package naver_cloud.live.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Thumbnail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Channel channel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ThumbnailType thumbnailType;

    @Column(nullable = false)
    private String url;
}
