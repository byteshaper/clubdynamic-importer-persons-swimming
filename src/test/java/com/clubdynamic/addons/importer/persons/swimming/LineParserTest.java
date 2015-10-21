package com.clubdynamic.addons.importer.persons.swimming;

import static org.hamcrest.Matchers.nullValue;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import com.clubdynamic.dto.person.PersonWriteDto;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LineParserTest {

  private static List<String> csvLines;

  private LineParser lineParser;

  @BeforeClass
  public static void readCsvLines() throws Exception {
    csvLines =
        Files.readAllLines(Paths.get(LineParserTest.class.getClassLoader().getResource("persons.csv").toURI()));
  }

  @Before
  public void init() {
    lineParser = new LineParser();
  }

  @Test
  public void normalLineWithMemberAndLandline() {
    PersonWriteDto person = lineParser.parse(csvLines.get(1));
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
  }
}

