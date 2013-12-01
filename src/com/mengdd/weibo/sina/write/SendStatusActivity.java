package com.mengdd.weibo.sina.write;

import com.mengdd.hellosina.R;
import com.weibo.sina.android.api.RequestListenerAdapter;
import com.weibo.sina.android.api.StatusesAPI;
import com.weibo.sina.android.utils.AppConstants;
import com.weibo.sina.android.utils.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendStatusActivity extends Activity {

    private static final String TAG = "Send";

    private Button mSendButton = null;
    private EditText mInputEditText = null;
    private String mTextContent = null;

    // how to hide input keyboard
    private InputMethodManager mInputMethodManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_write);
        // hide input keyboard
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mSendButton = (Button) findViewById(R.id.send);
        mSendButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 关闭软键盘
                mInputMethodManager.hideSoftInputFromWindow(
                        mInputEditText.getWindowToken(), 0);

                sendStatus(mTextContent);
            }
        });
        mInputEditText = (EditText) findViewById(R.id.input);
        mInputEditText.addTextChangedListener(mTextWatcher);
    }

    private void sendStatus(String text) {

        Log.i(TAG, "sendStatus: " + text);

        StatusesAPI.update(text, mSendRequestListener);
    }

    private RequestListenerAdapter mSendRequestListener = new RequestListenerAdapter() {

        public void onComplete(String result) {
            Log.i(TAG, "onComplete: " + result);

            SendStatusActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(SendStatusActivity.this, "微博发送成功！",
                            Toast.LENGTH_LONG).show();
                    mInputEditText.setText("");
                }
            });

        };
    };

    private TextWatcher mTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                int count) {
            if (!TextUtils.isEmpty(s))
                mTextContent = s.toString().trim();
            else {
                mTextContent = null;
            }

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            // 检查字数限制
            StringUtils.validateChineseStringLength(SendStatusActivity.this,
                    mInputEditText, s.toString(),
                    AppConstants.MAX_CHARACTERS_COUNT);

        }
    };

}
