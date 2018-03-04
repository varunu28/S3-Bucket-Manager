import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class S3BucketManager {

    private AmazonS3 s3Client = null;
    private static final String CREDENTIALS_FILE = "src/credentials.txt";
    private File file;
    private static S3BucketManager S3BucketManager = null;

    private S3BucketManager() throws IOException {

        List<String> credentials = getCredentials();

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(credentials.get(0), credentials.get(1));
        s3Client = AmazonS3ClientBuilder.standard()
                                        .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                                        .withRegion(Regions.US_EAST_1)
                                        .build();
    }

    public static S3BucketManager getS3BucketManager() throws IOException {

        if (S3BucketManager == null) {
            S3BucketManager = new S3BucketManager();
        }

        return S3BucketManager;
    }

    public void listAllBuckets() {

        List<Bucket> buckets = s3Client.listBuckets();
        System.out.println("List of buckets: ");
        for (Bucket b : buckets) {
            System.out.println(b.getName());
        }
    }

    public void createBucket(String bucketName) {

        try {
            if (s3Client.doesBucketExistV2(bucketName)) {
                System.out.println("Bucket already exists");
            } else {
                s3Client.createBucket(new CreateBucketRequest(bucketName));
            }
        } catch (AmazonServiceException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    public void deleteBucket(String bucketName) {

        try {
            if (s3Client.doesBucketExistV2(bucketName)) {
                System.out.println("Deleting " + bucketName);
                s3Client.deleteBucket(bucketName);
            }
            else {
                System.out.println("Bucket does not exist");
            }
        } catch (AmazonServiceException e) {
            System.out.println(e.getErrorMessage());
        }
    }

    public void addFileToBucket(String bucketName, String key, String filePath) {

        file = new File(filePath);

        if (s3Client.doesBucketExistV2(bucketName)) {
            try {
                s3Client.putObject(new PutObjectRequest(bucketName, key, file));
            } catch (AmazonServiceException e) {
                System.out.println(e.getErrorMessage());
            }
        }
    }

    public void deleteFileFromBucket(String bucketName, String key) {

        if (s3Client.doesBucketExistV2(bucketName)) {
            try {
                s3Client.deleteObject(new DeleteObjectRequest(bucketName, key));
            } catch (AmazonServiceException e) {
                System.out.println(e.getErrorMessage());
            }
        }
    }

    public Bucket getBucket(String bucketName) {

        if (s3Client.doesBucketExistV2(bucketName)) {
            try {
                List<Bucket> buckets = s3Client.listBuckets();
                for (Bucket bucket : buckets) {
                    if (bucket.getName().equals(bucketName)) {
                        return bucket;
                    }
                }
            } catch (AmazonServiceException e) {
                System.out.println(e.getErrorMessage());
            }
        }

        return null;
    }

    private List<String> getCredentials() throws IOException {

        file = new File(CREDENTIALS_FILE);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        List<String> credentials = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            credentials.add(line);
        }

        return credentials;
    }
}
