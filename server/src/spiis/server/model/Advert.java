package spiis.server.model;


import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Data
public class Advert {

    @Id
    @GeneratedValue
    @Nullable
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String link;

    @Column(columnDefinition="TEXT", nullable = false)
    private String picture;

    @PrePersist
    @PreUpdate
    public void verifyModel() {
        ModelUtil.requireNonNull(title);
        ModelUtil.requireNonNull(companyName);
        ModelUtil.requireNonNull(link);
        ModelUtil.requireNonNull(picture);

        ModelUtil.ensureTextTrimAndLength(title, 3, 200, "title");
        ModelUtil.ensureTextTrimAndLength(companyName, 2, 200, "companyName");
        ModelUtil.ensureTextTrimAndLength(link, 2, 200, "link");
        ModelUtil.ensureTextTrimAndLength(picture, 2, 40000000, "picture");
    }
}
