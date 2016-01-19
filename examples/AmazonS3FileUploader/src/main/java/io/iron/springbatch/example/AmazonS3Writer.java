package io.iron.springbatch.example;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.support.AbstractItemStreamItemWriter;

import java.net.URL;
import java.util.List;

public class AmazonS3Writer extends AbstractItemStreamItemWriter<URL> {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
    private String regionName;
    private String bucketName;
    private String accessKey;
    private String secretKey;
    private AmazonS3 s3;

    @Override
    public void close() {
    }

    /**
     * No-op.
     * @see org.springframework.batch.item.ItemStream#open(ExecutionContext)
     */
    @Override
    public void open(ExecutionContext executionContext) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
//        credentials = new EnvironmentVariableCredentialsProvider().getCredentials();

        s3 = new AmazonS3Client(credentials);

        s3.setRegion(RegionUtils.getRegion(regionName));

        System.out.println("===========================================");
        System.out.println("Getting Started with Amazon S3");
        System.out.println("===========================================\n");
    }

    @Override
    public void write(List<? extends URL> items) throws Exception {

        for (URL url : items) {
            System.out.println("Uploading a new object to S3 from a file\n");
            s3.putObject(new PutObjectRequest(bucketName, url.getHost(), url.openStream(), new ObjectMetadata()));
            logger.debug(url.toString());
        }
        logger.debug("{} urls wrote to console", items.size());
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
