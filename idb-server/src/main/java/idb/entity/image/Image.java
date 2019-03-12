package idb.entity.image;

import lombok.Data;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.*;
import java.util.List;

import javax.persistence.*;

@Entity
@Data
@Table(name = "image")
public class Image implements Serializable {
        private static final long serialVersionUID = -3009157732242241606L;
        @Column
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long imageId;

        @Column(name = "path")
        private String path;

        @Column(name = "fileType")
        private String fileType;

        @Column(name = "mods")
        private String[] mods;

        @Column(name = "createdBy")
        private Long createdBy;
}
