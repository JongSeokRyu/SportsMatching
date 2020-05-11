package com.example.bestmatching;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText TextInputEditText_id;
    TextInputEditText TextInputEditText_password;

    TextView test1;

    Button sign_btn;
    Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputEditText_id = findViewById(R.id.TextInputEditText_id);
        TextInputEditText_password = findViewById(R.id.TextInputEditText_password);

        test1 = findViewById(R.id.test1);

        sign_btn = findViewById(R.id.sign_btn);
        login_btn = findViewById(R.id.login_btn);

        sign_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);

    }

    // 안스에서 노드js로 데이터 보내는 부분
    public class Post extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                //jsonObject.put("user_id", "androidTest");
                //jsonObject.put("name", "yun");

                String id = TextInputEditText_id.getText().toString();
                String password = TextInputEditText_password.getText().toString();

                jsonObject.put("id", id);
                jsonObject.put("pw", password);

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    //URL url = new URL("http://192.168.25.16:3000/users");
                    URL url = new URL(urls[0]);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();

                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            test1.setText(result);//서버로 부터 받은 값을 출력해주는 부분

            try {
                JSONObject jsonObject = new JSONObject(result);
                String msg = jsonObject.getString("result");

                if ( msg.equals("Success")){
                    Toast.makeText(getApplicationContext(),"로그인 성공",Toast.LENGTH_SHORT).show();
                    login();
                }
                else {
                    Toast.makeText(getApplicationContext(),"로그인 실패",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    public void login(){

        Intent intent = new Intent(LoginActivity.this, LoginResultActivity.class);
        startActivity(intent);

        /*if (id.equals("1")&&password.equals("1")) {
                    Intent intent = new Intent(LoginActivity.this, LoginResultActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("password", password);
                    startActivity(intent);
                }*/
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    public void sign() {
        Intent intent = new Intent(LoginActivity.this, SignActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.login_btn:
                new Post().execute("http://59.151.245.176:3000/login");
                //login();
                break;
            case R.id.sign_btn:
                sign();
                break;

        }
    }


}
