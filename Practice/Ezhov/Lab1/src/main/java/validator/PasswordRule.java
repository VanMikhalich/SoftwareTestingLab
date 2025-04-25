package validator;

public interface PasswordRule {
    boolean validate(String password);
    String getErrorMessage();
}
