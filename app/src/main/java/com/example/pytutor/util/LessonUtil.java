package com.example.pytutor.util;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.pytutor.model.Lesson;
import com.google.firebase.firestore.WriteBatch;

import java.util.Arrays;
import java.util.HashMap;

public class LessonUtil {
    private final String TAG = "LessonUtil";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final WriteBatch batch = db.batch();

    public void seedDb() {
        Lesson lesson1 = new Lesson("Python basics", new HashMap<>() {{
            put("Syntax", new HashMap<>() {{
                put("File extension", Arrays.asList("Python files use .py extension", "url1"));
                put("Indentation", Arrays.asList(" Python uses indentation (spaces/tabs) to define code blocks, like loops or functions.", "url2"));
            }});

            put("Variables", new HashMap<>() {{
                put("Creating variables", Arrays.asList(" Assign using =, e.g., x = 10.", "url3"));
                put("Types", Arrays.asList("Common types include int, float, str, bool, list, etc.", "url3"));
                put("Case sensitivity", Arrays.asList("Variables like Value and value are different.\n", "url3"));
            }});

            put("Comments", new HashMap<>() {{
                put("Creating comments", Arrays.asList("Use # for single-line", "url4"));
                put("Multi-line comments", Arrays.asList("Often done with triple quotes \"\"\" ", "url5"));
            }});
        }});

        // Set the value of 'lesson1'
        DocumentReference ls1Ref = db.collection("lessons").document();
        batch.set(ls1Ref, lesson1);

        Lesson lesson2 = new Lesson("Flow control", new HashMap<>() {{
            put("if statement", new HashMap<>() {{
                put("if_else", Arrays.asList("Conditional statements to run code blocks.", "url1"));
            }});

            put("elif statement", new HashMap<>() {{
                put("elif", Arrays.asList("Used for multiple conditions.", "url3"));
            }});

            put("and, or, not", new HashMap<>() {{
                put("and", Arrays.asList("Returns True if both statements are true", "url4"));
                put("or", Arrays.asList("Returns True if one of the statements is true", "url5"));
                put("not", Arrays.asList("Reverse the result, returns False if the result is true", "url5"));
            }});
        }});

        // Set the value of 'lesson2'
        DocumentReference ls2Ref = db.collection("lessons").document();
        batch.set(ls2Ref, lesson2);

        Lesson lesson3 = new Lesson("Loops", new HashMap<>() {{
            put("while loop", new HashMap<>() {{
                put("while", Arrays.asList("Repeats while a condition is true.", "url1"));
            }});

            put("for loop", new HashMap<>() {{
                put("for", Arrays.asList("Iterates over items.", "url3"));
            }});
        }});

        // Set the value of 'lessons'
        DocumentReference ls3Ref = db.collection("lessons").document();
        batch.set(ls3Ref, lesson3);

        Lesson lesson4 = new Lesson("Functions", new HashMap<>() {{
            put("Creating functions", new HashMap<>() {{
                put("def", Arrays.asList("Defined using def keyword.", "url1"));
            }});

            put("Calling functions", new HashMap<>() {{
                put("function_name()", Arrays.asList("Use function name with parentheses.", "url4"));
            }});

            put("Arguments and parameters", new HashMap<>() {{
                put("Parameters", Arrays.asList("Parameters are in the function definition.", "url6"));
                put("Arguments", Arrays.asList("Arguments are the values passed during the call.", "url6"));
            }});
        }});

        // Set the value of 'lessons'
        DocumentReference ls4Ref = db.collection("lessons").document();
        batch.set(ls4Ref, lesson4);

        batch.commit().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "Seeded DB successfully.");
            } else {
                Log.w(TAG, "Seeding Failed.", task.getException());
            }
        });
    }
}
