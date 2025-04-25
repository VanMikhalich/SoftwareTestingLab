package validator.rules;

import validator.PasswordRule;

public class LengthRule implements PasswordRule {
    @Override
    public boolean validate(String password) {
        return password != null && password.length() >= 8;
    }

    @Override
    public String getErrorMessage() {
        return "Password must be at least 8 characters long.";
    }
}
