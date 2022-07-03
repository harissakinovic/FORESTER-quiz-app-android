package my.project.foresterApp.questions;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import my.project.foresterApp.MainActivity;
import my.project.foresterApp.R;
import my.project.foresterApp.dataBase.DatabaseHelper;
import my.project.foresterApp.dataBase.modelClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class showingQuestions extends AppCompatActivity {

    private int difficulty, level;
    private TextView answerA,answerC,answerB,answerD,question, pointsTV,ppText,texViewLevelUp, twPointsGained, hQPoints, lvlDifficulty, levelNbr;
    private DatabaseHelper dbHelper;
    private ProgressBar progress;
    private ArrayList<Integer> numbersOfQuestions=new ArrayList<Integer>();
    private List<modelClass> everyQuestion;
    private Random r;
    private int randomNumber;
    private int questionNumber;
    private String tableName="";
    private MediaPlayer mpCorrect, mpIncorrect;
    private int countNumberOfQuestion=0;
    private int points=0;
    private int pointBase=2;
    private int pointCounter=4;
    private int totalTries=4;
    private Dialog learnMoreDialog,finishLevelDialog,enterPointsDialog;
    private Button ppNextLevel,ppExitGame,submitAsw,btnSavePoints,ppfinishGame;
    private ImageView pictureQuestion, hardQPicture;
    private EditText enterAnswer,nameOfPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showing_questions);

       initialization();
       handlers(0);
    }

    public void initialization (){

        mpCorrect=MediaPlayer.create(this, R.raw.collect);
        mpIncorrect=MediaPlayer.create(this,R.raw.incorrect);

        learnMoreDialog= new Dialog(this);
        enterPointsDialog= new Dialog(this);



        Bundle bundle=getIntent().getExtras();
        difficulty= bundle.getInt("difficulty");
        level= bundle.getInt("level");
        setingUpLevel();
        finishLevelDialog=new Dialog(this);

        answerA=findViewById(R.id.textViewanswerA);
        answerB=findViewById(R.id.textViewanswerB);
        answerC=findViewById(R.id.textViewanswerC);
        answerD=findViewById(R.id.textViewanswerD);
        question=findViewById(R.id.textViewQuestion);
        pointsTV=findViewById(R.id.textViewPoints);
        pointsTV.setText("Bodovi: 0");
        progress=findViewById(R.id.progressBar);
        pictureQuestion=findViewById(R.id.imageViewQuestion);
        levelNbr= findViewById(R.id.level);
        lvlDifficulty =findViewById(R.id.levelDifficulty);





    }
    public void finishLevelD(){

        finishLevelDialog.setContentView(R.layout.next_level_popup);
        ppNextLevel=finishLevelDialog.findViewById(R.id.nextLevel);
        ppExitGame=finishLevelDialog.findViewById(R.id.exitGame);
        ppfinishGame=finishLevelDialog.findViewById(R.id.finishGame);
        texViewLevelUp=finishLevelDialog.findViewById(R.id.ppTextLevelUp);


       if(level>=5 && difficulty>=3) {

            ppNextLevel.setVisibility(View.GONE);
            ppExitGame. setVisibility(View.GONE);
            ppfinishGame.setVisibility(View.VISIBLE);

       } else {

            ppNextLevel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    level++;
                    if (level >= 5 && difficulty >= 3) {

                        ppNextLevel.setVisibility(View.GONE);

                    } else if (level == 5) {

                        countNumberOfQuestion=0;
                        numbersOfQuestions.clear();
                        setingUpLevel();
                        handlers(1000);
                        level = 0;
                        difficulty++;

                    } else if (level < 5) {

                        countNumberOfQuestion=0;
                        numbersOfQuestions.clear();
                        setingUpLevel();
                        handlers(1000);

                    } else if(level >5) {

                        level=1;
                        difficulty++;

                        countNumberOfQuestion=0;
                        numbersOfQuestions.clear();
                        setingUpLevel();
                        handlers(1000);

                    }else {
                        Toast.makeText(showingQuestions.this, "Error" , Toast.LENGTH_SHORT).show();
                    }

                    finishLevelDialog.dismiss();

                }


            });
        }

        ppExitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text="Čestitamo, broj osvojenih bodova je: "+ points;
                finishGameLevel(text);

            }
        });
        ppfinishGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text="Čestitamo, broj osvojenih bodova je: "+ points;
                finishGameLevel(text);

            }
        });

        finishLevelDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        finishLevelDialog.setCanceledOnTouchOutside(false);
        finishLevelDialog.show();

    }

    public void finishGameLevel(String text){

        finishLevelDialog.dismiss();
        enterPointsDialog.setContentView(R.layout.enter_points);
        nameOfPlayer=enterPointsDialog.findViewById(R.id.etName);
        twPointsGained=enterPointsDialog.findViewById(R.id.pointsGained);
        btnSavePoints=enterPointsDialog.findViewById(R.id.savePoints);

        twPointsGained.setText(text);


        btnSavePoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ffname=nameOfPlayer.getText().toString();
                if (ffname.isEmpty()){
                    ffname="Igrač";
                }

                String name = ffname;


                //modelResults=new modelResults(-1,name,points);
                dbHelper.addOne(name, points);

                Intent intent=new Intent(showingQuestions.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        enterPointsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        enterPointsDialog.setCanceledOnTouchOutside(false);
        enterPointsDialog.show();
    }

    public void randomNumber(){

        if(countNumberOfQuestion>=10){

            finishLevelD();
            setingUpLevel();


        } else {
            r=new Random();
            randomNumber=r.nextInt(14);
            questionNumber=randomNumber+1;

            if(numbersOfQuestions.contains(questionNumber)){

                do{
                    r=new Random();
                    randomNumber=r.nextInt(14);
                    questionNumber=randomNumber+1;

                } while (numbersOfQuestions.contains(questionNumber));
                questionSetUp();
            } else {
                questionSetUp();
            }

            countNumberOfQuestion++;
        }
    }


    private void questionSetUp() {


        if (difficulty==1)
            lvlDifficulty.setText("Easy");
        if (difficulty==2)
            lvlDifficulty.setText("Medium");


        levelNbr.setText("Nivo "+level);

        if(tableName.contains("hard")){

            setContentView(R.layout.hard_question_layout);
            submitAsw=findViewById(R.id.submitAnswer);
            enterAnswer=findViewById(R.id.etEnterAnswer);
            question=findViewById(R.id.tvQuestion);
            hQPoints=findViewById(R.id.hardQuestionPoints);
            hQPoints.setText("Bodovi: "+points);
            hardQPicture=findViewById(R.id.imageHQ);
            levelNbr= findViewById(R.id.level);
            lvlDifficulty =findViewById(R.id.levelDifficulty);
            levelNbr.setText("Nivo "+level);
            lvlDifficulty.setText("Hard");


            enterAnswer.setOnEditorActionListener(editorListener);


            numbersOfQuestions.add(questionNumber);

            dbHelper=new DatabaseHelper(showingQuestions.this);
            everyQuestion=dbHelper.getQuestion(questionNumber,tableName);
            question.setText(everyQuestion.get(0).getQuestion());

            String isTherePicture=everyQuestion.get(0).getIsPicture();

            if(isTherePicture.equals("yes")){

                hardQPicture.setVisibility(View.VISIBLE);
                hardQPicture.setImageBitmap(everyQuestion.get(0).getPicture());
            } else {
                hardQPicture.setVisibility(View.GONE);
            }

            submitAsw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    checkeHardQuestionAnswer();
//                    String answerOnQuestion=enterAnswer.getText().toString().toLowerCase();
//                    if(answerOnQuestion.isEmpty()) {
//                        enterAnswer.setError("Unesite odgovor");
//                    } else {
//                        answersInput(answerOnQuestion,enterAnswer);
//                    }
                }
            });


        } else {

        numbersOfQuestions.add(questionNumber);
        dbHelper=new DatabaseHelper(showingQuestions.this);

        everyQuestion=dbHelper.getQuestion(questionNumber,tableName);
        question.setText(everyQuestion.get(0).getQuestion());
        answerA.setText(everyQuestion.get(0).getA());
        answerB.setText(everyQuestion.get(0).getB());
        answerC.setText(everyQuestion.get(0).getC());
        answerD.setText(everyQuestion.get(0).getD());

        String isTherePicture=everyQuestion.get(0).getIsPicture();

        if(isTherePicture.equals("yes")){

            pictureQuestion.setVisibility(View.VISIBLE);
            pictureQuestion.setImageBitmap(everyQuestion.get(0).getPicture());
        }else {
            pictureQuestion.setVisibility(View.GONE);
        }

        }

    }

    private TextView.OnEditorActionListener editorListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (actionId){
                case EditorInfo
                        .IME_ACTION_NEXT:
                    checkeHardQuestionAnswer();
                break;

            }
            return false;
        }
    };

    private void checkeHardQuestionAnswer(){
        String answerOnQuestion=enterAnswer.getText().toString().toLowerCase();
        if(answerOnQuestion.isEmpty()) {
            enterAnswer.setError("Unesite odgovor");
        } else {
            answersInput(answerOnQuestion,enterAnswer);
        }
    }


    public void aClick (View view) {
        everyQuestion=dbHelper.getQuestion(questionNumber,tableName);
        String correctAnswer=everyQuestion.get(0).getA();
        answers(correctAnswer,answerA);


    }
    public void bClick (View view) {
        everyQuestion=dbHelper.getQuestion(questionNumber,tableName);
        String correctAnswer=everyQuestion.get(0).getB();
        answers(correctAnswer,answerB);


    }
    public void cClick (View view) {
        everyQuestion=dbHelper.getQuestion(questionNumber,tableName);
        String correctAnswer=everyQuestion.get(0).getC();
        answers(correctAnswer,answerC);

    }
    public void dClick (View view) {
        everyQuestion=dbHelper.getQuestion(questionNumber,tableName);
        String correctAnswer=everyQuestion.get(0).getD();
        answers(correctAnswer,answerD);
    }


    public void answers (String choosenAnser, View textView){

        everyQuestion=dbHelper.getQuestion(questionNumber,tableName);
        String correctAnswer=everyQuestion.get(0).getCorrect();

        if(choosenAnser.equals(correctAnswer)){
           answerHelper();
            textView.setBackgroundResource(R.color.correctAnswer);

        } else {
            pointCounter-=1;
            mpIncorrect.start();
            textView.setBackgroundResource(R.color.wrongAnswer);
        }

    }

    private void answerHelper(){
        mpCorrect.start();
        disableButtons(false);
        handlers(1500);
        points+=pointBase*pointCounter;
        pointsTV.setText("Bodova: "+points);
        pointCounter=4;
    }

    public void answersInput (String choosenAnser, EditText editText){

        everyQuestion=dbHelper.getQuestion(questionNumber,tableName);
        String correctAnswer=everyQuestion.get(0).getCorrect();

        if(choosenAnser.equals(correctAnswer)){

            answerHelper();
            editText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.correct_answer,0);

        } else {
            totalTries--;
            pointCounter-=1;
            mpIncorrect.start();
            editText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.wrong_answer,0);

            if(totalTries==0){
                editText.setText(correctAnswer);
                totalTries=4;
            }
        }

    }

    public void handlers(int deleayMillis){

        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                answerA.setBackgroundResource(R.color.lightGreen);
                answerB.setBackgroundResource(R.color.lightGreen);
                answerC.setBackgroundResource(R.color.lightGreen);
                answerD.setBackgroundResource(R.color.lightGreen);
            disableButtons(true);
            randomNumber();
            }

        }, deleayMillis);
    }

    private void disableButtons(boolean b) {

            answerA.setClickable(b);
            answerB.setClickable(b);
            answerC.setClickable(b);
            answerD.setClickable(b);

    }



    public void setingUpLevel() {

        if (difficulty == 1) {

            switch (level) {
                case 1:
                    tableName = "easyLevelOne";
                    break;
                case 2:
                    tableName = "easyLevelTwo";
                    break;
                case 3:
                    tableName = "easyLevelThree";
                    break;
                case 4:
                    tableName = "easyLevelFour";
                    break;
                case 5:
                    tableName = "easyLevelFive";
                    break;
                default:
                    return;
            }

        } else if (difficulty == 2) {

            switch (level) {
                case 1:
                    tableName = "mediumLevelOne";
                    break;
                case 2:
                    tableName = "mediumLevelTwo";
                    break;
                case 3:
                    tableName = "mediumLevelThree";
                    break;
                case 4:
                    tableName = "mediumLevelFour";
                    break;
                case 5:
                    tableName = "mediumLevelFive";
                    break;
                default:
                    return;
            }
        } else if (difficulty ==3) {

            switch (level) {
                case 1:
                    tableName = "hardLevelOne";
                    break;
                case 2:
                    tableName = "hardLevelTwo";
                   break;
                case 3:
                    tableName = "hardLevelThree";
                    break;
                case 4:
                    tableName = "hardLevelFour";
                    break;
                case 5:
                    tableName = "hardLevelFive";
                    break;
                default:
                    return;
            }
        } else {
            Toast.makeText(showingQuestions.this, "Problem with reading value of DIFFICULTY", Toast.LENGTH_SHORT).show();
        }

    }
}
