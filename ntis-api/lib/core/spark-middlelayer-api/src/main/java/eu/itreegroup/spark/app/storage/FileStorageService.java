package eu.itreegroup.spark.app.storage;

import java.io.InputStream;
import java.sql.Connection;

import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;

public interface FileStorageService {

    static final String FILE_STATUS_UPLOADED = "UPLOADED";

    static final String FILE_STATUS_CONFIRMED = "CONFIRMED";

    static final String UPLOAD_FILE_SIZE = "UPLOAD_FILE_SIZE";

    static final String UPLOAD_FILE_SIZE_DEFAULT = "10000000";

    void deleteTempFile(SprFile fileToDelete, String path, Connection dbConnection) throws Exception;

    void deleteFile(String fileKey, String path);

    void deleteFileByEncriptedKey(String fileKey, String path);

    InputStream getFileByFileKey(String fileKey, Connection conn);

    InputStream getFileByFileEncryptedKey(String fileKey, Connection conn);

    SprFile saveFile(MultipartFile file, Connection dbConnection) throws MaxUploadSizeExceededException, Exception;

    InputStream getFileByKeyAndPath(String fileKey, String path);

    InputStream getFileBySprFilesDAO(SprFilesDAO file);

    SprFilesDAO saveFile(InputStream fileInStream, String fileName, String contentType, Connection dbConnection) throws Exception;

    String encryptFileKey(String value);

    String decryptFileKey(String encrypted);

    SprFilesDAO saveExistingFile(SprFilesDAO fileInfo, String fileName, String contentType, Connection dbConnection) throws Exception;

    SprFilesDAO createFileIndexInformation() throws Exception;

}
