package validator.rules;

import validator.PasswordRule;

public class UppercaseRule implements PasswordRule {
    @Override
    public boolean validate(String password) {
        return password != null && password.matches(".*[A-Z].*");
    }

    @Override
    public String getErrorMessage() {
        return "Password must contain at least one uppercase letter.";
    }
}
