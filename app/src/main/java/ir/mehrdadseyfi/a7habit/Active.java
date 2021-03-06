package ir.mehrdadseyfi.a7habit;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import ir.mehrdadseyfi.a7habit.action.ScormPlayerActivity;

public class Active extends AppCompatActivity {
    Context mContext = this;
    EditText trackingCodeEditText;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active);
        IsActive book = new IsActive(false);
        book.save();
        trackingCodeEditText = (EditText) findViewById(R.id.trackingCodeEditText);
        submitButton = (Button) findViewById(R.id.submitButton);


        final String url1 = "http://shop.co21.ir/pay/activecode.php";
        final RequestParams params = new RequestParams();


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (trackingCodeEditText.getText().toString().equals("")) {
                    Toast.makeText(mContext, "کد رهگیریتان را وارد کنید", Toast.LENGTH_SHORT).show();
                } else {
                    if (isNetworkConnected()) {
                        params.put("tracking_code", trackingCodeEditText.getText().toString());
                        AsyncHttpClient client = new AsyncHttpClient();
                        client.post(url1, params, new TextHttpResponseHandler() {
                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Toast.makeText(mContext, "خطا در اتصال به شبکه", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                switch (responseString) {
                                    case "code not exist\r\n":
                                        Toast.makeText(mContext, "کد وارد شده اشتباه است", Toast.LENGTH_LONG).show();
                                        break;
                                    case "code used\r\n":
                                        Toast.makeText(mContext, "کد وارد شده استفاده شده است", Toast.LENGTH_LONG).show();
                                        break;
                                    case "success\r\n":
                                        IsActive check = IsActive.findById(IsActive.class, 1L);
                                        check.Active = true;
                                        check.save();
                                        Toast.makeText(mContext, "برنامه شما با موفقیت فعال شد", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(mContext, ScormPlayerActivity.class);// New activity
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                        break;

                                }
                            }
                        });
                    } else {
                        Toast.makeText(mContext, "شما به اینترنت متصل نمی باشید تنظیمات شبکه خود را بررسی نمایید", Toast.LENGTH_LONG).show();

                    }
                }
            }
        });
    }

    public void onBackPressed() {
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
