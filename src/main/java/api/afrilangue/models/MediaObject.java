package api.afrilangue.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Lob;
import java.time.LocalDateTime;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Data
public class MediaObject {
    private String data;
    private String filename;
    private String fileType;
    private String fileSize;
    @Lob
    private byte[] file;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @CreatedBy
    private String createdByUser;
    @LastModifiedBy
    private String modifiedByUser;
}
