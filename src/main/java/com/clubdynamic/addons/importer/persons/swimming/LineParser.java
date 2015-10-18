package com.clubdynamic.addons.importer.persons.swimming;

import com.clubdynamic.dto.person.PersonWriteDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LineParser {

  private static final Logger LOGGER = LoggerFactory.getLogger(LineParser.class);
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

  public PersonWriteDto parse(String line) {
    PersonWriteDto person = new PersonWriteDto();
    String[] fields = line.split(FIELD_SEPARATOR);
    // System.err.println(line);
    person.firstName = nonEmptyTrimmedStringOrNull(fields[COL_FIRSTNAME]);
    person.lastName = nonEmptyTrimmedStringOrNull(fields[COL_LASTNAME]);
    person.birthday = parseDate(fields);
    person.gender = nonEmptyTrimmedStringOrNull(fields[COL_GENDER]);
    person.street = nonEmptyTrimmedStringOrNull(fields[COL_STREET]);
    person.zip = nonEmptyTrimmedStringOrNull(fields[COL_ZIP]);
    person.city = nonEmptyTrimmedStringOrNull(fields[COL_CITY]);
    person.phone = nonEmptyTrimmedStringOrNull(fields[COL_PHONE]);
    person.mobile = nonEmptyTrimmedStringOrNull(fields[COL_MOBILE]);
    return person;
  }

  private String parseDate(String[] fields) {
    try {
      return LocalDate.parse(fields[COL_BIRTHDAY], DATE_FORMATTER).toString();
    } catch (DateTimeParseException e) {
      LOGGER.warn("Unparseable date of birth '{}' in line {}", fields[COL_BIRTHDAY], fieldsToString(fields));
      return null;
    }
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
