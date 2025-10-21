package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attendance.AttendanceList;
import seedu.address.model.attendance.AttendanceStatus;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser
 * classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading
     * and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero
     *                        unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /** Parses comma-separated subjects into {@code List<String>}. */
    public static List<String> parseSubjects(String csvSubjects) throws ParseException {
        requireNonNull(csvSubjects);
        String trimmed = csvSubjects.trim();
        if (trimmed.isEmpty()) {
            return new ArrayList<>();
        }
        String[] parts = trimmed.split(",");
        List<String> subjects = new ArrayList<>();
        for (String part : parts) {
            String s = part.trim();
            if (!s.isEmpty()) {
                subjects.add(s);
            }
        }
        return subjects;
    }

    /** Parses class string (non-empty). */
    public static String parseStudentClass(String studentClass) throws ParseException {
        requireNonNull(studentClass);
        String trimmed = studentClass.trim();
        if (trimmed.isEmpty()) {
            throw new ParseException("Class cannot be empty.");
        }
        return trimmed;
    }

    /** Parses emergency contact, basic digit check. */
    public static String parseEmergencyContact(String contact) throws ParseException {
        requireNonNull(contact);
        String trimmed = contact.trim();
        if (!trimmed.matches("\\d{3,}")) {
            throw new ParseException("Emergency contact should contain at least 3 digits.");
        }
        return trimmed;
    }

    /** Parses attendance list from a single attendance record string. */
    public static AttendanceList parseAttendanceList(String recordStr) throws ParseException {
        requireNonNull(recordStr);
        String trimmed = recordStr.trim();

        AttendanceList res = new AttendanceList();

        try {
            AttendanceStatus status = AttendanceStatus.valueOf(trimmed.toUpperCase());
            res.markAttendance(LocalDateTime.now(), status);
        } catch (IllegalArgumentException e) {
            throw new ParseException("Invalid attendance status. Must be one of: PRESENT, ABSENT, LATE, EXCUSED");
        }

        return res;
    }

    /** Parses payment status (free-form). */
    public static String parsePaymentStatus(String status) throws ParseException {
        requireNonNull(status);
        return status.trim();
    }

    /** Parses assignment status (free-form). */
    public static String parseAssignmentStatus(String status) throws ParseException {
        requireNonNull(status);
        return status.trim();
    }

    /** Parses optional free-form status. */
    public static String parseOptionalStatus(String status) throws ParseException {
        requireNonNull(status);
        return status.trim();
    }
}
