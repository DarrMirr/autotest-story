package darr.mirr.utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Optional;

public class HttpClientManager {
    private static CloseableHttpClient httpClient = HttpClients.createDefault();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(HttpClientManager::close));
    }

    public Optional<String> sendFormData(String url, File file, Header... headers) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeaders(headers);
        HttpEntity httpEntity = MultipartEntityBuilder
                .create()
                .setContentType(ContentType.MULTIPART_FORM_DATA)
                .addPart("attachment", new FileBody(file, ContentType.APPLICATION_OCTET_STREAM))
                .build();
        httpPost.setEntity(httpEntity);
        return Optional.ofNullable(send(httpPost));
    }

    private String send(HttpUriRequest requestBase) {
        try (CloseableHttpResponse response = httpClient.execute(requestBase)) {
            if (!isStatusSuccess(response.getStatusLine())) {
                throw new HttpResponseException(response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());
            }
            return EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isStatusSuccess(StatusLine statusLine) {
        return statusLine.getStatusCode() >= 200 && statusLine.getStatusCode() < 300;
    }

    public static void close() {
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
