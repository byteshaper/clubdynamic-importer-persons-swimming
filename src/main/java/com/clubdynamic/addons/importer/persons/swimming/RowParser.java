package com.clubdynamic.addons.importer.persons.swimming;

import com.clubdynamic.dto.membership.MembershipCreateDto;
import com.clubdynamic.dto.person.PersonWriteDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RowParser {

  private static final Logger LOGGER = LoggerFactory.getLogger(RowParser.class);
  private static final String FIELD_SEPARATOR = "\\|";
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

  private static final int COL_LASTNAME = 0;
  private static final int COL_FIRSTNAME = 1;
  private static final int COL_BIRTHDAY = 2;
  private static final int COL_GENDER = 3;
  private static final int COL_STREET = 4;
  private static final int COL_ZIP = 5;
  private static final int COL_CITY = 6;
  private static final int COL_PHONE = 7;
  private static final int COL_MOBILE = 9;
  private static final int COL_EMAIL = 10;
  private static final int COL_ACTIVE_SWIMMER = 14;
  private static final int COL_ACTIVE_COACH = 18;
  private static final int COL_FIRST_DAY = 35;
  private static final int COL_LAST_DAY = 36;

  public RowData parse(String line) {
    PersonWriteDto person = new PersonWriteDto();
    String[] fields = line.split(FIELD_SEPARATOR);
    person.firstName = nonEmptyTrimmedStringOrDie(fields, COL_FIRSTNAME);
    person.lastName = nonEmptyTrimmedStringOrDie(fields, COL_LASTNAME);
    person.birthday = parseDate(fields, COL_BIRTHDAY, true);
    person.gender = nonEmptyTrimmedStringOrDie(fields, COL_GENDER);
    person.street = nonEmptyTrimmedStringOrNull(fields[COL_STREET]);
    person.zip = nonEmptyTrimmedStringOrNull(fields[COL_ZIP]);
    person.city = nonEmptyTrimmedStringOrNull(fields[COL_CITY]);
    person.phone = nonEmptyTrimmedStringOrNull(fields[COL_PHONE]);
    person.mobile = nonEmptyTrimmedStringOrNull(fields[COL_MOBILE]);
    person.emailAddress = nonEmptyTrimmedStringOrNull(fields[COL_EMAIL]);

    MembershipCreateDto membership = new MembershipCreateDto();
    membership.firstDay = parseDate(fields, COL_FIRST_DAY, true);

    Optional<String> lastDay = Optional.ofNullable(parseDate(fields, COL_LAST_DAY, false));
    boolean activeSwimmer = parseBoolean(fields, COL_ACTIVE_SWIMMER);
    boolean activeCoach = parseBoolean(fields, COL_ACTIVE_COACH);
    return new RowData(person, membership, lastDay, activeSwimmer, activeCoach);
  }

  private boolean parseBoolean(String[] fields, int colIndex) {
    String content = nonEmptyTrimmedStringOrDie(fields, colIndex).toLowerCase();

    if (content.equals("ja")) {
      return true;
    } else if (content.equals("nein")) {
      return false;
    }

    throw new ParseException(String.format("Invalid boolean value '%s' in column %d of row %s",
        content,
        colIndex,
        fieldsToString(fields)));
  }

  private String parseDate(String[] fields, int colIndex, boolean warnOnNull) {
    try {
      return LocalDate.parse(fields[colIndex], DATE_FORMATTER).toString();
    } catch (DateTimeParseException e) {
      if (warnOnNull) {
        LOGGER.warn("Unparseable date '{}' in column %d of row {}",
            fields[colIndex],
            colIndex,
            fieldsToString(fields));
      }
      return null;
    }
  }

  private String nonEmptyTrimmedStringOrDie(String[] fields, int colIndex) {
    String result = fields[colIndex].trim();

    if (result.isEmpty()) {
      throw new ParseException(String.format("Empty mandatory column %d in row %s.", colIndex, fieldsToString(fields)));
    }

    return result;
  }

  private String nonEmptyTrimmedStringOrNull(String input) {
    String result = input.trim();

    if (result.isEmpty()) {
      return null;
    }

    return result;
  }

  private String fieldsToString(String[] fields) {
    StringBuilder result = new StringBuilder("{");
    Arrays.stream(fields).forEach(f -> result.append(f).append(" | "));
    return result.append("}").toString();
  }
}
