@regression
Feature: Login validation
  In order to protect the application
  As a user
  I should see a clear error when my account is locked

  @smoke
  Scenario: Locked-out user cannot log in
    Given the user is on the login page
    When the user signs in with username "locked_out_user" and password "secret_sauce"
    Then the login error should contain "locked out"
