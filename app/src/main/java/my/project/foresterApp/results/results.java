package my.project.foresterApp.results;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import my.project.foresterApp.R;
import my.project.foresterApp.dataBase.DatabaseHelper;
import my.project.foresterApp.dataBase.adapterRW;
import my.project.foresterApp.dataBase.modelResults;

import java.util.ArrayList;
import java.util.List;

public class results extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList <modelResults> arrayList;
    private RecyclerView.Adapter adapterRW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        recyclerView=findViewById(R.id.resultsLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList=new ArrayList<>();


        DatabaseHelper databaseHelper=new DatabaseHelper(results.this);
        List <modelResults> everyone=databaseHelper.getResults();

        adapterRW=new adapterRW(everyone,this);
        recyclerView.setAdapter(adapterRW);


        //adapter=new rwAdapter(results.this, (ArrayList<modelResults>) everyone);

        //recyclerView.setLayoutManager(new GridLayoutManager(this,2) );

        //recyclerView.setAdapter(adapter);


    }
}