package naver_cloud.live.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String vodId;

    @OneToOne
    @JoinColumn
    private Channel channel;

    @Column
    private String vodName;
}
