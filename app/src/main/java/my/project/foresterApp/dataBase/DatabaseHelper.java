package my.project.foresterApp.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "foresterQuizDB.db";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper INSTANCE;

    public DatabaseHelper(Context context) {

        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(final Context context){
        if(INSTANCE ==null)
        {
            INSTANCE = new DatabaseHelper(context);
        }

        return  INSTANCE;
    }

    public Cursor getCursor(){
        return this.getReadableDatabase().rawQuery("select * from easyLevelOne",null);
    }




    //ovdje pocinje prijasnji open helper
    private static final String databaseName="foresterQuizDB.db";
    private static final String easyLevel1="easyLevelOne";
    private static final String question="question";
    private static final String answerA="a";
    private static final String answerB="b";
    private static final String answerC="c";
    private static final String answerD="d";
    private static final String correctAnswer="correct";
    private static final String questionID="id";



//    public DatabaseHelper(@Nullable Context context ) {
//        super(context, databaseName, null, 1);
//    }

//    @Override
//    public void onCreate(SQLiteDatabase db) {
//
//        String createTabelStatement="CREATE TABLE IF NOT EXISTS "+easyLevel1+" (" +questionID+ " INTEGER PRIMARY KEY AUTOINCREMENT," +question+ " VARCHAR(100)," +answerA+ " VARCHAR(40)," +answerB+ " VARCHAR(40)," +answerC+ " VARCHAR(40)," +answerD+ " VARCHAR(40)," +correctAnswer+ " VARCHAR(40))";
//        db.execSQL(createTabelStatement);
//
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public boolean addOne(String name, int points)
    {//---read last one in table

        boolean retVal=true;
        int count=countNbrOfRows();
        SQLiteDatabase db=this.getWritableDatabase();


        if(count<10){

            retVal= addToDB(db,name,points);

        } else {

        String queryString = "SELECT * FROM results order by points desc";
        SQLiteDatabase dbResult=this.getReadableDatabase();
        Cursor cursor= dbResult.rawQuery(queryString,null);

        int idNbr=0;
        int pointsDB=0;

            if(cursor.moveToFirst()){

                do{

                    idNbr = cursor.getInt(cursor.getColumnIndex("id"));
                    pointsDB = cursor.getInt(cursor.getColumnIndex("points"));

                } while (cursor.moveToNext());

            } else {

            }



                if (points> pointsDB) {

                    String querry = "DELETE FROM results WHERE id = " + idNbr;
                    Cursor ccs = db.rawQuery(querry, null);
                    ccs.moveToFirst();

                    retVal = addToDB(db, name, points);

                    db.close();

                } else {

                }

        cursor.close();
        dbResult.close();
        }

 return retVal;
    }

    public boolean addToDB (SQLiteDatabase db,String name, int points){

        boolean retVal=true;
        ContentValues cv=new ContentValues();
        cv.put("name", name);
        cv.put("points", points);


        long insert = db.insert("results", null, cv);
        if(insert==-1){
            retVal= false;
        } else {
            retVal =true;
        }

        return retVal;
    }

    public int countNbrOfRows(){
        int retVal=0;
        String count="SELECT * FROM results";

        SQLiteDatabase dbResult=this.getReadableDatabase();
        Cursor cour=dbResult.rawQuery(count,null);

        retVal=cour.getCount();

        return retVal;
    }




    public List<modelClass> getQuestion(int nbrID, String tableName){

        List<modelClass> returnList=new ArrayList<>();
        String queryString = "SELECT * FROM "+tableName+" WHERE id = "+nbrID;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor= db.rawQuery(queryString,null);

        if(cursor.moveToFirst()){

            do{
                if(tableName.contains("hard")){

                    int questionID=cursor.getInt(0);
                    String question=cursor.getString(1);
                    String correctAnswer=cursor.getString(2);
                    String picture=cursor.getString(3);
                    byte [] urlPicture=cursor.getBlob(4);
                    Bitmap objectBitmap=null;


                    try
                    {
                        objectBitmap= BitmapFactory.decodeByteArray(urlPicture,0,urlPicture.length);

                    } catch (Exception e)
                    {

                    }


                    modelClass modelClass=new modelClass(questionID,question,null,null,null,null,correctAnswer,picture, objectBitmap);
                    returnList.add(modelClass);

                } else {
                    int questionID=cursor.getInt(0);
                    String question=cursor.getString(1);
                    String answerA=cursor.getString(2);
                    String answerB=cursor.getString(3);
                    String answerC=cursor.getString(4);
                    String answerD=cursor.getString(5);
                    String correctAnswer=cursor.getString(6);
                    String picture=cursor.getString(7);
                    byte [] urlPicture=cursor.getBlob(8);
                    Bitmap objectBitmap=null;


                    try
                    {
                        objectBitmap= BitmapFactory.decodeByteArray(urlPicture,0,urlPicture.length);

                    } catch (Exception e)
                    {

                    }


                    modelClass modelClass=new modelClass(questionID,question,answerA,answerB,answerC,answerD,correctAnswer,picture, objectBitmap);
                    returnList.add(modelClass);
                }


            } while (cursor.moveToNext());

        } else {

        }

        cursor.close();
        db.close();

        return  returnList;
    }

    public List<modelResults> getResults(){

        List<modelResults> returnList=new ArrayList<>();
        String queryString = "SELECT * FROM results order by points desc";
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor= db.rawQuery(queryString,null);
        int idNbr=0;
        String nameOF="";

        if(cursor.moveToFirst()){

            do{

                int id=cursor.getInt(0);
                    String name=cursor.getString(1);
                    int points=cursor.getInt(2);

                    modelResults modelResults=new modelResults(id,name,points);
                    returnList.add(modelResults);

                idNbr = cursor.getInt(cursor.getColumnIndex("id"));
                nameOF = cursor.getString(cursor.getColumnIndex("name"));


            } while (cursor.moveToNext());

        } else {

        }

        Log.i("id",idNbr+"");
        Log.i("name",nameOF);



        //proba
        cursor.close();
        db.close();

        return  returnList;
    }


}
