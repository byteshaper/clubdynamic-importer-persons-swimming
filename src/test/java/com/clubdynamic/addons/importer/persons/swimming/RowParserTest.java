package com.clubdynamic.addons.importer.persons.swimming;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.clubdynamic.dto.membership.MembershipCreateDto;
import com.clubdynamic.dto.person.PersonWriteDto;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RowParserTest {

  private static List<String> csvLines;

  private RowParser lineParser;

  @BeforeClass
  public static void readCsvLines() throws Exception {

    csvLines = Files.readAllLines(Paths.get(RowParserTest.class.getClassLoader().getResource("persons.csv").toURI()));
  }

  @Before
  public void init() {
    lineParser = new RowParser();
  }

  @Test
  public void validRow1() {
    RowData rowData = lineParser.parse(csvLines.get(1));
    PersonWriteDto person = rowData.person;
    assertThat(person.firstName, equalTo("Martina"));
    assertThat(person.lastName, equalTo("Musterfrau"));
    assertThat(person.birthday, equalTo("1967-05-30"));
    assertThat(person.gender, equalTo("F"));
    assertThat(person.street, equalTo("Musterstraße 1"));
    assertThat(person.zip, equalTo("12345"));
    assertThat(person.city, equalTo("Berlin"));
    assertThat(person.phone, equalTo("030/123123"));
    assertThat(person.mobile, nullValue());
    assertThat(person.emailAddress, equalTo("muster.mann@example.com"));

    MembershipCreateDto membership = rowData.membership;
    assertThat(membership.firstDay, equalTo("2015-02-21"));

    assertFalse(rowData.lastDay.isPresent());
  }

  @Test
  public void validRow2() {
    RowData rowData = lineParser.parse(csvLines.get(2));
    PersonWriteDto person = rowData.person;
    assertThat(person.firstName, equalTo("Martin"));
    assertThat(person.lastName, equalTo("Mustermann"));
    assertThat(person.birthday, equalTo("1981-01-09"));
    assertThat(person.gender, equalTo("M"));
    assertThat(person.street, equalTo("Musterstraße 134"));
    assertThat(person.zip, equalTo("12343"));
    assertThat(person.city, equalTo("Berlin"));
    assertThat(person.phone, equalTo("0301231243"));
    assertThat(person.mobile, equalTo("0151123123"));
    assertThat(person.emailAddress, equalTo("muster.martin@example.com"));

    MembershipCreateDto membership = rowData.membership;
    assertThat(membership.firstDay, equalTo("2014-05-01"));

    assertTrue(rowData.lastDay.isPresent());
    assertThat(rowData.lastDay.get(), equalTo("2015-06-30"));
  }

  @Test
  public void validRow3() {
    RowData rowData = lineParser.parse(csvLines.get(3));
    PersonWriteDto person = rowData.person;
    assertThat(person.firstName, equalTo("Heinz"));
    assertThat(person.lastName, equalTo("Mustermensch"));
    assertThat(person.birthday, equalTo("1997-12-22"));
    assertThat(person.gender, equalTo("M"));
    assertThat(person.street, equalTo("Musterstraße 134"));
    assertThat(person.zip, equalTo("98765"));
    assertThat(person.city, equalTo("Kuhdorf"));
    assertThat(person.phone, nullValue());
    assertThat(person.mobile, nullValue());
    assertThat(person.emailAddress, nullValue());

    MembershipCreateDto membership = rowData.membership;
    assertThat(membership.firstDay, equalTo("2015-05-12"));

    assertFalse(rowData.lastDay.isPresent());
  }

  @Test(expected=ParseException.class)
  public void invalidRowNoFirstname() {
    lineParser.parse(csvLines.get(4));
  }
  
  @Test(expected=ParseException.class)
  public void invalidRowNoLastname() {
    lineParser.parse(csvLines.get(5));
  }
  
  @Test(expected=ParseException.class)
  public void invalidRowNoGender() {
    lineParser.parse(csvLines.get(6));
  }
}
