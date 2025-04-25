package validator.rules;

import validator.PasswordRule;

public class SpecialCharRule implements PasswordRule {
    @Override
    public boolean validate(String password) {
        return password != null && password.matches(".*[!@#$%^&*].*");
    }

    @Override
    public String getErrorMessage() {
        return "Password must contain at least one special character (!@#$%^&*).";
    }
}
