package ics.mgs.dao;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BELL_TB")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Bell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private SiteUser user;

    private String fileName;

    @Column(length = 2083)
    private String url;

    @Builder
    public Bell(String fileName, String url) {
        this.fileName = fileName;
        this.url = url;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setUser(SiteUser user) {
        this.user = user;
    }
}
