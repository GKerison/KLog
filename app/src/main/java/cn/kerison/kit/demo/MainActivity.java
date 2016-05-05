package cn.kerison.kit.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.kerison.kit.log.KL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showLog();
    }
    private void showLog() {

//        KL.disable();
//        KL.config().setTag("GK").setMethodCount(1);
//        KL.config() .hideExtraInfo();
        String name = "kerison";
        String json = " {\"citys\": [\n" +
                "        {\n" +
                "          \"id\": \"110000\",\n" +
                "          \"full_name\": \"北京市\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": \"120000\",\n" +
                "          \"full_name\": \"天津市\"\n" +
                "        }\n" +
                "      ]}";

        String xml = "<manifest package=\"cn.kerison.kit.log\"\n" +
                "          xmlns:android=\"http://schemas.android.com/apk/res/android\">\n" +
                "    <application />\n" +
                "</manifest>\n";

        KL.v("hello %s ", name);
        KL.d("hello %s ", name);
        KL.i("hello %s ", name);
        KL.w("hello %s ", name);
        KL.e("hello %s ", name);
        KL.wtf("hello %s ", name);
        KL.json(json);
        KL.xml(xml);
    }

}
