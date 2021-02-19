package `in`.ragv.streamlist

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
object AwsClient {
    val bucketName = "<bucket_name>"
    private val region =  Region.getRegion(Regions.AP_SOUTH_1)
    private val creds= BasicAWSCredentials("<access_key>", "<secret_key>")
    val s3Client: AmazonS3 by lazy {
        AmazonS3Client(creds,region)
    }

}