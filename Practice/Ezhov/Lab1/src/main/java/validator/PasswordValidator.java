package validator;

import java.util.ArrayList;
import java.util.List;

public class PasswordValidator {
    private final List<PasswordRule> rules;

    public PasswordValidator(List<PasswordRule> rules) {
        this.rules = rules;
    }

    public boolean validate(String password) {
        return rules.stream().allMatch(rule -> rule.validate(password));
    }

    public List<String> getValidationMessages(String password) {
        List<String> messages = new ArrayList<>();
        for (PasswordRule rule : rules) {
            if (!rule.validate(password)) {
                messages.add(rule.getErrorMessage());
            }
        }
        return messages;
    }
}
