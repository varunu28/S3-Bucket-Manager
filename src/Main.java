import java.io.IOException;

public class Main {

    public static void main(String... args) throws IOException {

        S3BucketManager s3BucketManager = S3BucketManager.getS3BucketManager();

        s3BucketManager.listAllBuckets();

        s3BucketManager.createBucket("test-bucket-aws2018");
        s3BucketManager.listAllBuckets();

        s3BucketManager.deleteBucket("test-bucket-aws2018");
        s3BucketManager.listAllBuckets();
    }
}
