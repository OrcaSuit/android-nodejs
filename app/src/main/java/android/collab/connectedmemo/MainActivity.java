package android.collab.connectedmemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Retrofit mRetrofit;
    private RetrofitAPI mRetrofitAPI;
    private Call<String> mCallTextList;
    private Gson mGson;
    TextListVO mTextListVO;


    TextView contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.connect_bt_c);

        contents = (TextView)findViewById(R.id.contents_tv);


       button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                setRetrofitInit();
                callTextList();
            }
        });
    }

    private void setRetrofitInit() {
         mRetrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.57:3000/")
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
        mRetrofitAPI = mRetrofit.create(RetrofitAPI.class);
    }

    private void callTextList() {
        mCallTextList = mRetrofitAPI.getTextList();
        mCallTextList.enqueue(mRetrofitCallback);
    }

    private Callback<String> mRetrofitCallback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            String result = response.body();
            mTextListVO = (TextListVO)mGson.fromJson(result,TextListVO.class);
            contents.setText(mTextListVO.getCategory());

        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            t.printStackTrace();
        }
    };
}

