package my.project.foresterApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import my.project.foresterApp.credits.credits;
import my.project.foresterApp.dataBase.DatabaseHelper;
import my.project.foresterApp.newGame.newGame;
import my.project.foresterApp.results.results;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper db=new DatabaseHelper(this);
        db.getInstance(this);
    }

    public void newGame (View view)
    {

        Intent intent =new Intent(MainActivity.this, newGame.class);
        startActivity(intent);

    }
    public void results (View view)
    {
        Intent intent =new Intent(MainActivity.this, results.class);
        startActivity(intent);
    }
    public void credits (View view)
    {

        Intent intent =new Intent(MainActivity.this, credits.class);
        startActivity(intent);
    }

}