package in.co.appadda.brainteaser.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.backendless.BackendlessCollection;

import in.co.appadda.brainteaser.activity.Splash;
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

    private int currentPage;
    private int totalPages;

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "brainteaser.db";

    // Contacts table name
    private static final String TABLE_APTITUDE = "aptitude";
    private static final String TABLE_LOGICAL = "logical";
    private static final String TABLE_PUZZLE = "puzzles";
    private static final String TABLE_RIDDLE = "riddles";

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

    private static final String CREATE_TABLE_APTITUDE = "CREATE TABLE " + TABLE_APTITUDE + "(" + APTITUDE_ID + " INTEGER PRIMARY KEY," + APTITUDE_QUESTIONS + " TEXT," + APTITUDE_OPTION_ONE + " TEXT," + APTITUDE_OPTION_TWO + " TEXT," + APTITUDE_OPTION_THREE + " TEXT," + APTITUDE_OPTION_FOUR + " TEXT," + APTITUDE_ANSWER + " TEXT," + APTITUDE_EXPLANATION + " TEXT, " + APTITUDE_SET_NO + " INT," + " status" + " INT" + ")";
    private static final String CREATE_TABLE_LOGICAL = "CREATE TABLE " + TABLE_LOGICAL + "(" + LOGICAL_ID + " INTEGER PRIMARY KEY," + LOGICAL_QUESTIONS + " TEXT," + LOGICAL_OPTION_ONE + " TEXT," + LOGICAL_OPTION_TWO + " TEXT," + LOGICAL_OPTION_THREE + " TEXT," + LOGICAL_OPTION_FOUR + " TEXT," + LOGICAL_ANSWER + " TEXT," + LOGICAL_EXPLANATION + " TEXT, " + LOGICAL_SET_NO + " INT," + " status" + " INT" + ")";
    private static final String CREATE_TABLE_PUZZLE = "CREATE TABLE " + TABLE_PUZZLE + "(" + PUZZLE_ID + " INTEGER PRIMARY KEY," + PUZZLE_QUESTIONS + " TEXT," + PUZZLE_ANSWER + " STRING," + PUZZLE_EXPLANATION + " TEXT, status" + " INT" + ")";
    private static final String CREATE_TABLE_RIDDLE = "CREATE TABLE " + TABLE_RIDDLE + "(" + RIDDLE_ID + " INTEGER PRIMARY KEY," + RIDDLE_QUESTIONS + " TEXT," + RIDDLE_ANSWER + " TEXT, status" + " INT" + ")";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_APTITUDE);
        db.execSQL(CREATE_TABLE_LOGICAL);
        db.execSQL(CREATE_TABLE_PUZZLE);
        db.execSQL(CREATE_TABLE_RIDDLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APTITUDE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGICAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PUZZLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RIDDLE);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    //Adding new aptitude
    public void addAptitude() {
        String[] aptitude_id, aptitude_que, aptitude_option_one, aptitude_option_two, aptitude_option_three, aptitude_option_four, aptitude_answer, aptitude_explanation, aptitude_set_no, aptitude_user_status;

        aptitudecollection = Splash.getAptitudeCollection();
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
        aptitude_user_status = new String[aptitudecollection.getCurrentPage().size()];

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

            values.put(APTITUDE_QUESTIONS, aptitude_que[k]);
            values.put(APTITUDE_OPTION_ONE, aptitude_option_one[k]);
            values.put(APTITUDE_OPTION_TWO, aptitude_option_two[k]);
            values.put(APTITUDE_OPTION_THREE, aptitude_option_three[k]);
            values.put(APTITUDE_OPTION_FOUR, aptitude_option_four[k]);
            values.put(APTITUDE_ANSWER, aptitude_answer[k]);
            values.put(APTITUDE_EXPLANATION, aptitude_explanation[k]);
            values.put(APTITUDE_SET_NO, aptitude_set_no[k]);
            values.put(APTITUDE_ID, aptitude_id[k]);
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

        logicalcollection = Splash.getLogicalCollection();
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

            values.put(LOGICAL_QUESTIONS, logical_que[k]);
            values.put(LOGICAL_OPTION_ONE, logical_option_one[k]);
            values.put(LOGICAL_OPTION_TWO, logical_option_two[k]);
            values.put(LOGICAL_OPTION_THREE, logical_option_three[k]);
            values.put(LOGICAL_OPTION_FOUR, logical_option_four[k]);
            values.put(LOGICAL_ANSWER, logical_answer[k]);
            values.put(LOGICAL_EXPLANATION, logical_explanation[k]);
            values.put(LOGICAL_ID, logical_id[k]);
            values.put(LOGICAL_SET_NO, logical_set_no[k]);
            db.insert(TABLE_LOGICAL, null, values);
        }

        // Inserting Row

        db.close(); // Closing database connection
    }

    //Adding new puzzle
    public void addPuzzles() {
        String[] puzzle_id, puzzle_que, puzzle_answer, puzzle_explanation;

        puzzlecollection = Splash.getPuzzleCollection();
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

            values.put(PUZZLE_QUESTIONS, puzzle_que[k]);
            values.put(PUZZLE_ANSWER, puzzle_answer[k]);
            values.put(PUZZLE_EXPLANATION, puzzle_explanation[k]);
            values.put(PUZZLE_ID, puzzle_id[k]);
            db.insert(TABLE_PUZZLE, null, values);
        }

        // Inserting Row

        db.close(); // Closing database connection
    }

    //Adding new riddle
    public void addRiddle() {
        String[] riddle_id, riddle_que, riddle_answer;

        riddlecollection = Splash.getRiddleCollection();
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

            values.put(RIDDLE_QUESTIONS, riddle_que[k]);
            values.put(RIDDLE_ANSWER, riddle_answer[k]);
            values.put(RIDDLE_ID, riddle_id[k]);
            db.insert(TABLE_RIDDLE, null, values);
        }
        // Inserting Row

        db.close(); // Closing database connection
    }

    //
    // Getting single aptitude
    public Cursor getAptitude(int i, int j) {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + TABLE_APTITUDE + " WHERE set_no = " + i + " AND _id = " + j;

        Cursor cursor = db.rawQuery(select, null);

//        Cursor cursor = db.query(TABLE_APTITUDE, new String[] { APTITUDE_QUESTIONS,
//                        APTITUDE_OPTION_ONE, APTITUDE_OPTION_TWO,APTITUDE_OPTION_THREE,APTITUDE_OPTION_FOUR }, APTITUDE_ID + "=?",
//                new String[] { String.valueOf(1) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
    }

    // Getting single logical
    public Cursor getLogical(int i, int j) {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + TABLE_LOGICAL + " WHERE set_no = " + i + " AND _id = " + j;

        Cursor cursor = db.rawQuery(select, null);
        if (cursor != null)
            cursor.moveToFirst();

        return cursor;
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

    public int getPuzzleStatusCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PUZZLE + " WHERE status != 0";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
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

    // Getting puzzle Count
    public int getPuzzleCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PUZZLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count =cursor.getCount();


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


}
