package api.afrilangue.serrvices.mediaobject;

import api.afrilangue.dto.response.DefaultResponseMedia;
import api.afrilangue.models.MediaObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.bson.BsonValue;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Ibrahima Diallo <iibdiallo@gmail.com>
 */
@Service
@RequiredArgsConstructor
public class MediaObjectService {

    private final GridFsTemplate template;

    private final GridFsOperations operations;

    public DefaultResponseMedia create(MultipartFile upload) throws IOException {
        DBObject metadata = new BasicDBObject();

        metadata.put("fileSize", upload.getSize());

        Object fileID = template.store(upload.getInputStream(), upload.getOriginalFilename(), upload.getContentType(), metadata);

        return DefaultResponseMedia.builder()
                .data(fileID.toString())
                .type(upload.getContentType())
                .build();
    }

    public MediaObject downloadFile(String id) throws IOException {
        GridFSFile gridFSFile = template.findOne(new Query(Criteria.where("_id").is(id)));
        MediaObject loadFile = new MediaObject();

        if (gridFSFile != null && gridFSFile.getMetadata() != null) {

            loadFile.setFilename(gridFSFile.getFilename());

            loadFile.setFileType(gridFSFile.getMetadata().get("_contentType").toString());

            loadFile.setFileSize(gridFSFile.getMetadata().get("fileSize").toString());

            loadFile.setFile(IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()));
        }

        return loadFile;
    }

    public void downloadFilesAsZip(HttpServletResponse response) throws IOException {
        List<GridFSFile> fileList = new ArrayList<>();
        template.find(new Query()).into(fileList);
        if (fileList.size() > 0) {
            ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
            for (GridFSFile gridFSFile : fileList) {
                BsonValue bsonValue = gridFSFile.getId();
                String file_id = String.valueOf(bsonValue.asObjectId().getValue());
                MediaObject file = downloadFile(file_id);
                ZipEntry zipEntry = new ZipEntry(file.getFilename());
                zipEntry.setSize(Long.parseLong(file.getFileSize()));
                zipOutputStream.putNextEntry(zipEntry);
                ByteArrayResource fileResource = new ByteArrayResource(file.getFile());
                StreamUtils.copy(fileResource.getInputStream(), zipOutputStream);
                zipOutputStream.closeEntry();
            }

            zipOutputStream.finish();
            zipOutputStream.close();
        }
    }

    public void deleteFile(String id) {
        GridFSFile gridFSFile = template.findOne(new Query(Criteria.where("_id").is(id)));
        if (gridFSFile == null) {
            throw new IllegalArgumentException("Le media " + id + " n'existe pas");
        }
        template.delete(new Query(Criteria.where("_id").is(id)));
    }
}
