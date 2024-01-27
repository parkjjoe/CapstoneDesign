package com.example.capstonedesign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SourceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.source);

        Button buttonA = findViewById(R.id.button0);

        // BACK 버튼
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView textView0 = findViewById(R.id.text_view0);

        textView0.setText("[이미지 출처: Freepik]\n\n1. <a href=\"https://kr.freepik.com/free-vector/collection-of-flat-sleeping-poses_4365048.htm#query=%EC%88%98%EB%A9%B4&position=2&from_view=search&track=sph\">Freepik</a>\n\n2. <a href=\"https://kr.freepik.com/free-vector/collection-of-flat-sleeping-poses_4365035.htm#query=%EC%88%98%EB%A9%B4%20%EC%9E%90%EC%84%B8&position=34&from_view=search&track=ais\">Freepik</a>\n\n3. <a href=\"https://kr.freepik.com/free-vector/top-view-flat-person-sleep-position-pack_4433873.htm#page=2&query=%EC%88%98%EB%A9%B4%20%EC%9E%90%EC%84%B8&position=0&from_view=search&track=ais\">Freepik</a>\n\n4. <a href=\"https://kr.freepik.com/free-vector/people-sleeping-poses-top-view_5971402.htm#page=2&query=%EC%88%98%EB%A9%B4%20%EC%9E%90%EC%84%B8&position=34&from_view=search&track=ais\">작가 macrovector</a>\n\n5. <a href=\"https://www.freepik.com/free-vector/collection-flat-sleeping-poses_4365049.htm#page=3&query=%EC%97%8E%EB%93%9C%EB%A6%B0%20%EC%88%98%EB%A9%B4%20%EC%9E%90%EC%84%B8&position=10&from_view=search&track=ais\">Freepik</a>" +
                "\n\n\n[수면 자세 정보 출처]\n\n1. https://www.chosun.com/site/data/html_dir/2016/01/26/2016012603329.html\n\n2. https://naver.me/FKxK6hHa\n\n3. https://shop.hyundai.com/main/list?action=story_detail&seq=1658793634\n\n4. https://m.jaseng.org/news/encyclopedia_view.do?idx=1898\n\n5. https://digitalchosun.dizzo.com/site/data/html_dir/2018/10/25/2018102511426.html\n\n6. https://kormedi.com/1592199/%EB%82%B4-%EB%AA%B8-%EC%83%81%ED%83%9C%EC%97%90-%EB%A7%9E%EB%8A%94-%EC%B5%9C%EA%B3%A0%EC%9D%98-%EC%88%98%EB%A9%B4-%EC%9E%90%EC%84%B8%EB%8A%94\n\n 7. https://www.miraclenight.app/blog-ko/sleeping-position\n\n8. https://www.webmd.com/sleep-disorders/best-sleep-positions\n\n9. https://www.healthline.com/health/best-sleeping-position");
    }
}
