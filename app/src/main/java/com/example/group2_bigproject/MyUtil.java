package com.example.group2_bigproject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtil {
    // this method for displaying the avaUser in the bestway
    // ImageButton uses background, ImageView uses ImageResource
    public static void setImageButtonBackground(Context context, int imageId, ImageView imageBtn) {
        Drawable drawable = ContextCompat.getDrawable(context, imageId);
        if (drawable != null) {
            imageBtn.setImageDrawable(drawable);
        }
    }

    // Phương thức kiểm tra từ ngữ nhạy cảm
    public static boolean containsSensitiveWords(Context context, String text) {
        try {
            // Đọc danh sách từ nhạy cảm từ file JSON và biên dịch biểu thức chính quy
            String[] sensitiveWordsArray = readSensitiveWordsFromJson(context, "bad_words.json");
            String regexPattern = buildRegexPattern(sensitiveWordsArray);
            Pattern pattern = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);

            // Kiểm tra văn bản
            Matcher matcher = pattern.matcher(text);
            return matcher.find();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String[] readSensitiveWordsFromJson(Context context, String fileName) throws IOException, JSONException {
        InputStream is = context.getAssets().open(fileName);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        String json = new String(buffer, "UTF-8");
        JSONObject jsonObject = new JSONObject(new JSONTokener(json));

        // Lấy danh sách các từ nhạy cảm (các khóa của JSONObject)
        Iterator<String> keys = jsonObject.keys();
        String[] words = new String[jsonObject.length()];
        int index = 0;
        while (keys.hasNext()) {
            words[index++] = keys.next();
        }
        return words;
    }

    private static String buildRegexPattern(String[] sensitiveWordsArray) {
        StringBuilder patternBuilder = new StringBuilder();
        patternBuilder.append("\\b(");

        for (int i = 0; i < sensitiveWordsArray.length; i++) {
            String word = sensitiveWordsArray[i];
            String wordWithSpaces = word.replace("_", " ");
            patternBuilder.append(Pattern.quote(word))
                    .append("|")
                    .append(Pattern.quote(wordWithSpaces));

            if (i < (sensitiveWordsArray.length - 1)) {
                patternBuilder.append("|");
            }
        }

        patternBuilder.append(")\\b");
        return patternBuilder.toString();
    }
}
