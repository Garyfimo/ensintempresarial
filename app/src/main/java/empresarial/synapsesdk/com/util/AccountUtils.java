package empresarial.synapsesdk.com.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import empresarial.synapsesdk.com.activity.LoginActivity;
import empresarial.synapsesdk.com.model.User;

public class AccountUtils {

    private static final String PREF_CHOSEN_ACCOUNT = "chosen_account";
    private static final String PREF_ACCOUNT_DATA = "data_account";

    public static boolean isAuthenticated(final Context context) {
        return getAccountName(context) != null;
    }

    public static String getAccountName(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_CHOSEN_ACCOUNT, null);
    }

    public static void setAccountName(final Context context, final String accountName) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PREF_CHOSEN_ACCOUNT, accountName).commit();
    }

    public static void setUserData(final Context context, final String userData) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PREF_ACCOUNT_DATA, userData).commit();
    }

    public static User getUserData(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return new Gson().fromJson(sp.getString(PREF_ACCOUNT_DATA, null), User.class);
    }

    public static void startAuthenticationFlow(final Context context) {
        Intent loginFlowIntent = new Intent(context, LoginActivity.class);
        loginFlowIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(loginFlowIntent);
    }

    public static void registerAccount(Context context, User user) {
        String dataJson = new Gson().toJson(user);
        setAccountName(context, user.getUsername());
        setUserData(context, dataJson);
    }
}
