package com.example.appcheckinbyqrcode;

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
        String validNumber = "^[+]?[0-9]{10,12}$";
        if (number.matches(validNumber)) {
            return true;
        }
        return false;
    }

    public static boolean isValidName(String number)
    {
        String validName = "[a-zA-Z ]*";
        if (number.matches(validName)) {
            return true;
        }
        return false;
    }
}
