package validator.rules;

import validator.PasswordRule;

public class LowercaseRule implements PasswordRule {
    @Override
    public boolean validate(String password) {
        return password != null && password.matches(".*[a-z].*");
    }

    @Override
    public String getErrorMessage() {
        return "Password must contain at least one lowercase letter.";
    }
}
