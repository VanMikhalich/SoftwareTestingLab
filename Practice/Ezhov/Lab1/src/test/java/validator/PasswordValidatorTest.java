package validator;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import validator.rules.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PasswordValidatorTest {

    @Test
    void validPasswordShouldPass() {
        List<PasswordRule> rules = List.of(
                new LengthRule(),
                new UppercaseRule(),
                new LowercaseRule(),
                new DigitRule(),
                new SpecialCharRule()
        );
        PasswordValidator validator = new PasswordValidator(rules);
        assertTrue(validator.validate("Abcdef1!"));
    }

    @Test
    void invalidPasswordShouldFail() {
        List<PasswordRule> rules = List.of(
                new LengthRule(),
                new UppercaseRule()
        );
        PasswordValidator validator = new PasswordValidator(rules);
        assertFalse(validator.validate("abcdefg")); // no uppercase
    }

    @Test
    void getValidationMessagesShouldReturnErrors() {
        List<PasswordRule> rules = List.of(
                new LengthRule(),
                new UppercaseRule(),
                new DigitRule()
        );
        PasswordValidator validator = new PasswordValidator(rules);
        List<String> messages = validator.getValidationMessages("short");

        assertEquals(3, messages.size());
        assertTrue(messages.contains("Password must be at least 8 characters long."));
        assertTrue(messages.contains("Password must contain at least one uppercase letter."));
        assertTrue(messages.contains("Password must contain at least one digit."));
    }

    @Test
    void mockRuleTest() {
        PasswordRule mockRule = Mockito.mock(PasswordRule.class);
        when(mockRule.validate(anyString())).thenReturn(false);
        when(mockRule.getErrorMessage()).thenReturn("Mock error");

        PasswordValidator validator = new PasswordValidator(List.of(mockRule));
        assertFalse(validator.validate("Anything"));
        assertEquals(List.of("Mock error"), validator.getValidationMessages("Anything"));

        verify(mockRule, times(2)).validate("Anything");
    }

    @Test
    void digitRuleShouldValidateCorrectly() {
        DigitRule rule = new DigitRule();
        assertTrue(rule.validate("pass1word"));
        assertFalse(rule.validate("password"));
        assertEquals("Password must contain at least one digit.", rule.getErrorMessage());
    }

    @Test
    void lengthRuleShouldValidateCorrectly() {
        LengthRule rule = new LengthRule();
        assertTrue(rule.validate("longpassword"));
        assertFalse(rule.validate("short"));
        assertEquals("Password must be at least 8 characters long.", rule.getErrorMessage());
    }

    @Test
    void lowercaseRuleShouldValidateCorrectly() {
        LowercaseRule rule = new LowercaseRule();
        assertTrue(rule.validate("Password"));
        assertFalse(rule.validate("PASSWORD"));
        assertEquals("Password must contain at least one lowercase letter.", rule.getErrorMessage());
    }

    @Test
    void uppercaseRuleShouldValidateCorrectly() {
        UppercaseRule rule = new UppercaseRule();
        assertTrue(rule.validate("Password"));
        assertFalse(rule.validate("password"));
        assertEquals("Password must contain at least one uppercase letter.", rule.getErrorMessage());
    }

    @Test
    void specialCharRuleShouldValidateCorrectly() {
        SpecialCharRule rule = new SpecialCharRule();
        assertTrue(rule.validate("pass@word"));
        assertFalse(rule.validate("password"));
        assertEquals("Password must contain at least one special character (!@#$%^&*).", rule.getErrorMessage());
    }

    @Test
    void nullPasswordShouldBeInvalidForAllRules() {
        List<PasswordRule> rules = List.of(
                new LengthRule(),
                new UppercaseRule(),
                new LowercaseRule(),
                new DigitRule(),
                new SpecialCharRule()
        );
        PasswordValidator validator = new PasswordValidator(rules);
        assertFalse(validator.validate(null));

        List<String> messages = validator.getValidationMessages(null);
        assertEquals(5, messages.size());
    }

    @Test
    void emptyPasswordShouldBeInvalidForAllRules() {
        List<PasswordRule> rules = List.of(
                new LengthRule(),
                new UppercaseRule(),
                new LowercaseRule(),
                new DigitRule(),
                new SpecialCharRule()
        );
        PasswordValidator validator = new PasswordValidator(rules);
        assertFalse(validator.validate(""));

        List<String> messages = validator.getValidationMessages("");
        assertEquals(5, messages.size());
    }

    @Test
    void passwordValidatorWithEmptyRulesShouldAlwaysPass() {
        PasswordValidator validator = new PasswordValidator(List.of());
        assertTrue(validator.validate("anypassword"));
        assertTrue(validator.validate(null));
        assertTrue(validator.validate(""));
        assertTrue(validator.getValidationMessages("any").isEmpty());
    }
}