package com.example.appcheckinbyqrcode;

import android.text.Editable;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckValidate {
    public static boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }

    public static boolean isPasswordValid(String password)
    {
        String PASSWORD_PATTERN = "^[a-z0-9]{6,12}$";
        CharSequence inputStr = password;
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }

    public static boolean isValidPhoneNumber(String number)
    {
        String validNumber0 = "^(0|84)[35789]{1}\\d{8}$";
        String validNumber84 = "^84[35789]{1}\\d{9}$";
        Pattern pattern0 = Pattern.compile(validNumber0,Pattern.CASE_INSENSITIVE);
        Matcher matcher0 = pattern0.matcher(number);
        Pattern pattern84 = Pattern.compile(validNumber84,Pattern.CASE_INSENSITIVE);
        Matcher matcher84 = pattern84.matcher(number);
        if (matcher0.find() ) {//|| matcher84.find()
            return true;
        }
        return false;
    }

    public static boolean isValidName(Editable s)
    {
        String validName = "^[$&+,:;=\\?@#|/'<>.^*()%!-]$";
        if (s.toString().matches(validName)) {
            return true;
        }
        return false;
    }
    public static boolean isValidSpecialCharacters(Editable s){
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
        if (regex.matcher(s).find()) {
            return true;
        }
        return false;
    }
    public static boolean isValidAddress(Editable s){
        Pattern regex = Pattern.compile("[$&+:;=\\\\?@#|/'<>.^*()%!]");
        if (regex.matcher(s).find()) {
            return true;
        }
        return false;
    }
}
