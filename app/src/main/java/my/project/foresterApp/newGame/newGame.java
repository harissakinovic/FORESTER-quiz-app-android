package my.project.foresterApp.newGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import my.project.foresterApp.MainActivity;
import my.project.foresterApp.R;
import my.project.foresterApp.easyLevel.easy;

public class newGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
    }

    public void easy (View view){

        sendData(1);
    }
    public void medium (View view){
        sendData(2);
    }
    public void hard (View view){
        sendData(3);
    }
    public void back (View view){

        Intent intent=new Intent(newGame.this, MainActivity.class);
        startActivity(intent);
    }

    private void sendData (int difficulty){
        Intent intent=new Intent(newGame.this, easy.class);
        Bundle bundle=new Bundle();
        bundle.putInt("difficulty",difficulty);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}