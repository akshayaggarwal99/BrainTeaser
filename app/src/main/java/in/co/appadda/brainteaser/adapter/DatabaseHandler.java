package in.co.appadda.brainteaser.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.backendless.BackendlessCollection;

import in.co.appadda.brainteaser.activity.Splash;
import in.co.appadda.brainteaser.data.api.model.aptitude;
import in.co.appadda.brainteaser.data.api.model.puzzles;

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
    private String[] items;

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

    // APTITUDE Table Columns names
    private static final String LOGICAL_ID = "_id";
    private static final String LOGICAL_QUESTIONS = "questions";
    private static final String LOGICAL_OPTION_ONE = "option_one";
    private static final String LOGICAL_OPTION_TWO = "option_two";
    private static final String LOGICAL_OPTION_THREE = "option_three";
    private static final String LOGICAL_OPTION_FOUR = "option_four";
    private static final String LOGICAL_ANSWER = "answer";
    private static final String LOGICAL_EXPLANATION = "explanation";

    // PUZZLE Table Columns names
    private static final String PUZZLE_ID = "_id";
    private static final String PUZZLE_QUESTIONS = "questions";
    private static final String PUZZLE_ANSWER = "answer";
    private static final String PUZZLE_EXPLANATION = "solution";

    // RIDDLE Table Columns names
    private static final String RIDDLE_ID = "_id";
    private static final String RIDDLE_QUESTIONS = "questions";
    private static final String RIDDLE_ANSWER = "answer";

    private static final String CREATE_TABLE_APTITUDE = "CREATE TABLE " + TABLE_APTITUDE + "(" + APTITUDE_ID + " INTEGER PRIMARY KEY," + APTITUDE_QUESTIONS + " TEXT," + APTITUDE_OPTION_ONE + " TEXT," + APTITUDE_OPTION_TWO + " TEXT," + APTITUDE_OPTION_THREE + " TEXT," + APTITUDE_OPTION_FOUR + " TEXT," + APTITUDE_ANSWER + " TEXT," + APTITUDE_EXPLANATION + " TEXT" + ")";
    private static final String CREATE_TABLE_LOGICAL = "CREATE TABLE " + TABLE_LOGICAL + "(" + LOGICAL_ID + " INTEGER PRIMARY KEY," + LOGICAL_QUESTIONS + " TEXT," + LOGICAL_OPTION_ONE + " TEXT," + LOGICAL_OPTION_TWO + " TEXT," + LOGICAL_OPTION_THREE + " TEXT," + LOGICAL_OPTION_FOUR + " TEXT," + LOGICAL_ANSWER + " TEXT," + LOGICAL_EXPLANATION + " TEXT" + ")";
    private static final String CREATE_TABLE_PUZZLE = "CREATE TABLE " + TABLE_PUZZLE + "(" + PUZZLE_ID + " INTEGER PRIMARY KEY," + PUZZLE_QUESTIONS + " TEXT," + PUZZLE_ANSWER + " TEXT," + PUZZLE_EXPLANATION + " TEXT" + ")";
    private static final String CREATE_TABLE_RIDDLE = "CREATE TABLE " + TABLE_RIDDLE + "(" + RIDDLE_ID + " INTEGER PRIMARY KEY," + RIDDLE_QUESTIONS + " TEXT," + RIDDLE_ANSWER + " TEXT" + ")";

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
        String[] aptitude_id, aptitude_que, aptitude_option_one, aptitude_option_two, aptitude_option_three, aptitude_option_four, aptitude_answer, aptitude_explanation;

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

            values.put(APTITUDE_QUESTIONS, aptitude_que[k]);
            values.put(APTITUDE_OPTION_ONE, aptitude_option_one[k]);
            values.put(APTITUDE_OPTION_TWO, aptitude_option_two[k]);
            values.put(APTITUDE_OPTION_THREE, aptitude_option_three[k]);
            values.put(APTITUDE_OPTION_FOUR, aptitude_option_four[k]);
            values.put(APTITUDE_ANSWER, aptitude_answer[k]);
            values.put(APTITUDE_EXPLANATION, aptitude_explanation[k]);
            values.put(APTITUDE_ID, aptitude_id[k]);
            db.insert(TABLE_APTITUDE, null, values);
            //           INSERT_APTITUDE = "INSERT INTO "+TABLE_APTITUDE+" ("+APTITUDE_ID+","+APTITUDE_QUESTIONS+","+APTITUDE_OPTION_ONE+","+APTITUDE_OPTION_TWO+","+APTITUDE_OPTION_THREE+","+APTITUDE_OPTION_FOUR+","+APTITUDE_ANSWER+","+APTITUDE_EXPLANATION+") VALUES ("+"'"+aptitude_id[k]+"','"+aptitude_que[k]+"','"+aptitude_option_one+"','"+aptitude_option_two+"','"+aptitude_option_three+"','"+aptitude_option_four+"','"+aptitude_answer+"','"+aptitude_explanation+"')";
            //           db.rawQuery(INSERT_APTITUDE,null);
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

    //Adding new puzzle
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
            riddle_id[k] = String.valueOf(((puzzles) riddlecollection.getCurrentPage().get(k)).get_id());
            riddle_que[k] = String.valueOf(((puzzles) riddlecollection.getCurrentPage().get(k)).getQuestion());
            riddle_answer[k] = String.valueOf(((puzzles) riddlecollection.getCurrentPage().get(k)).getAnswer());

            values.put(RIDDLE_QUESTIONS, riddle_que[k]);
            values.put(RIDDLE_ANSWER, riddle_answer[k]);
            values.put(RIDDLE_ID,riddle_id[k]);
            db.insert(TABLE_RIDDLE, null, values);
        }
        // Inserting Row

        db.close(); // Closing database connection
    }

    //
    // Getting single contact
    public Cursor getAptitude(int i) {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + TABLE_APTITUDE + " LIMIT " + i + ",1";

        Cursor cursor = db.rawQuery(select, null);

//        Cursor cursor = db.query(TABLE_APTITUDE, new String[] { APTITUDE_QUESTIONS,
//                        APTITUDE_OPTION_ONE, APTITUDE_OPTION_TWO,APTITUDE_OPTION_THREE,APTITUDE_OPTION_FOUR }, APTITUDE_ID + "=?",
//                new String[] { String.valueOf(1) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();


        // return NULL
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
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Getting puzzle Count
    public int getPuzzleCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PUZZLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Getting logical Count
    public int getLogicalCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGICAL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Getting riddle Count
    public int getRiddleCount() {
        String countQuery = "SELECT  * FROM " + TABLE_RIDDLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }













































































































































































































































}
