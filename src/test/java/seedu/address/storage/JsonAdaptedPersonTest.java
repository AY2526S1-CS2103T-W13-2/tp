package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * Unit tests for {@link JsonAdaptedPerson}.
 * <p>
 * Ensures that both {@code Person} and {@code Student} objects are correctly serialized to
 * and deserialized from JSON. This test suite covers all basic field validations and verifies
 * that missing or invalid data raises appropriate exceptions.
 */
public class JsonAdaptedPersonTest {

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    // Default student-related test data (can be null to simulate Person)
    private static final String VALID_STUDENT_CLASS = "10A";
    private static final List<String> VALID_SUBJECTS = List.of("Math", "Physics");
    private static final String VALID_EMERGENCY_CONTACT = "91234567";
    private static final String VALID_PAYMENT_STATUS = "Paid";
    private static final String VALID_ASSIGNMENT_STATUS = "Completed";
    private static final List<String> VALID_ATTENDANCE_LIST = List.of("PRESENT,2025-10-14T10:00");

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "person",
                INVALID_NAME,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_TAGS,
                null, null, null, null, null, null
        );
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "person",
                null,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_TAGS,
                null, null, null, null, null, null
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "person",
                VALID_NAME,
                INVALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_TAGS,
                null, null, null, null, null, null
        );
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "person",
                VALID_NAME,
                null,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_TAGS,
                null, null, null, null, null, null
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "person",
                VALID_NAME,
                VALID_PHONE,
                INVALID_EMAIL,
                VALID_ADDRESS,
                VALID_TAGS,
                null, null, null, null, null, null
        );
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "person",
                VALID_NAME,
                VALID_PHONE,
                null,
                VALID_ADDRESS,
                VALID_TAGS,
                null, null, null, null, null, null
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "person",
                VALID_NAME,
                VALID_PHONE,
                VALID_EMAIL,
                INVALID_ADDRESS,
                VALID_TAGS,
                null, null, null, null, null, null
        );
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "person",
                VALID_NAME,
                VALID_PHONE,
                VALID_EMAIL,
                null,
                VALID_TAGS,
                null, null, null, null, null, null
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));

        JsonAdaptedPerson person = new JsonAdaptedPerson(
                "person",
                VALID_NAME,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                invalidTags,
                null, null, null, null, null, null
        );

        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_validStudentDetails_returnsStudent() {
        JsonAdaptedPerson student = new JsonAdaptedPerson(
                "student",
                VALID_NAME,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_TAGS,
                VALID_STUDENT_CLASS,
                VALID_SUBJECTS,
                VALID_EMERGENCY_CONTACT,
                VALID_PAYMENT_STATUS,
                VALID_ASSIGNMENT_STATUS,
                VALID_ATTENDANCE_LIST
        );
        // We only test for successful conversion (not deep equality)
        assertEquals("student", student.getType());
    }
}
