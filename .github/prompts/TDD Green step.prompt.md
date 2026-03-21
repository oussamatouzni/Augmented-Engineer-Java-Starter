---
agent: agent
name: TDD Green step
description: This prompt is used to implement the minimal code required to make a failing test pass in a TDD workflow, following the "TDD as if you meant it" approach (implementation inside the test only)
argument-hint: Make the following test pass using minimal implementation inside the test class only: {test_file_path}#{test_method_name}
  tools: ['execute/getTerminalOutput', 'execute/runInTerminal', 'read/problems', 'read/readFile', 'read/terminalSelection', 'read/terminalLastCommand', 'edit/createDirectory', 'edit/createFile', 'edit/editFiles', 'search', 'context7/*', 'todo']
  model: GPT-5 mini (copilot)
---

# Green TDD step prompt

## Instructions

1. Analyze the provided test reference carefully.

    * The input will contain a test file path and a test method name.
    * Retrieve the test file content and locate the specified test method.
2. Understand why the test is currently failing.
3. Implement the **minimum amount of code required to make this specific test pass**.

    * The implementation MUST be done **inside the test class itself**.
    * Do NOT create or modify any production code.
4. Run the test to confirm it now passes.

---

## Requirements

* You **MUST NOT** implement any production code in this step.
* You **MUST** implement the minimal code necessary to make the test pass.
* You **MUST NOT** modify the test logic, assertions, or method name.
* You **MUST NOT** modify other tests in the file.
* The test MUST pass when executed.

---

## Module Resolution Rules

* If the test references domain entities → use `domain`
* If it references use cases / handlers → use `application`
* If it references external systems (DB, API, filesystem) → use `infrastructure`
* If ambiguous → default to `application`

---

## Implementation Rules (TDD "as if you meant it")

* ALL implementation MUST be done inside the test file
* You MAY:

    * add helper methods inside the test class
    * add minimal inline classes inside the test file if needed
    * return hardcoded values if necessary
* You MUST:

    * implement the simplest possible solution
    * avoid abstraction
    * avoid reuse
    * avoid overengineering

---

## Minimal Implementation Strategy

* Start with hardcoded values
* Add minimal logic ONLY if required by the test
* Prefer duplication to design
* Do NOT anticipate future test cases

---

## Idempotency Rules

* Only modify the test file containing the specified test
* Do NOT modify unrelated files
* Do NOT duplicate existing code unnecessarily
* If implementation already exists, adjust minimally to make the test pass

---

## Test Execution Verification

* Run the test after implementation
* Capture output
* Confirm the specified test passes
* If the test still fails → adjust implementation
* Ensure no unintended changes to other tests

---

## Coding Constraints

* Use JUnit 5
* Use AssertJ for assertions
* Keep Given / When / Then structure intact
* Avoid mocks unless strictly necessary

---

## Forbidden Actions

* DO NOT implement production code
* DO NOT create new production classes
* DO NOT modify business logic outside the test
* DO NOT refactor code
* DO NOT improve design
* DO NOT add extra features
* DO NOT modify the test expectations
* DO NOT skip test execution

---

## Examples

### Domain test example : minimal green implementation

Input :
Test File: `ContactExportUseCaseTest.java`
Test Method: `shouldProduceExportDtoWhenContactsExist`

Expected Behavior:

* The test initially fails because `handler.execute(query)` is not implemented.

Expected Output:

* Modify the test file to include minimal implementation inside the test class:

```java
class ContactExportUseCaseTest {

    // minimal fake implementation inside test
    private static class FakeUseCaseHandler implements UseCaseHandler<ExportContactQuery, ContactExportDto> {
        @Override
        public ContactExportDto execute(ExportContactQuery query) {
            List<Contact> contacts = IntStream.range(0, 20)
                .mapToObj(i -> new Contact("Contact " + i, "contact" + i + "@example.com"))
                .collect(Collectors.toList());

            return new ContactExportDto(contacts);
        }
    }
}
```

* The test now passes using this minimal implementation.

---

### Incorrect Example

* Creating a real `ExportContactUseCase` in production code
* Implementing repositories or database access
* Refactoring domain logic

These are NOT allowed in Green step
