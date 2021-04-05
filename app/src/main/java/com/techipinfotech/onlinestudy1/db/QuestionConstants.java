package com.techipinfotech.onlinestudy1.db;

import android.provider.BaseColumns;

public final class QuestionConstants {

    private QuestionConstants() {
    }

    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "exam_questions";
        public static final String COLUMN_QUESTION_ID = "question_id";
        public static final String COLUMN_QUESTION_TYPE = "question_type";
        public static final String COLUMN_QUESTION = "questionimage";
        public static final String COLUMN_OPTION1 = "opt1";
        public static final String COLUMN_OPTION2 = "opt2";
        public static final String COLUMN_OPTION3 = "opt3";
        public static final String COLUMN_OPTION4 = "opt4";
        public static final String COLUMN_OPTION1E = "opt1e";
        public static final String COLUMN_OPTION2E = "opt2e";
        public static final String COLUMN_OPTION3E = "opt3e";
        public static final String COLUMN_OPTION4E = "opt4e";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_TEST_ID = "test_id";
        public static final String COLUMN_EXAM_ID = "exam_id";

    }

    public static class AnswersTable implements BaseColumns {
        public static final String TABLE_NAME = "exam_answers";
        public static final String COLUMN_QUESTION_ID = "question_id";
        public static final String COLUMN_ANSWER = "answer";
        public static final String COLUMN_GIVEN_ANSWER = "given_answer";
        public static final String COLUMN_TEST_ID = "test_id";
        public static final String COLUMN_EXAM_ID = "exam_id";
        public static final String COLUMN_CLASS_ID = "class_id";
        public static final String COLUMN_STUDENT_MOBILE = "student_mobile";

    }
}
