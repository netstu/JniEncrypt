package com.example.wlh.jniencrypt;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class JniEncryptActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText input_data;
    private Button encypt;
    private TextView encrpt_data;
    private Button crypt;
    private TextView orgion_data;
    private final String mKey = "123456789abcdcc";


    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("jni_mix");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni_encrypt);

        // Example of a call to a native method
//        TextView tv = (TextView) findViewById(R.id.crypt);
//        tv.setText("Build类获得cpu指令集为" + stringFromJNI());
        input_data = findViewById(R.id.inputdata);
        encypt = findViewById(R.id.encrypt);
        encrpt_data =findViewById(R.id.encrypt_data);
        crypt = findViewById(R.id.crypt);
        orgion_data = findViewById(R.id.origon_data);

        encypt.setOnClickListener(this);
        crypt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if( R.id.encrypt== v.getId()){
            String str = input_data.getText().toString();
            Log.d("JniEncryptActivity","wanlihua debug input_data : " + str);
            String des = encryptFromJni(str, mKey);
            Log.d("JniEncryptActivity","wanlihua debug encryptFromJni  : " + des);
            encrpt_data.setText(des);

        }else if (R.id.crypt == v.getId()){
            String src = decryptFromJni(encrpt_data.getText().toString(), mKey);
            Log.d("JniEncryptActivity","wanlihua debug decrypt src : " + src);
            orgion_data.setText(src);
            if(src.equals(input_data.getText().toString()))
                Toast.makeText(JniEncryptActivity.this,"AES解密匹配成功！！！",Toast.LENGTH_SHORT).show();
        }
    }

    private native String decryptFromJni(String str, java.lang.String mKey);

    private native String encryptFromJni(String str, String mKey);

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
