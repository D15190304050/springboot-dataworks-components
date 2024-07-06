package stark.dataworks.boot.autoconfig.minio;

import io.minio.messages.Bucket;
import stark.dataworks.boot.minio.IProgressListener;
import stark.dataworks.boot.minio.ProgressInputStream;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class EasyMinio
{
    private final MinioClient minioClient;

    public EasyMinio(EasyMinioProperties easyMinioProperties)
    {
        minioClient = MinioClient.builder()
                .endpoint(easyMinioProperties.getEndpoint())
                .credentials(easyMinioProperties.getAccessKey(), easyMinioProperties.getSecretKey())
                .build();
    }

    public MinioClient getMinioClient()
    {
        return minioClient;
    }

    public void createBucket(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build()))
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());

        log.info("Bucket \"{}\" is created successfully.", bucketName);
    }

    public String getObjectUrl(String bucketName, String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        String objectUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketName)
                .object(objectName)
                .build());

        log.info("URL of object [{}/{}] = {}", bucketName, objectName, objectUrl);
        return objectUrl;
    }

    public String uploadFileByStream(String bucketName, String fileName, InputStream inputStream) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        log.info("fileName = " + fileName);

        ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .stream(inputStream, inputStream.available(), -1)
                .build());

        inputStream.close();
        log.info("File upload successfully...");

        return getObjectUrl(bucketName, fileName);
    }

    public String uploadFileAndNotifyProgress(String bucketName, String fileName, InputStream inputStream, IProgressListener progressListener) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        log.info("fileName = " + fileName);

        ProgressInputStream progressInputStream = new ProgressInputStream(inputStream, progressListener);
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .stream(progressInputStream, progressInputStream.available(), -1)
                .build());

        progressInputStream.close();
        log.info("File upload successfully...");

        return getObjectUrl(bucketName, fileName);
    }

    public String uploadFile(String bucketName, MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        String fileName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        return uploadFileByStream(bucketName, fileName, inputStream);
    }

    public String uploadFile(String bucketName, File file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        String fileName = file.getName();
        InputStream inputStream = new FileInputStream(file);
        return uploadFileByStream(bucketName, fileName, inputStream);
    }

    public void putObject(String bucketName, String objectName, InputStream inputStream) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(inputStream, inputStream.available(), -1)
                .build());

        inputStream.close();
        log.info("File uploaded successfully...");
    }

    public void composeObjects(String bucketName, String objectName, List<String> sourceObjectNames) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        List<ComposeSource> sources = new ArrayList<>();
        for (String sourceObjectName : sourceObjectNames)
        {
            sources.add(ComposeSource.builder()
                    .bucket(bucketName)
                    .object(sourceObjectName)
                    .build());
        }

        minioClient.composeObject(ComposeObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .sources(sources)
                .build());

        log.info("Object composition successfully...");
    }

    public boolean deleteObjects(String bucketName, List<String> objectNames) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        List<DeleteObject> objectsToDelete = new ArrayList<>();
        for (String objectName : objectNames)
            objectsToDelete.add(new DeleteObject(objectName));

        Iterable<Result<DeleteError>> deletionResults = minioClient.removeObjects(
                RemoveObjectsArgs
                        .builder()
                        .bucket(bucketName)
                        .objects(objectsToDelete)
                        .build());

        int errorCount = 0;
        for (Result<DeleteError> result : deletionResults)
        {
            errorCount++;
            DeleteError error = result.get();
            log.error("Error in deleting object {}; {}", error.objectName(), error.message());
        }

        return errorCount == 0;
    }

    public List<String> listBucketNames() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        List<Bucket> bucketList = minioClient.listBuckets();
        return bucketList.stream().map(Bucket::name).collect(Collectors.toList());
    }

    public InputStream getObjectInputStream(String bucketName, String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        GetObjectResponse result = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
        log.info("Get object input stream successfully...");
        return result;
    }

    public void copyObject(String bucketName, String sourceObjectName, String destinationObjectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        minioClient.copyObject(
                CopyObjectArgs.builder()
                        .source(CopySource.builder().bucket(bucketName).object(sourceObjectName).build())
                        .bucket(bucketName)
                        .object(destinationObjectName)
                        .build());
    }
}
