package com.techipinfotech.onlinestudy1.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.techipinfotech.onlinestudy1.db.QuestionConstants.AnswersTable;
import com.techipinfotech.onlinestudy1.db.QuestionConstants.QuestionsTable;
import com.techipinfotech.onlinestudy1.model.AnswersItem;
import com.techipinfotech.onlinestudy1.model.QuestionsItem;

import java.util.ArrayList;
import java.util.List;

public class QuestionDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PragatiInstituteExam.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public QuestionDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION_ID + " INTEGER, " +
                QuestionsTable.COLUMN_QUESTION_TYPE + " TEXT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1E + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2E + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3E + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4E + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER + " INTEGER, " +
                QuestionsTable.COLUMN_TEST_ID + " INTEGER, " +
                QuestionsTable.COLUMN_EXAM_ID + " INTEGER" +
                ")";
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);

        final String SQL_CREATE_ANSWER_TABLE = "CREATE TABLE " +
                AnswersTable.TABLE_NAME + " ( " +
                AnswersTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AnswersTable.COLUMN_QUESTION_ID + " INTEGER," +
                AnswersTable.COLUMN_ANSWER + " INTEGER," +
                AnswersTable.COLUMN_GIVEN_ANSWER + " INTEGER," +
                AnswersTable.COLUMN_TEST_ID + " INTEGER," +
                AnswersTable.COLUMN_EXAM_ID + " INTEGER," +
                AnswersTable.COLUMN_CLASS_ID + " TEXT," +
                AnswersTable.COLUMN_STUDENT_MOBILE + " TEXT" +
                ")";
        db.execSQL(SQL_CREATE_ANSWER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AnswersTable.TABLE_NAME);
        onCreate(db);

    }

    public void addAnswer(AnswersItem answersItem) {
        db = getWritableDatabase();
        insertAnswer(answersItem);
    }

    private void insertAnswer(AnswersItem answersItem) {

        ContentValues cv = new ContentValues();
        cv.put(AnswersTable.COLUMN_QUESTION_ID, answersItem.getQuestionId());
        cv.put(AnswersTable.COLUMN_ANSWER, answersItem.getRightAnswer());
        cv.put(AnswersTable.COLUMN_GIVEN_ANSWER, answersItem.getGivenAnswer());
        cv.put(AnswersTable.COLUMN_TEST_ID, answersItem.getTestId());
        cv.put(AnswersTable.COLUMN_EXAM_ID, answersItem.getExamId());
        cv.put(AnswersTable.COLUMN_CLASS_ID, answersItem.getClassId());
        cv.put(AnswersTable.COLUMN_STUDENT_MOBILE, answersItem.getStudentMobile());
        db.insert(AnswersTable.TABLE_NAME, null, cv);
    }


    public void fixAnswer(AnswersItem answersItem) {
        db = getWritableDatabase();
        updateAnswer(answersItem);
    }


    private void updateAnswer(AnswersItem answersItem) {

        ContentValues cv = new ContentValues();
        cv.put(AnswersTable.COLUMN_QUESTION_ID, answersItem.getQuestionId());
        cv.put(AnswersTable.COLUMN_ANSWER, answersItem.getRightAnswer());
        cv.put(AnswersTable.COLUMN_GIVEN_ANSWER, answersItem.getGivenAnswer());
        cv.put(AnswersTable.COLUMN_TEST_ID, answersItem.getTestId());
        cv.put(AnswersTable.COLUMN_EXAM_ID, answersItem.getExamId());
        cv.put(AnswersTable.COLUMN_CLASS_ID, answersItem.getClassId());
        cv.put(AnswersTable.COLUMN_STUDENT_MOBILE, answersItem.getStudentMobile());
        db.update(AnswersTable.TABLE_NAME,  cv,AnswersTable.COLUMN_QUESTION_ID+" = ?",new String[] { answersItem.getQuestionId() });

    }


    public List<AnswersItem> getAllAnswers() {
        List<AnswersItem> answersItems = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + AnswersTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {

                AnswersItem question = new AnswersItem(
                        c.getString(c.getColumnIndex(AnswersTable.COLUMN_QUESTION_ID)),
                        c.getString(c.getColumnIndex(AnswersTable.COLUMN_ANSWER)),
                        c.getString(c.getColumnIndex(AnswersTable.COLUMN_GIVEN_ANSWER)),
                        c.getString(c.getColumnIndex(AnswersTable.COLUMN_TEST_ID)),
                        c.getString(c.getColumnIndex(AnswersTable.COLUMN_EXAM_ID)),
                        c.getString(c.getColumnIndex(AnswersTable.COLUMN_CLASS_ID)),
                        c.getString(c.getColumnIndex(AnswersTable.COLUMN_STUDENT_MOBILE))
                );

                answersItems.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return answersItems;
    }


    public void addQuestions(List<QuestionsItem> questions) {
        db = getWritableDatabase();
        for (QuestionsItem question : questions) {
            insertQuestion(question);
        }
    }


    private void insertQuestion(QuestionsItem question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION_ID, question.getQuestionId());
        cv.put(QuestionsTable.COLUMN_QUESTION_TYPE, question.getQuestionType());
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestionImage());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOpt1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOpt2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOpt3());
        cv.put(QuestionsTable.COLUMN_OPTION4, question.getOpt4());
        cv.put(QuestionsTable.COLUMN_OPTION1E, question.getOpt1e());
        cv.put(QuestionsTable.COLUMN_OPTION2E, question.getOpt2e());
        cv.put(QuestionsTable.COLUMN_OPTION3E, question.getOpt3e());
        cv.put(QuestionsTable.COLUMN_OPTION4E, question.getOpt4e());
        cv.put(QuestionsTable.COLUMN_ANSWER, question.getRightAnswer());
        cv.put(QuestionsTable.COLUMN_TEST_ID, question.getTestId());
        cv.put(QuestionsTable.COLUMN_EXAM_ID, question.getExamId());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);

    }

    public List<QuestionsItem> getAllQuestions() {
        List<QuestionsItem> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {

                QuestionsItem question = new QuestionsItem(
                        c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION_ID)),
                        c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION_TYPE)),
                        c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)),
                        c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)),
                        c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)),
                        c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)),
                        c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)),
                        c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER)),
                        c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1E)),
                        c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2E)),
                        c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3E)),
                        c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4E)),
                        c.getString(c.getColumnIndex(QuestionsTable.COLUMN_TEST_ID)),
                        c.getString(c.getColumnIndex(QuestionsTable.COLUMN_EXAM_ID))
                );

                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }


    public void deleteTableData() {

        db = getWritableDatabase();
        db.execSQL("delete from " + QuestionsTable.TABLE_NAME);
        db.execSQL("delete from " + AnswersTable.TABLE_NAME);
    }


}
