package eu.itreegroup.spark.app.storage.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import eu.itreegroup.s2.client.util.S2Message;
import eu.itreegroup.s2.server.jwt.BackendUserSession;
import eu.itreegroup.spark.app.SprAuthorization;
import eu.itreegroup.spark.app.storage.FileStorageService;
import eu.itreegroup.spark.app.storage.security.SecretKeyModel;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.enums.SecretKeyType;
import eu.itreegroup.spark.modules.admin.logic.forms.model.SprFile;
import eu.itreegroup.spark.modules.admin.rest.SparkDataService;
import eu.itreegroup.spark.modules.common.dao.SprFilesDAO;
import eu.itreegroup.spark.modules.common.service.SprFilesDBService;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkBusinessException;
import lt.jmsys.spark.bind.executor.plsql.errors.SparkMessageType;

public class FileStorageServiceImpl implements FileStorageService {

    Path storageLocation = Paths.get("/var/lib/portalStorage");

    @Value("${filesStorage.path}")
    private String storagePath;

    @Autowired
    SprFilesDBService filesDBService;

    @Autowired
    DBPropertyManager dbPropertyManager;

    private static final Logger log = LoggerFactory.getLogger(SparkDataService.class);

    @Override
    public void deleteTempFile(SprFile fileToDelete, String path, Connection dbConnection) throws Exception {
        String fileKey = decryptFileKey(fileToDelete.getFil_key());
        SprFilesDAO fileDao = filesDBService.loadRecordByKey(dbConnection, fileKey);
        try {
            if (FILE_STATUS_UPLOADED.equals(fileDao.getFil_status())) {
                File attachment = null;
                String locationPath = fileDao.getFil_path();

                File dir = new File(locationPath);
                File[] files = dir.listFiles((dir1, name) -> name.startsWith(fileKey));
                attachment = files[0];
                if (attachment != null) {
                    attachment.delete();
                    filesDBService.deleteRecord(dbConnection, fileDao.getFil_id());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not delete the file from storage directory. Error: " + e.getMessage());
        }
    }

    public String saveFile(InputStream file) {
        try {
            Files.createDirectories(storageLocation).toAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize storage", e);
        }

        String storageFileName = UUID.randomUUID().toString();
        try {
            // InputStream fileStream = new FileInputStream(file);
            Files.copy(file, storageLocation.resolve(storageFileName));
            return storageFileName;
        } catch (Exception e) {
            log.info("Could not upload file. Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteFile(String fileKey, String path) {
        try {
            File[] files = new File(path).listFiles((dir, name) -> name.startsWith(fileKey));
            if (files != null && files.length > 0 && files[0] != null) {
                files[0].delete();
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("Could not delete the file '%s' from storage directory '%s'.", fileKey, path), e);
        }
    }

    @Override
    public void deleteFileByEncriptedKey(String fileKey, String path) {
        deleteFile(decryptFileKey(fileKey), path);
    }

    @Override
    public InputStream getFileByFileKey(String fileKey, Connection conn) {
        try {

            SprFilesDAO fileDao = filesDBService.loadRecordByKey(conn, fileKey);
            File dir = new File(fileDao.getFil_path());
            File[] files = dir.listFiles((dir1, name) -> name.startsWith(fileKey));
            log.info(String.format("Files length: %d", files.length));
            File file = files[0];
            return new FileInputStream(file);
        } catch (Exception e) {
            throw new RuntimeException("Could not load the file from storage. Error: " + e.getMessage());
        }
    }

    @Override
    public InputStream getFileByFileEncryptedKey(String fileKey, Connection conn) {
        return getFileByFileKey(decryptFileKey(fileKey), conn);
    }

    @Override
    public InputStream getFileByKeyAndPath(String fileKey, String path) {
        try {
            File dir = new File(path);
            File[] files = dir.listFiles((dir1, name) -> name.startsWith(fileKey));
            log.info(String.format("Files length: %d", files.length));
            File file = files[0];
            return new FileInputStream(file);
        } catch (Exception e) {
            throw new RuntimeException("Could not load the file from storage. Error: " + e.getMessage());
        }
    }

    @Override
    public InputStream getFileBySprFilesDAO(SprFilesDAO file) {
        return getFileByKeyAndPath(file.getFil_key(), file.getFil_path());
    }

    @Override
    public SprFilesDAO createFileIndexInformation() throws Exception {
        SprFilesDAO uploadedFileData = filesDBService.newRecord();

        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        String varPath = year + "/" + month + "/" + day;

        String locationPath = storagePath + "/" + varPath;

        String storageFileName = UUID.randomUUID().toString();
        uploadedFileData.setFil_key(storageFileName);
        uploadedFileData.setFil_status(FILE_STATUS_UPLOADED);
        uploadedFileData.setFil_status_date(new Date());
        uploadedFileData.setFil_path(locationPath);
        Path location = Paths.get(locationPath);
        Files.createDirectories(location).toAbsolutePath();
        uploadedFileData.setFil_server(InetAddress.getLocalHost().getHostName());
        return uploadedFileData;
    }

    @Override
    public SprFile saveFile(MultipartFile file, Connection dbConnection) throws MaxUploadSizeExceededException, Exception {
        int maxFileSize = Integer.parseInt(dbPropertyManager.getPropertyByName(UPLOAD_FILE_SIZE, UPLOAD_FILE_SIZE_DEFAULT));
        if (file.getSize() > maxFileSize) {
            throw new MaxUploadSizeExceededException(maxFileSize);
        }
        SprFilesDAO uploadedFileData = createFileIndexInformation();
        Path location = Paths.get(uploadedFileData.getFil_path());
        Files.createDirectories(location).toAbsolutePath();
        Files.copy(file.getInputStream(), location.resolve(uploadedFileData.getFil_key()));
        uploadedFileData.setFil_name(file.getOriginalFilename());
        uploadedFileData.setFil_size((double) file.getSize());
        uploadedFileData.setFil_content_type(file.getContentType());
        uploadedFileData.setFil_server(InetAddress.getLocalHost().getHostName());
        filesDBService.insertRecord(dbConnection, uploadedFileData);
        uploadedFileData.setFil_path(null);
        return new SprFile(uploadedFileData.getFil_content_type(), encryptFileKey(uploadedFileData.getFil_key()), uploadedFileData.getFil_name(),
                uploadedFileData.getFil_size(), uploadedFileData.getFil_status(), uploadedFileData.getFil_status_date());
    }

    @Override
    public SprFilesDAO saveFile(InputStream fileInStream, String fileName, String contentType, Connection dbConnection) throws Exception {
        SprFilesDAO uploadedFileData = createFileIndexInformation();
        Path location = Paths.get(uploadedFileData.getFil_path());
        Files.createDirectories(location).toAbsolutePath();
        Files.copy(fileInStream, location.resolve(uploadedFileData.getFil_key()));
        long bytes = Files.size(location.resolve(uploadedFileData.getFil_key()));
        uploadedFileData.setFil_name(fileName);
        uploadedFileData.setFil_size(Double.valueOf(bytes));
        uploadedFileData.setFil_content_type(contentType);
        uploadedFileData.setFil_server(InetAddress.getLocalHost().getHostName());
        uploadedFileData = filesDBService.insertRecord(dbConnection, uploadedFileData);
        return uploadedFileData;
    }

    @Override
    public SprFilesDAO saveExistingFile(SprFilesDAO fileInfo, String fileName, String contentType, Connection dbConnection) throws Exception {
        fileInfo.setFil_name(fileName);
        fileInfo.setFil_content_type(contentType);
        fileInfo.setFil_server(InetAddress.getLocalHost().getHostName());
        fileInfo = filesDBService.insertRecord(dbConnection, fileInfo);
        return fileInfo;
    }

    @Override
    public String encryptFileKey(String value) {
        return new FileKeyEncryptor().encryptFileKey(value);
    }

    @Override
    public String decryptFileKey(String encrypted) {
        return new FileKeyEncryptor().decryptFileKey(encrypted);
    }

    private class FileKeyEncryptor {

        private static final String FILE_KEY_CIPHER = "AES/CBC/PKCS5Padding";

        private static final String INITIALIZATION_VECTOR = "AODVNUASDNVVAOVF";

        private SecretKey getSecretKeyFromString(String input, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(input.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
            return secret;

        }

        /**
         * Function will create SecretKeyModel, that will be used for file key encryption. In case if user logged then for key 
         * generation will be used session key. In case if user works as anonymous then for encryption will be used current date
         * for decryption will be used information from provided encripted key (last two symbols will be shown what date should be
         * used for decription.
         * @param userSession - object that present user session data
         * @param encriptedkey - string that presents what should be decripted.
         * @return Security key model, that has informaction about encriptio/decription
         * @throws NoSuchAlgorithmException
         * @throws InvalidKeySpecException
         */
        private SecretKeyModel getSecretKey(BackendUserSession userSession, String encriptedkey) throws NoSuchAlgorithmException, InvalidKeySpecException {
            String sesKey = null;
            String userIdAsHexString = null;
            String keySuffix = null;
            SecretKeyType keyType;
            if (userSession != null && userSession.getUsrId() != SprAuthorization.ANONYMOUS_USER_ID) {
                keyType = SecretKeyType.LOGED_USER_KEY;
                sesKey = userSession.getSesKey();
                userIdAsHexString = Double.toHexString(userSession.getUsrId());
                int length = 2;
                boolean useLetters = true;
                boolean useNumbers = false;
                keySuffix = RandomStringUtils.random(length, useLetters, useNumbers);
            } else {
                keyType = SecretKeyType.PUBLIC_USER_KEY;
                Date today = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(today);
                int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
                if (encriptedkey == null) {
                    keySuffix = String.format("%1$" + 2 + "s", dayOfMonth).replace(' ', '0');
                } else {
                    keySuffix = encriptedkey.substring(encriptedkey.length() - 2);
                    int dayOfMonthFromKey = Integer.parseInt(keySuffix);
                    if (dayOfMonthFromKey != dayOfMonth && dayOfMonthFromKey + 1 != dayOfMonth) {
                        throw new InvalidKeySpecException("Wrong key provided");
                    } else {
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonthFromKey);
                    }

                }
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                sesKey = formatter.format(cal.getTime());
                userIdAsHexString = Double.toHexString(42);
            }
            return new SecretKeyModel(getSecretKeyFromString(sesKey, userIdAsHexString), keyType, keySuffix);

        }

        public String encryptFileKey(String value) {
            String result = value;
            try {
                Cipher cipher = Cipher.getInstance(FILE_KEY_CIPHER);
                SecretKeyModel keyModel = getSecretKey(filesDBService.getUserSession(), null);
                cipher.init(Cipher.ENCRYPT_MODE, keyModel.getKey(), new IvParameterSpec(INITIALIZATION_VECTOR.getBytes(StandardCharsets.UTF_8)));
                result = Base64.encodeBase64URLSafeString(
                        ArrayUtils.addAll(cipher.doFinal(value.getBytes(StandardCharsets.UTF_8)), keyModel.getKeySuffix().getBytes("UTF-8")));
            } catch (Exception ex) {
                log.error("Error on file key encription. Provided file key value will be returned. ", ex);
            }
            return result;
        }

        public String decryptFileKey(String encrypted) {
            String result = encrypted;
            try {
                Cipher cipher = Cipher.getInstance(FILE_KEY_CIPHER);
                byte[] encriptedBytes = Base64.decodeBase64(encrypted.getBytes());
                byte[] encriptedKeyBytes = new byte[encriptedBytes.length - 2];
                System.arraycopy(encriptedBytes, 0, encriptedKeyBytes, 0, encriptedBytes.length - 2);
                SecretKeyModel keyModel = getSecretKey(filesDBService.getUserSession(), new String(encriptedBytes));
                cipher.init(Cipher.DECRYPT_MODE, keyModel.getKey(), new IvParameterSpec(INITIALIZATION_VECTOR.getBytes(StandardCharsets.UTF_8)));
                byte[] original = cipher.doFinal(encriptedKeyBytes);
                result = new String(original);
            } catch (Exception ex) {
                log.error("Error on file key decription. Provided file key value will be returned", ex);
            }

            return result;
        }
    }

    private String getMimeTypeClass(String mimeType) {
        return mimeType.substring(0, mimeType.indexOf('/'));
    }

    private String getFileExtension(MultipartFile file) {
        String[] fileNameParts = file.getOriginalFilename().split("\\.");
        return fileNameParts.length > 0 ? "." + fileNameParts[fileNameParts.length - 1] : "";
    }

    /**
     * Checks if file type matches the accepted value
     * @param file - file to check
     * @param accept - comma separated list of accepted file extensions or MIME types (e.g. <code>".geojson, application/zip, image/*"</code>)
     * @return true - if file type matches one of the accepted types, false otherwise
     */
    public boolean isFileTypeAccepted(MultipartFile file, String accept) {
        Stream<String> acceptValuesStream = Arrays.stream(accept.split(",", 0)).map(type -> type.trim()).filter(type -> !type.isBlank());
        return acceptValuesStream.anyMatch(acceptableType -> {
            if (acceptableType.indexOf('*') != -1) {
                return this.getMimeTypeClass(acceptableType).equalsIgnoreCase(this.getMimeTypeClass(file.getContentType())) || acceptableType.equals("*");
            } else {
                return file.getContentType().equals(acceptableType) || acceptableType.equalsIgnoreCase(this.getFileExtension(file));
            }
        });
    }

    /**
     * Checks if file type matches the accepted value and throws SparkBusinessException if it doesn't
     * @param file - file to check
     * @param accept - comma separated list of accepted file extensions or MIME types (e.g. <code>".geojson, application/zip, image/*"</code>)
     */
    public void checkIsFileTypeAccepted(MultipartFile file, String accept) throws SparkBusinessException {
        if (!this.isFileTypeAccepted(file, accept)) {
            throw new SparkBusinessException(new S2Message("common.error.fileType", SparkMessageType.ERROR, accept));
        }
    }

}
