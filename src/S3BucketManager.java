import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class S3BucketManager {

    private AmazonS3 s3Client = null;
    private final String FILE_NAME = "src/credentials.txt";
    private static S3BucketManager S3BucketManager = null;

    private S3BucketManager() throws IOException {

        List<String> credentials = getCredentials();

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(credentials.get(0),credentials.get(1));
        s3Client = AmazonS3ClientBuilder.standard()
                                        .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                                        .build();
    }

    public static S3BucketManager getS3BucketManager() throws IOException {
        if (S3BucketManager == null) {
            S3BucketManager = new S3BucketManager();
        }

        return S3BucketManager;
    }

    private List<String> getCredentials() throws IOException {

        File file = new File(FILE_NAME);

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        List<String> credentials = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            credentials.add(line);
        }

        return credentials;
    }

    public void listAllBuckets() {

        List<Bucket> buckets = s3Client.listBuckets();

        for (Bucket b : buckets) {
            System.out.println(b.getName());
        }
    }
}
