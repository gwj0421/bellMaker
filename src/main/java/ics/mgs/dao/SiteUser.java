package ics.mgs.dao;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "SITE_USER_TB")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", length = 20, nullable = false)
    private String userId;

    @Column(length = 15, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String email;

    @OneToMany(mappedBy = "user")
    private Set<Bell> bells = new LinkedHashSet<>();

    @Builder
    public SiteUser(String userId, String name, String password, String email) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addBell(Bell bell) {
        this.bells.add(bell);
        bell.setUser(this);
    }

    public void addBells(Set<Bell> bells) {
        for (Bell bell : bells) {
            addBell(bell);
        }
    }

    public void removeBell(Bell bell) {
        this.bells.remove(bell);
        bell.setUser(null);
    }
}
