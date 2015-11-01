package com.clubdynamic.addons.importer.persons.swimming;

import java.util.Optional;

import com.clubdynamic.dto.membership.MembershipCreateDto;
import com.clubdynamic.dto.person.PersonWriteDto;

/**
 * Wraps data parsed from one row in the file we import.
 * 
 * @author Henning Schütz <henning@byteshaper.com>
 *
 */
public class RowData {

  public final PersonWriteDto person;

  public final MembershipCreateDto membership;

  public final Optional<String> lastDay;

  public final boolean activeSwimmer;

  public final boolean activeCoach;

  public RowData(
      PersonWriteDto person,
      MembershipCreateDto membership,
      Optional<String> lastDay,
      boolean activeSwimmer,
      boolean activeCoach) {
    this.person = person;
    this.membership = membership;
    this.lastDay = lastDay;
    this.activeSwimmer = activeSwimmer;
    this.activeCoach = activeCoach;
  }
}
