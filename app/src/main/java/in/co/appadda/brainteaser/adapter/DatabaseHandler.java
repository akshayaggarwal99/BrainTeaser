package in.co.appadda.brainteaser.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.backendless.BackendlessCollection;

import java.util.ArrayList;
import java.util.List;

import in.co.appadda.brainteaser.activity.MainActivity;
import in.co.appadda.brainteaser.activity.Splash;
import in.co.appadda.brainteaser.data.api.model.PrefUtils;
import in.co.appadda.brainteaser.data.api.model.aptitude;
import in.co.appadda.brainteaser.data.api.model.logical;
import in.co.appadda.brainteaser.data.api.model.puzzles;
import in.co.appadda.brainteaser.data.api.model.riddles;

/**
 * Created by dewangankisslove on 07-03-2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private BackendlessCollection aptitudecollection;
    private BackendlessCollection logicalcollection;
    private BackendlessCollection puzzlecollection;
    private BackendlessCollection riddlecollection;
    String skip_update = "FALSE";

    int i = 0;

    private int currentPage;
    private int totalPages;

    private Context context;

    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "brainteaser.db";

    // Contacts table name
    private static final String TABLE_APTITUDE = "aptitude";
    private static final String TABLE_LOGICAL = "logical";
    private static final String TABLE_PUZZLE = "puzzles";
    private static final String TABLE_RIDDLE = "riddles";
    //private static final String TABLE_LEADERBOARD = "leaderboard";
    private static final String TABLE_ALL_QUE = "allque";
    private static final String TABLE_ATTEMPTED_QUE = "attempted";
    private static final String TABLE_CONTEST_ATTEMPTED = "contestattempted";
    private static final String TABLE_ALL_CONTEST_QUE = "contestque";

    // APTITUDE Table Columns names
    private static final String APTITUDE_ID = "_id";
    private static final String APTITUDE_QUESTIONS = "questions";
    private static final String APTITUDE_OPTION_ONE = "option_one";
    private static final String APTITUDE_OPTION_TWO = "option_two";
    private static final String APTITUDE_OPTION_THREE = "option_three";
    private static final String APTITUDE_OPTION_FOUR = "option_four";
    private static final String APTITUDE_ANSWER = "answer";
    private static final String APTITUDE_EXPLANATION = "explanation";
    private static final String APTITUDE_SET_NO = "set_no";

    // LOGICAL Table Columns names
    private static final String LOGICAL_ID = "_id";
    private static final String LOGICAL_QUESTIONS = "questions";
    private static final String LOGICAL_OPTION_ONE = "option_one";
    private static final String LOGICAL_OPTION_TWO = "option_two";
    private static final String LOGICAL_OPTION_THREE = "option_three";
    private static final String LOGICAL_OPTION_FOUR = "option_four";
    private static final String LOGICAL_ANSWER = "answer";
    private static final String LOGICAL_EXPLANATION = "explanation";
    private static final String LOGICAL_SET_NO = "set_no";

    // PUZZLE Table Columns names
    private static final String PUZZLE_ID = "_id";
    private static final String PUZZLE_QUESTIONS = "question";
    private static final String PUZZLE_ANSWER = "answer";
    private static final String PUZZLE_EXPLANATION = "solution";

    // RIDDLE Table Columns names
    private static final String RIDDLE_ID = "_id";
    private static final String RIDDLE_QUESTIONS = "question";
    private static final String RIDDLE_ANSWER = "answer";

    // Leaderboard Table Columns names
//    private static final String LEADERBOARD_USER_FLAG = "country";
//    private static final String LEADERBOARD_USER_ID = "_id";
//    private static final String LEADERBOARD_USER_NAME = "name";
//    private static final String LEADERBOARD_USER_TOTAL_POINTS = "points";
//    private static final String LEADERBOARD_QUE_TYPE_INFO = "que_type_info";
//    private static final String LEADERBOARD_USER_TOTAL_ATTEMPTED_QUE = "attemp_que";
//    private static final String LEADERBOARD_USER_TOTAL_RIGHT_ANS = "right_answer";

    // All Que Table Columns names
    private static final String ID = "_id";
    private static final String QUE_ID = "que_id";
    private static final String QUE = "que";
    private static final String ANSWER = "answer";
    private static final String EXPLANATION = "explanation";
    private static final String OPTION_ONE = "option_one";
    private static final String OPTION_TWO = "option_two";
    private static final String OPTION_THREE = "option_three";
    private static final String OPTION_FOUR = "option_four";
    private static final String QUE_POINT = "que_point";
    private static final String TOPIC = "topic";


    // Attempted Table Columns names
    private static final String ATTEMPTED_QUE_ID = "que_id";
    private static final String ATTEMPTED_QUE_TYPE = "que_type";
    private static final String ATTEMPTED_QUE_CHECKING = "que_checking";
    private static final String ATTEMPTED_QUE_POINTS = "que_points";
    private static final String USER_ID = "user_id";
    private static final String USERNAME = "username";
    private static final String ATTEMPTED_UNIQUE_KEY = "unique_key";
    private static final String ATTEMPTED_DATE = "createdAt";

    private static final String CREATE_TABLE_APTITUDE = "CREATE TABLE " + TABLE_APTITUDE + "(" + APTITUDE_ID + " INTEGER PRIMARY KEY," + APTITUDE_QUESTIONS + " TEXT," + APTITUDE_OPTION_ONE + " TEXT," + APTITUDE_OPTION_TWO + " TEXT," + APTITUDE_OPTION_THREE + " TEXT," + APTITUDE_OPTION_FOUR + " TEXT," + APTITUDE_ANSWER + " TEXT," + APTITUDE_EXPLANATION + " TEXT, " + APTITUDE_SET_NO + " INT," + " status" + " INT" + ")";
    private static final String CREATE_TABLE_LOGICAL = "CREATE TABLE " + TABLE_LOGICAL + "(" + LOGICAL_ID + " INTEGER PRIMARY KEY," + LOGICAL_QUESTIONS + " TEXT," + LOGICAL_OPTION_ONE + " TEXT," + LOGICAL_OPTION_TWO + " TEXT," + LOGICAL_OPTION_THREE + " TEXT," + LOGICAL_OPTION_FOUR + " TEXT," + LOGICAL_ANSWER + " TEXT," + LOGICAL_EXPLANATION + " TEXT, " + LOGICAL_SET_NO + " INT," + " status" + " INT" + ")";
    private static final String CREATE_TABLE_PUZZLE = "CREATE TABLE " + TABLE_PUZZLE + "(" + PUZZLE_ID + " INTEGER PRIMARY KEY," + PUZZLE_QUESTIONS + " TEXT," + PUZZLE_ANSWER + " STRING," + PUZZLE_EXPLANATION + " TEXT, status" + " INT" + ")";
    private static final String CREATE_TABLE_RIDDLE = "CREATE TABLE " + TABLE_RIDDLE + "(" + RIDDLE_ID + " INTEGER PRIMARY KEY," + RIDDLE_QUESTIONS + " TEXT," + RIDDLE_ANSWER + " TEXT, status" + " INT" + ")";

    //private static final String CREATE_TABLE_LEADERBOARD = "CREATE TABLE " + TABLE_LEADERBOARD + "(" + LEADERBOARD_USER_ID + " TEXT," + LEADERBOARD_USER_NAME + " TEXT," + LEADERBOARD_USER_FLAG + " TEXT," + LEADERBOARD_QUE_TYPE_INFO + " TEXT," + LEADERBOARD_USER_TOTAL_ATTEMPTED_QUE + " TEXT," + LEADERBOARD_USER_TOTAL_RIGHT_ANS + " TEXT," + LEADERBOARD_USER_TOTAL_POINTS + " TEXT, status" + " INT" + ")";
    private static final String CREATE_TABLE_ALL_QUE = "CREATE TABLE " + TABLE_ALL_QUE + "(" + ID + " INTEGER PRIMARY KEY," + QUE_ID + " TEXT," + QUE + " TEXT," + ANSWER + " TEXT," + EXPLANATION + " TEXT," + OPTION_ONE + " TEXT," + OPTION_TWO + " TEXT," + OPTION_THREE + " TEXT," + OPTION_FOUR + " TEXT," + QUE_POINT + " INT," + TOPIC + " TEXT, status" + " TEXT" + ")";
    private static final String CREATE_TABLE_ATTEMPTED = "CREATE TABLE " + TABLE_ATTEMPTED_QUE + "(" + ATTEMPTED_QUE_ID + " TEXT," + USER_ID + " TEXT," + ATTEMPTED_QUE_TYPE + " TEXT," + USERNAME + " TEXT," + ATTEMPTED_QUE_POINTS + " INT," + ATTEMPTED_QUE_CHECKING + " TEXT," + ATTEMPTED_DATE + " DATE," + ATTEMPTED_UNIQUE_KEY + " TEXT" + ")";
    private static final String CREATE_TABLE_CONTEST_ATTEMPTED = "CREATE TABLE " + TABLE_CONTEST_ATTEMPTED + "(" + ATTEMPTED_QUE_ID + " TEXT," + USER_ID + " TEXT," + ATTEMPTED_QUE_TYPE + " TEXT," + USERNAME + " TEXT," + ATTEMPTED_QUE_POINTS + " INT," + ATTEMPTED_QUE_CHECKING + " TEXT," + ATTEMPTED_DATE + " DATE," + ATTEMPTED_UNIQUE_KEY + " TEXT" + ")";
    private static final String CREATE_TABLE_ALL_CONTEST_QUE = "CREATE TABLE " + TABLE_ALL_CONTEST_QUE + "(" + ID + " INTEGER PRIMARY KEY," + QUE_ID + " TEXT," + QUE + " TEXT," + ANSWER + " TEXT," + OPTION_ONE + " TEXT," + OPTION_TWO + " TEXT," + OPTION_THREE + " TEXT," + OPTION_FOUR + " TEXT," + QUE_POINT + " INT, status" + " TEXT" + ")";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_APTITUDE);
        db.execSQL(CREATE_TABLE_LOGICAL);
        db.execSQL(CREATE_TABLE_PUZZLE);
        db.execSQL(CREATE_TABLE_RIDDLE);
        //db.execSQL(CREATE_TABLE_LEADERBOARD);
        db.execSQL(CREATE_TABLE_ALL_QUE);
        db.execSQL(CREATE_TABLE_ATTEMPTED);
        db.execSQL(CREATE_TABLE_ALL_CONTEST_QUE);
        db.execSQL(CREATE_TABLE_CONTEST_ATTEMPTED);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APTITUDE);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGICAL);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PUZZLE);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RIDDLE);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEADERBOARD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALL_QUE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTEMPTED_QUE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALL_QUE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTEMPTED_QUE);

        // Create tables again
        //onCreate(db);
        db.execSQL(CREATE_TABLE_ALL_QUE);
        db.execSQL(CREATE_TABLE_ATTEMPTED);
        db.execSQL(CREATE_TABLE_ALL_CONTEST_QUE);
        db.execSQL(CREATE_TABLE_CONTEST_ATTEMPTED);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    //Adding data in allque table
    public void addContestAttemptQue(String date, String uniqueKey, String queId, String userId, String name, int points, String check) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ATTEMPTED_DATE, date);
        values.put(ATTEMPTED_UNIQUE_KEY, uniqueKey);
        values.put(ATTEMPTED_QUE_ID, queId);
        values.put(USER_ID, userId);
        values.put(USERNAME, name);
        values.put(ATTEMPTED_QUE_POINTS, points);
        values.put(ATTEMPTED_QUE_CHECKING, check);
        db.insert(TABLE_CONTEST_ATTEMPTED, null, values);

        db.close(); // Closing database connection
    }

    public ArrayList<String[]> getContestAttemptedQues() {
        ArrayList<String[]> result = new ArrayList<String[]>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTEST_ATTEMPTED, null, null, null, null, null, ATTEMPTED_DATE + " DESC");
        try {
            if (cursor.moveToFirst()) {
                do {
                    String[] object = new String[6];
                    object[0] = cursor.getString(0); //queid
                    object[1] = cursor.getString(1); //userId
                    object[2] = cursor.getString(2); //username
                    object[3] = String.valueOf(cursor.getInt(3)); //point
                    object[4] = cursor.getString(4); //check
                    object[5] = cursor.getString(5); //date
                    result.add(object);

                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return result;
    }

    public boolean checkContestAttemptedQueStatus(String uniq) {
        boolean result = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.query(TABLE_CONTEST_ATTEMPTED, null, ATTEMPTED_UNIQUE_KEY + "=?", new String[]{uniq}, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                result = true;
            }
        } finally {
            cursor.close();
        }
        return result;
    }

    public void addContestAllQue(int Id, String queId, String que, String answer, int point, String optOne, String optTwo, String optThree, String optFour) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID, Id);
        values.put(QUE_ID, queId);
        values.put(QUE, que);
        values.put(ANSWER, answer);
        values.put(QUE_POINT, point);
        values.put(OPTION_ONE, optOne);
        values.put(OPTION_TWO, optTwo);
        values.put(OPTION_THREE, optThree);
        values.put(OPTION_FOUR, optFour);
        db.insert(TABLE_ALL_QUE, null, values);

        db.close(); // Closing database connection
    }

    public ArrayList<String[]> getContestAllQue() {
        ArrayList<String[]> result = new ArrayList<String[]>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ALL_CONTEST_QUE, null, null, null, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String[] object = new String[9];
                    object[0] = String.valueOf(cursor.getInt(0)); //id
                    object[1] = cursor.getString(1); //queId
                    object[2] = cursor.getString(2); //que
                    object[3] = cursor.getString(3); //ans
                    object[4] = cursor.getString(4); //optOne
                    object[5] = cursor.getString(5); //optTwo
                    object[6] = cursor.getString(6); //optThree
                    object[7] = cursor.getString(7); //optFour
                    object[8] = String.valueOf(cursor.getInt(8)); //point
                    result.add(object);

                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return result;
    }

    public void addContestAllQueStatus(String queID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", "1");
        db.update(TABLE_ALL_CONTEST_QUE, values, "que_id = '" + queID + "'", null);
    }

    public boolean checkContestAllQueStatus() {
        boolean result = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.query(TABLE_ALL_CONTEST_QUE, null, "status" + "=?", new String[]{"1"}, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                result = true;
            }
        } finally {
            cursor.close();
        }
        return result;
    }

    public void removeContestAllQue() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALL_CONTEST_QUE, "status" + "=?", new String[]{"1"});
    }

    public void removeContestAttemptedQues() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTEST_ATTEMPTED, null, null);
    }

    public void addAttemptedQue(String date, String uniqueKey, String queId, String userId, String queType, String name, int points, String check) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ATTEMPTED_DATE, date);
        values.put(ATTEMPTED_UNIQUE_KEY, uniqueKey);
        values.put(ATTEMPTED_QUE_ID, queId);
        values.put(USER_ID, userId);
        values.put(ATTEMPTED_QUE_TYPE, queType);
        values.put(USERNAME, name);
        values.put(ATTEMPTED_QUE_POINTS, points);
        values.put(ATTEMPTED_QUE_CHECKING, check);
        db.insert(TABLE_ATTEMPTED_QUE, null, values);

        db.close(); // Closing database connection
    }

    public ArrayList<String[]> getAttemptedQues() {
        ArrayList<String[]> result = new ArrayList<String[]>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ATTEMPTED_QUE, null, null, null, null, null, ATTEMPTED_DATE + " DESC");
        try {
            if (cursor.moveToFirst()) {
                do {
                    String[] object = new String[7];
                    object[0] = cursor.getString(0); //queid
                    object[1] = cursor.getString(1); //userId
                    object[2] = cursor.getString(2); //queType
                    object[3] = cursor.getString(3); //username
                    object[4] = String.valueOf(cursor.getInt(4)); //point
                    object[5] = cursor.getString(5); //check
                    object[6] = cursor.getString(6); //date
                    result.add(object);

                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return result;
    }

    public boolean checkAttemptedQueStatus(String uniq) {
        boolean result = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.query(TABLE_ATTEMPTED_QUE, null, ATTEMPTED_UNIQUE_KEY + "=?", new String[]{uniq}, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                result = true;
            }
        } finally {
            cursor.close();
        }
        return result;
    }

    //Adding data in allque table
    public void addAllQue(int Id, String queId, String que, String answer, String explain, String topic, int point, String optOne, String optTwo, String optThree, String optFour) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID, Id);
        values.put(QUE_ID, queId);
        values.put(QUE, que);
        values.put(ANSWER, answer);
        values.put(EXPLANATION, explain);
        values.put(TOPIC, topic);
        values.put(QUE_POINT, point);
        values.put(OPTION_ONE, optOne);
        values.put(OPTION_TWO, optTwo);
        values.put(OPTION_THREE, optThree);
        values.put(OPTION_FOUR, optFour);
        db.insert(TABLE_ALL_QUE, null, values);

        db.close(); // Closing database connection
    }

    public ArrayList<String[]> getAllQue(String topicName) {
        ArrayList<String[]> result = new ArrayList<String[]>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ALL_QUE, null, TOPIC + "=?", new String[]{topicName}, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String[] object = new String[11];
                    object[0] = String.valueOf(cursor.getInt(0)); //id
                    object[1] = cursor.getString(1); //queId
                    object[2] = cursor.getString(2); //que
                    object[3] = cursor.getString(3); //ans
                    object[4] = cursor.getString(4); //explana..
                    object[5] = cursor.getString(5); //optOne
                    object[6] = cursor.getString(6); //optTwo
                    object[7] = cursor.getString(7); //optThree
                    object[8] = cursor.getString(8); //optFour
                    object[9] = String.valueOf(cursor.getInt(9)); //point
                    object[10] = cursor.getString(10); //topic
                    result.add(object);

                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return result;
    }

    public void addAllQueStatus(String queID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", "1");
        db.update(TABLE_ALL_QUE, values, "que_id = '" + queID + "'", null);
    }

    public boolean checkAllQueStatus() {
        boolean result = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.query(TABLE_ALL_QUE, null, "status" + "=?", new String[]{"1"}, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                result = true;
            }
        } finally {
            cursor.close();
        }
        return result;
    }

    public void removeAllQue() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALL_QUE, "status" + "=?", new String[]{"1"});
    }

    public void removeAttemptedQues() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ATTEMPTED_QUE, null, null);
    }


    //Adding new aptitude
    public void addAptitude() {
        String[] aptitude_id, aptitude_que, aptitude_option_one, aptitude_option_two, aptitude_option_three, aptitude_option_four, aptitude_answer, aptitude_explanation, aptitude_set_no, aptitude_user_status;

        skip_update = PrefUtils.getFromPrefs(context, "skip_update", "FALSE");
        if (skip_update.contentEquals("TRUTH")) {
            aptitudecollection = MainActivity.getAptitudeCollection();
        } else {
            aptitudecollection = Splash.getAptitudeCollection();
        }


        currentPage = 1;

        aptitude_id = new String[aptitudecollection.getCurrentPage().size()];
        aptitude_que = new String[aptitudecollection.getCurrentPage().size()];
        aptitude_option_one = new String[aptitudecollection.getCurrentPage().size()];
        aptitude_option_two = new String[aptitudecollection.getCurrentPage().size()];
        aptitude_option_three = new String[aptitudecollection.getCurrentPage().size()];
        aptitude_option_four = new String[aptitudecollection.getCurrentPage().size()];
        aptitude_answer = new String[aptitudecollection.getCurrentPage().size()];
        aptitude_explanation = new String[aptitudecollection.getCurrentPage().size()];
        aptitude_set_no = new String[aptitudecollection.getCurrentPage().size()];

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for (int k = 0; k < aptitudecollection.getCurrentPage().size(); k++) {
            aptitude_id[k] = String.valueOf(((aptitude) aptitudecollection.getCurrentPage().get(k)).get_id());
            aptitude_que[k] = String.valueOf(((aptitude) aptitudecollection.getCurrentPage().get(k)).getQuestions());
            aptitude_option_one[k] = String.valueOf(((aptitude) aptitudecollection.getCurrentPage().get(k)).getOption_one());
            aptitude_option_two[k] = String.valueOf(((aptitude) aptitudecollection.getCurrentPage().get(k)).getOption_two());
            aptitude_option_three[k] = String.valueOf(((aptitude) aptitudecollection.getCurrentPage().get(k)).getOption_three());
            aptitude_option_four[k] = String.valueOf(((aptitude) aptitudecollection.getCurrentPage().get(k)).getOption_four());
            aptitude_answer[k] = String.valueOf(((aptitude) aptitudecollection.getCurrentPage().get(k)).getAnswer());
            aptitude_explanation[k] = String.valueOf(((aptitude) aptitudecollection.getCurrentPage().get(k)).getExplanation());
            aptitude_set_no[k] = String.valueOf(((aptitude) aptitudecollection.getCurrentPage().get(k)).getSet_no());

            values.put(APTITUDE_ID, aptitude_id[k]);
            values.put(APTITUDE_QUESTIONS, aptitude_que[k]);
            values.put(APTITUDE_OPTION_ONE, aptitude_option_one[k]);
            values.put(APTITUDE_OPTION_TWO, aptitude_option_two[k]);
            values.put(APTITUDE_OPTION_THREE, aptitude_option_three[k]);
            values.put(APTITUDE_OPTION_FOUR, aptitude_option_four[k]);
            values.put(APTITUDE_ANSWER, aptitude_answer[k]);
            values.put(APTITUDE_EXPLANATION, aptitude_explanation[k]);
            values.put(APTITUDE_SET_NO, aptitude_set_no[k]);
            values.put("status", 0);
            db.insert(TABLE_APTITUDE, null, values);
            //           INSERT_APTITUDE = "INSERT INTO "+TABLE_APTITUDE+" ("+APTITUDE_ID+","+APTITUDE_QUESTIONS+","+APTITUDE_OPTION_ONE+","+APTITUDE_OPTION_TWO+","+APTITUDE_OPTION_THREE+","+APTITUDE_OPTION_FOUR+","+APTITUDE_ANSWER+","+APTITUDE_EXPLANATION+") VALUES ("+"'"+aptitude_id[k]+"','"+aptitude_que[k]+"','"+aptitude_option_one+"','"+aptitude_option_two+"','"+aptitude_option_three+"','"+aptitude_option_four+"','"+aptitude_answer+"','"+aptitude_explanation+"')";
            //           db.rawQuery(INSERT_APTITUDE,null);
        }

        // Inserting Row

        db.close(); // Closing database connection
    }

    //Adding new logical
    public void addLogical() {
        String[] logical_id, logical_que, logical_option_one, logical_option_two, logical_option_three, logical_option_four, logical_answer, logical_explanation, logical_set_no;

        skip_update = PrefUtils.getFromPrefs(context, "skip_update", "FALSE");
        if (skip_update.contentEquals("TRUTH")) {
            logicalcollection = MainActivity.getLogicalCollection();
        } else {
            logicalcollection = Splash.getLogicalCollection();
        }

        currentPage = 1;

        logical_id = new String[logicalcollection.getCurrentPage().size()];
        logical_que = new String[logicalcollection.getCurrentPage().size()];
        logical_option_one = new String[logicalcollection.getCurrentPage().size()];
        logical_option_two = new String[logicalcollection.getCurrentPage().size()];
        logical_option_three = new String[logicalcollection.getCurrentPage().size()];
        logical_option_four = new String[logicalcollection.getCurrentPage().size()];
        logical_answer = new String[logicalcollection.getCurrentPage().size()];
        logical_explanation = new String[logicalcollection.getCurrentPage().size()];
        logical_set_no = new String[logicalcollection.getCurrentPage().size()];

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        for (int k = 0; k < logicalcollection.getCurrentPage().size(); k++) {
            logical_id[k] = String.valueOf(((logical) logicalcollection.getCurrentPage().get(k)).get_id());
            logical_que[k] = String.valueOf(((logical) logicalcollection.getCurrentPage().get(k)).getQuestions());
            logical_option_one[k] = String.valueOf(((logical) logicalcollection.getCurrentPage().get(k)).getOption_one());
            logical_option_two[k] = String.valueOf(((logical) logicalcollection.getCurrentPage().get(k)).getOption_two());
            logical_option_three[k] = String.valueOf(((logical) logicalcollection.getCurrentPage().get(k)).getOption_three());
            logical_option_four[k] = String.valueOf(((logical) logicalcollection.getCurrentPage().get(k)).getOption_four());
            logical_answer[k] = String.valueOf(((logical) logicalcollection.getCurrentPage().get(k)).getAnswer());
            logical_explanation[k] = String.valueOf(((logical) logicalcollection.getCurrentPage().get(k)).getExplanation());
            logical_set_no[k] = String.valueOf(((logical) logicalcollection.getCurrentPage().get(k)).getSet_no());

            values.put(LOGICAL_ID, logical_id[k]);
            values.put(LOGICAL_QUESTIONS, logical_que[k]);
            values.put(LOGICAL_OPTION_ONE, logical_option_one[k]);
            values.put(LOGICAL_OPTION_TWO, logical_option_two[k]);
            values.put(LOGICAL_OPTION_THREE, logical_option_three[k]);
            values.put(LOGICAL_OPTION_FOUR, logical_option_four[k]);
            values.put(LOGICAL_ANSWER, logical_answer[k]);
            values.put(LOGICAL_EXPLANATION, logical_explanation[k]);
            values.put(LOGICAL_SET_NO, logical_set_no[k]);
            values.put("status", 0);
            db.insert(TABLE_LOGICAL, null, values);
        }

        // Inserting Row

        db.close(); // Closing database connection
    }

    //Adding new puzzle
    public void addPuzzles() {
        String[] puzzle_id, puzzle_que, puzzle_answer, puzzle_explanation;

        skip_update = PrefUtils.getFromPrefs(context, "skip_update", "FALSE");
        if (skip_update.contentEquals("TRUTH")) {
            puzzlecollection = MainActivity.getPuzzleCollection();
        } else {
            puzzlecollection = Splash.getPuzzleCollection();
        }

        currentPage = 1;

        puzzle_id = new String[puzzlecollection.getCurrentPage().size()];
        puzzle_que = new String[puzzlecollection.getCurrentPage().size()];
        puzzle_answer = new String[puzzlecollection.getCurrentPage().size()];
        puzzle_explanation = new String[puzzlecollection.getCurrentPage().size()];

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        for (int k = 0; k < puzzlecollection.getCurrentPage().size(); k++) {
            puzzle_id[k] = String.valueOf(((puzzles) puzzlecollection.getCurrentPage().get(k)).get_id());
            puzzle_que[k] = String.valueOf(((puzzles) puzzlecollection.getCurrentPage().get(k)).getQuestion());
            puzzle_answer[k] = String.valueOf(((puzzles) puzzlecollection.getCurrentPage().get(k)).getAnswer());
            puzzle_explanation[k] = String.valueOf(((puzzles) puzzlecollection.getCurrentPage().get(k)).getSolution());

            values.put(PUZZLE_ID, puzzle_id[k]);
            values.put(PUZZLE_QUESTIONS, puzzle_que[k]);
            values.put(PUZZLE_ANSWER, puzzle_answer[k]);
            values.put(PUZZLE_EXPLANATION, puzzle_explanation[k]);
            values.put("status", 0);
            db.insert(TABLE_PUZZLE, null, values);
        }

        // Inserting Row

        db.close(); // Closing database connection
    }

    //Adding new riddle
    public void addRiddle() {
        String[] riddle_id, riddle_que, riddle_answer;

        skip_update = PrefUtils.getFromPrefs(context, "skip_update", "FALSE");
        if (skip_update.contentEquals("TRUTH")) {
            riddlecollection = MainActivity.getRiddleCollection();
        } else {
            riddlecollection = Splash.getRiddleCollection();
        }

        currentPage = 1;

        riddle_id = new String[riddlecollection.getCurrentPage().size()];
        riddle_que = new String[riddlecollection.getCurrentPage().size()];
        riddle_answer = new String[riddlecollection.getCurrentPage().size()];

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        for (int k = 0; k < riddlecollection.getCurrentPage().size(); k++) {
            riddle_id[k] = String.valueOf(((riddles) riddlecollection.getCurrentPage().get(k)).get_id());
            riddle_que[k] = String.valueOf(((riddles) riddlecollection.getCurrentPage().get(k)).getQuestion());
            riddle_answer[k] = String.valueOf(((riddles) riddlecollection.getCurrentPage().get(k)).getSolution());

            values.put(RIDDLE_ID, riddle_id[k]);
            values.put(RIDDLE_QUESTIONS, riddle_que[k]);
            values.put(RIDDLE_ANSWER, riddle_answer[k]);
            values.put("status", 0);
            db.insert(TABLE_RIDDLE, null, values);
        }
        // Inserting Row

        db.close(); // Closing database connection
    }

    //
    // Getting single aptitude
    public List<String> getAptitude(int i, int j) {
        List<String> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + TABLE_APTITUDE + " WHERE set_no = " + i + " AND _id = " + j;

        Cursor cursor = db.rawQuery(select, null);

//        Cursor cursor = db.query(TABLE_APTITUDE, new String[] { APTITUDE_QUESTIONS,
//                        APTITUDE_OPTION_ONE, APTITUDE_OPTION_TWO,APTITUDE_OPTION_THREE,APTITUDE_OPTION_FOUR }, APTITUDE_ID + "=?",
//                new String[] { String.valueOf(1) }, null, null, null, null);
        try {
            if (cursor != null)
                cursor.moveToFirst();
            result.add(cursor.getString(1));
            result.add(cursor.getString(2));
            result.add(cursor.getString(3));
            result.add(cursor.getString(4));
            result.add(cursor.getString(5));
            result.add(cursor.getString(6));
            result.add(cursor.getString(7));
        } finally {
            cursor.close();
        }

        return result;
    }

    // Getting single logical
    public List<String> getLogical(int i, int j) {
        List<String> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + TABLE_LOGICAL + " WHERE set_no = " + i + " AND _id = " + j;

        Cursor cursor = db.rawQuery(select, null);
        try {
            if (cursor != null)
                cursor.moveToFirst();
            result.add(cursor.getString(1));
            result.add(cursor.getString(2));
            result.add(cursor.getString(3));
            result.add(cursor.getString(4));
            result.add(cursor.getString(5));
            result.add(cursor.getString(6));
            result.add(cursor.getString(7));
        } finally {
            cursor.close();
        }

        return result;
    }

    // Getting single puzzle
    public Cursor getPuzzle(int i) {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + TABLE_PUZZLE + " WHERE _id = " + i;

        Cursor cursor = db.rawQuery(select, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor getRiddle(int i) {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + TABLE_RIDDLE + " WHERE _id = " + i;

        Cursor cursor = db.rawQuery(select, null);
        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    public boolean checkPuzzleStatus(int id) {
        boolean result = false;
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + TABLE_PUZZLE + " WHERE status != 0 AND _id = " + id;
        Cursor cursor = db.rawQuery(countQuery, null);
        try {
            if (cursor.moveToFirst()) {
                result = true;
            }
        } finally {
            cursor.close();
        }
        return result;
    }

    public boolean checkRiddleStatus(int id) {
        boolean result = false;
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + TABLE_RIDDLE + " WHERE status != 0 AND _id = " + id;
        Cursor cursor = db.rawQuery(countQuery, null);
        try {
            if (cursor.moveToFirst()) {
                result = true;
            }
        } finally {
            cursor.close();
        }
        return result;
    }

    public boolean checkAptitudeStatus(int id, int setNo) {
        boolean result = false;
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + TABLE_APTITUDE + " WHERE status != 0 AND _id = " + id + " AND set_no = " + setNo;
        Cursor cursor = db.rawQuery(countQuery, null);
        try {
            if (cursor.moveToFirst()) {
                result = true;
            }
        } finally {
            cursor.close();
        }
        return result;
    }

    public boolean checkLogicalStatus(int id, int setNo) {
        boolean result = false;
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + TABLE_LOGICAL + " WHERE status != 0 AND _id = " + id + " AND set_no = " + setNo;
        Cursor cursor = db.rawQuery(countQuery, null);
        try {
            if (cursor.moveToFirst()) {
                result = true;
            }
        } finally {
            cursor.close();
        }
        return result;
    }

    //
//    // Getting All Contacts
//    public List<Contact> getAllContacts() {
//        List<Contact> contactList = new ArrayList<Contact>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Contact contact = new Contact();
//                contact.setID(Integer.parseInt(cursor.getString(0)));
//                contact.setName(cursor.getString(1));
//                contact.setPhoneNumber(cursor.getString(2));
//                // Adding contact to list
//                contactList.add(contact);
//            } while (cursor.moveToNext());
//        }
//
//        // return contact list
//        return contactList;
//    }
//
//    // Updating single contact
//    public int updateContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, contact.getName());
//        values.put(KEY_PH_NO, contact.getPhoneNumber());
//
//        // updating row
//        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//    }
//
//
    // Getting aptitude Count
    public int getAptitudeCount() {
        String countQuery = "SELECT  * FROM " + TABLE_APTITUDE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public int getAptitudeStatusCount() {
        String countQuery = "SELECT  * FROM " + TABLE_APTITUDE + " WHERE status != 0";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public int getAptitudeUserStatusCount() {
        float count = getAptitudeStatusCount() * 100 / getAptitudeCount();

        // return count
        return Math.round(count);
    }

    public int getAptitudeSetStatusCount(int j) {
        String countQuery = "SELECT  * FROM " + TABLE_APTITUDE + " WHERE status != 0 AND set_no = " + j;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public int getLogicalStatusCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGICAL + " WHERE status != 0";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public int getLogicalSetStatusCount(int j) {
        String countQuery = "SELECT  * FROM " + TABLE_LOGICAL + " WHERE status != 0 AND set_no = " + j;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public int getLogicalUserStatusCount() {
        float count = getLogicalStatusCount() * 100 / getLogicalCount();

        // return count
        return Math.round(count);
    }

    public int getPuzzleStatusCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PUZZLE + " WHERE status != 0";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public int getPuzzleUserStatusCount() {
        float count = getPuzzleStatusCount() * 100 / getPuzzleCount();

        // return count
        return Math.round(count);
    }

    public int getRiddleStatusCount() {
        String countQuery = "SELECT  * FROM " + TABLE_RIDDLE + " WHERE status != 0";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public int getRiddleUserStatusCount() {
        float count = getRiddleStatusCount() * 100 / getRiddleCount();

        // return count
        return Math.round(count);
    }

    // Getting puzzle Count
    public int getPuzzleCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PUZZLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();


        cursor.close();

        // return count
        return count;
    }

    // Getting logical Count
    public int getLogicalCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGICAL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    // Getting riddle Count
    public int getRiddleCount() {
        String countQuery = "SELECT  * FROM " + TABLE_RIDDLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public void addAptitudeStatusCount(int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", 1);
        db.update(TABLE_APTITUDE, values, "_id = " + i, null);
    }

    public void addLogicalStatusCount(int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", 1);
        db.update(TABLE_LOGICAL, values, "_id = " + i, null);
    }

    public void addPuzzleStatusCount(int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", 1);
        db.update(TABLE_PUZZLE, values, "_id = " + i, null);
    }

    public void addRiddleStatusCount(int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", 1);
        db.update(TABLE_RIDDLE, values, "_id = " + i, null);
    }

//    public void addLeaderboard(String userId, String username, String queTypeInfo, String totalAtmptQue, String totalRightAns, String totalPoints) {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put(LEADERBOARD_USER_ID, userId);
//        values.put(LEADERBOARD_USER_NAME, username);
//        values.put(LEADERBOARD_QUE_TYPE_INFO, queTypeInfo);
//        values.put(LEADERBOARD_USER_TOTAL_ATTEMPTED_QUE, totalAtmptQue);
//        values.put(LEADERBOARD_USER_TOTAL_RIGHT_ANS, totalRightAns);
//        values.put(LEADERBOARD_USER_TOTAL_POINTS, totalPoints);
//        db.insert(TABLE_LEADERBOARD, null, values);
//
//        db.close(); // Closing database connection
//    }

//    public ArrayList<String[]> getLeaderboardInfo() {
//        ArrayList<String[]> result = new ArrayList<String[]>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_LEADERBOARD, null, null, null, null, null, null);
//        if (cursor.moveToFirst()) {
//            do {
//                String[] object = new String[6];
//                object[0] = cursor.getString(0);
//                object[1] = cursor.getString(1);
//                object[2] = cursor.getString(3);
//                object[3] = cursor.getString(4);
//                object[4] = cursor.getString(5);
//                object[5] = cursor.getString(6);
//                result.add(object);
//
//            } while (cursor.moveToNext());
//        }
//        return result;
//    }

//    public boolean checkUserLeaderboard(String id) {
//        boolean result = false;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor;
//        cursor = db.query(TABLE_LEADERBOARD, null, LEADERBOARD_USER_ID + "=?", new String[]{id}, null, null, null);
//        try {
//            if (cursor.moveToFirst()) {
//                result = true;
//            }
//        } finally {
//            cursor.close();
//        }
//        return result;
//    }
//
//    public void removeUserLeaderboard(String id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_LEADERBOARD, LEADERBOARD_USER_ID + "=?", new String[]{id});
//    }

}
