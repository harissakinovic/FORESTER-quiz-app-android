package my.project.foresterApp.easyLevel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import my.project.foresterApp.R;
import my.project.foresterApp.questions.showingQuestions;

public class easy extends AppCompatActivity {

    private int diff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);

        Bundle bundle=getIntent().getExtras();
       diff= bundle.getInt("difficulty");
    }

    public void level1(View view){

        sendData(diff,1);
    }
    public void level2(View view){
        sendData(diff,2);
    }
    public void level3(View view){
        sendData(diff,3);
    }
    public void level4(View view){
        sendData(diff,4);
    }
    public void level5(View view){
        sendData(diff,5);
    }

    private void sendData (int difficulty, int levele){

            Intent intent=new Intent(easy.this, showingQuestions.class);
            Bundle bundle=new Bundle();
            bundle.putInt("difficulty",difficulty);
            bundle.putInt("level",levele);
            intent.putExtras(bundle);
            startActivity(intent);
    }
}