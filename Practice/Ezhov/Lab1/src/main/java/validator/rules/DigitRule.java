package validator.rules;

import validator.PasswordRule;

public class DigitRule implements PasswordRule {
    @Override
    public boolean validate(String password) {
        return password != null && password.matches(".*\\d.*");
    }

    @Override
    public String getErrorMessage() {
        return "Password must contain at least one digit.";
    }
}
