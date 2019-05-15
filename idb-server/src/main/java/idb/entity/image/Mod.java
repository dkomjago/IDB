package idb.entity.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "mods")
public class Mod implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private int cropX;

    @Column
    private int cropY;

    @Column
    private Integer cropWidth;

    @Column
    private Integer cropHeight;

    @Column
    private int width;

    @Column
    private int height;

    @Column
    private String upperText;

    @Column
    private String lowerText;


    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false)
    @JoinColumn(name = "image_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Image image;
}
