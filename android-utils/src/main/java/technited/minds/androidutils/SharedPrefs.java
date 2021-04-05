package technited.minds.androidutils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    SharedPreferences sp;
    SharedPreferences.Editor edit;

    public SharedPrefs(Context context, String tag) {
        sp = context.getSharedPreferences(tag, Context.MODE_PRIVATE);
        edit = sp.edit();
    }

    public void set(String key, String value) {
        edit.putString(key, value);
        edit.commit();
    }

    public void apply() {
        edit.apply();
    }

    public String get(String key) {
        return sp.getString(key, null);
    }

    public void clearAll() {
        sp.edit().clear().apply();
    }

}
