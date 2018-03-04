# S3-Bucket-Manager
S3 Bucket Manager for performing various operations on AWS S3 bucket using AWS SDK

## How to use this?
You need to add the [AWS-SDK](https://aws.amazon.com/sdk-for-java/) ```core``` and ```third-party``` libraries to the class path.

Update the path to your credentials file under ```CREDENTIALS_FILE```

## List of operations and corresponding methods:
 - List buckets ```listAllBuckets```
 - Create bucket ```createBucket```
 - Delete a bucket ```deleteBucket```
 - Get a bucket using the key ```getBucket```
 - Upload a file to a bucket ```addFileToBucket```
 - Remove a file from a bucket ```deleteFileFromBucket``` (Adds a delete marker if versioning is enabled)
 
**NOTE:** A singleton is implemented to avoid opening multiple s3Client connections

## Contributing
Raise a PR or open an issue for a new operation or any issues in the code