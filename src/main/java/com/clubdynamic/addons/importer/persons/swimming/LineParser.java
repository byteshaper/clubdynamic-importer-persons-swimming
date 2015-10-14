package com.clubdynamic.addons.importer.persons.swimming;

import com.clubdynamic.dto.person.PersonWriteDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LineParser {

  private static final String FIELD_SEPARATOR = "\\|";
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
  
  private static final int COL_LASTNAME = 0;
  private static final int COL_FIRSTNAME = 1;
  private static final int COL_BIRTHDAY = 2;
  private static final int COL_GENDER = 3;
  private static final int COL_STREET = 4;
  private static final int COL_ZIP = 5;
  private static final int COL_CITY = 6;

  public PersonWriteDto parse(String line) {
    PersonWriteDto person = new PersonWriteDto();
    String[] lines = line.split(FIELD_SEPARATOR);
    // System.err.println(line);
    person.firstName = lines[COL_FIRSTNAME];
    person.lastName = lines[COL_LASTNAME];
    person.birthday = LocalDate.parse(lines[COL_BIRTHDAY], DATE_FORMATTER).toString();
    person.gender = lines[COL_GENDER];
    person.street = lines[COL_STREET];
    person.zip = lines[COL_ZIP];
    person.city = lines[COL_CITY];
    return person;
  }
}
