package oysd.com.android_rx_demo.utils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by oysd on 2016/9/12.
 */
public class DownloadUtil {

    private OkHttpClient client;
    public DownloadUtil(){
        client = new OkHttpClient();
    }

    public Observable<byte []> downloadImage(final String path){
        return Observable.create(new Observable.OnSubscribe<byte[]>() {
            @Override
            public void call(final Subscriber<? super byte[]> subscriber) {
                Request request = new Request.Builder().url(path).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                        subscriber.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.isSuccessful()){
                            byte [] data = response.body().bytes();
                            if(data != null){
                                subscriber.onNext(data);
                            }
                        }
                        subscriber.onCompleted();
                    }
                });
            }
        });
    }
}
