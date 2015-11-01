package com.clubdynamic.addons.importer.persons.swimming;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for importer. Verifies file loading functionality and interaction with {@link RowParser}.
 * 
 * @author Henning Schütz <henning@byteshaper.com>
 *
 */
public class ImporterTest {

  private RowParser rowParser;
  
  private Importer importer;
  
  @Before
  public void setup() {
    importer = new Importer(rowParser);
  }
  
  @Test
  public void importFile() {
    importer.importFile("persons.csv");
  }
}
