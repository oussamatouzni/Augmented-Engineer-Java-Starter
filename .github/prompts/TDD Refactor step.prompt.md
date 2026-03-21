---
agent: agent
name: TDD Red step
description: This prompt is used to implement one test scenario that fails in a TDD workflow for an AI agent
argument-hint: Implement the following test scenario in a TDD workflow for an AI agent: {scenario_description}
tools: ['execute/getTerminalOutput', 'execute/runInTerminal', 'read/problems', 'read/readFile', 'read/terminalSelection', 'read/terminalLastCommand', 'edit/createDirectory', 'edit/createFile', 'edit/editFiles', 'search', 'context7/*', 'todo']
model: GPT-5 mini (copilot)
---

# Refactor TDD step prompt

## Instructions

1. Analyze the provided test reference carefully.

    * The input will contain a test file path and a test method name.
    * Retrieve the test file content and locate the specified test method.
    * Read the corresponding JSON output file from `build/test-output/<TestClassName>.json` produced by the Green step.
    * if the JSON file is missing, run the Green step to generate it.
2. Identify all inline/fake implementations inside the test class (inner classes, helper methods, hardcoded values).
3. Extract each piece of inline implementation into the appropriate production module.

    * Follow the module structure: `domain`, `application`, or `infrastructure`.
    * Follow the testing guidelines for the target module.
4. Update the test file to use the new production classes instead of the inline fakes.
5. Run the tests to confirm they still pass after refactoring.

---

## Requirements

* You **MUST NOT** change any test logic, assertions, or method names.
* You **MUST NOT** add new behavior or features during refactoring.
* You **MUST** ensure all tests still pass after refactoring.
* You **MUST** follow the architecture and naming conventions of the existing codebase.
* The JSON output file from `build/test-output` MUST be used as the reference contract for the expected production behavior.

---

## Module Resolution Rules

* If the implementation references domain entities → extract to `domain`
* If it references use cases / handlers → extract to `application`
* If it references external systems (DB, API, filesystem) → extract to `infrastructure`
* If ambiguous → default to `application`

---

## Extraction Rules

* Extract inline classes to their appropriate production package
* Extract hardcoded values to real logic only if the test requires it
* Preserve the method signatures established in the Green step
* One class per file — follow existing file naming conventions
* Do NOT merge unrelated concerns into the same class

---

## Minimal Refactor Strategy

* Move code, do not rewrite it
* Preserve all behavior established in the Green step
* Introduce abstractions only if required by the existing architecture
* Prefer clarity over cleverness
* Do NOT anticipate future test cases

---

## Idempotency Rules

* If a production class already exists for the concept:
    * Extend it rather than creating a duplicate
* If the method already exists in production code:
    * Verify its behavior matches the Green step output
    * Adjust only if there is a mismatch
* Do NOT touch unrelated production classes

---

## Test Execution Verification

* Run the full test suite for the affected module after refactoring
* Capture output
* Confirm all tests pass — including pre-existing ones
* If any test fails → revert only the change that caused the regression



## Forbidden Actions

* DO NOT change test expectations
* DO NOT add new features or behavior
* DO NOT modify unrelated production classes
* DO NOT introduce design patterns not already present in the codebase
* DO NOT skip test execution
* DO NOT leave inline fake implementations in the test file after extraction

---

## Examples

### Domain refactor example

Input:
Test File: `ContactExportUseCaseTest.java`
Test Method: `shouldProduceExportDtoWhenContactsExist`
JSON Contract: `build/test-output/ContactExportUseCaseTest.json`

Expected Behavior:

* The `FakeUseCaseHandler` inline class is extracted to a real `ContactExportUseCase` in production code.
* The test is updated to instantiate `ContactExportUseCase` directly instead of the fake.
* All tests still pass.

Expected Output:

* New file `application/src/main/java/com/example/application/contact/ContactExportUseCase.java`:
```java
package com.example.application.contact;

import com.example.domain.contact.Contact;
import com.example.domain.contact.ContactExportDto;
import com.example.domain.contact.ExportContactQuery;
import com.example.domain.contact.UseCaseHandler;

import java.util.List;

public class ContactExportUseCase implements UseCaseHandler<ExportContactQuery, ContactExportDto> {

    private final ContactRepository contactRepository;

    public ContactExportUseCase(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public ContactExportDto execute(ExportContactQuery query) {
        List<Contact> contacts = contactRepository.findByUserId(query.getUserId());
        return new ContactExportDto(contacts);
    }
}
```

* Updated test file — `FakeUseCaseHandler` removed, replaced with real class:
```java
@BeforeEach
void setUp() {
    fixture = new ContactExportFixture();
    fixture.setUseCaseHandler(new ContactExportUseCase(fixture.getContactRepository()));
}
```

* Test passes with no changes to assertions.

---

### Incorrect Example

* Changing `assertThat(exportDto.getContacts()).hasSize(20)` to `hasSize(0)`
* Adding a new `exportToCsv()` method not covered by any test
* Leaving `FakeUseCaseHandler` in the test file alongside the real class

These are NOT allowed in the Refactor step.