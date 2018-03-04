import java.io.IOException;

public class Main {

    public static final String UPLOAD_FILE_PATH = "src/fileUpload.txt";
    public static final String BUCKET_NAME = "first-bucket-varun";
    public static final String KEY = "test";

    public static void main(String... args) throws IOException {

        S3BucketManager s3BucketManager = S3BucketManager.getS3BucketManager();

        s3BucketManager.listAllBuckets();

        s3BucketManager.createBucket("test-bucket-aws2018");

        s3BucketManager.deleteBucket("test-bucket-aws2018");

        s3BucketManager.addFileToBucket(BUCKET_NAME, KEY, UPLOAD_FILE_PATH);

        s3BucketManager.deleteFileFromBucket(BUCKET_NAME, KEY);
    }
}
