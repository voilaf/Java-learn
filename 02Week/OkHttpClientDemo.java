import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class OkHttpClientDemo {

    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://localhost:8801";

        Request request = new Request.Builder().url(url).get().build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response != null && response.isSuccessful()) {
            try {
                System.out.println(response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
