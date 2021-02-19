package `in`.ragv.streamlist

import com.amazonaws.AmazonServiceException
import com.amazonaws.HttpMethod
import com.amazonaws.AmazonClientException
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.amazonaws.services.s3.model.ListObjectsV2Request
import com.amazonaws.services.s3.model.ListObjectsV2Result
import java.net.URL
import java.util.Date
import kotlin.collections.ArrayList


class FileService {
    fun getPresignedURL(key:String): URL {
        val expiration = Date()
        var expTimeMillis = expiration.time
        expTimeMillis += 1000 * 60 * 60 * 24 .toLong()
        expiration.time = expTimeMillis

        // Generate the presigned URL.

        // Generate the presigned URL.
        val generatePresignedUrlRequest =
            GeneratePresignedUrlRequest(AwsClient.bucketName, key)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration)
        val url: URL = AwsClient.s3Client.generatePresignedUrl(generatePresignedUrlRequest)
        return url
    }
    fun getStreamables(): ArrayList<DataModel> {
        val data_result = ArrayList<DataModel>()
        try {


            // maxKeys is set to 2 to demonstrate the use of
            // ListObjectsV2Result.getNextContinuationToken()
            val req =
                ListObjectsV2Request().withBucketName(AwsClient.bucketName).withMaxKeys(100)
            var result: ListObjectsV2Result
            do {
                result = AwsClient.s3Client.listObjectsV2(req)
                for (objectSummary in result.objectSummaries) {

                    data_result.add(DataModel(objectSummary.key, objectSummary.key, objectSummary.size))
                }
                // If there are more than maxKeys keys in the bucket, get a continuation token
                // and list the next objects.
                val token = result.nextContinuationToken
                println("Next Continuation Token: $token")
                req.continuationToken = token
            } while (result.isTruncated)
        } catch (e: AmazonServiceException) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace()
        } catch (e: AmazonClientException) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace()
        }
        return data_result
    }
}