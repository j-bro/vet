package com.cosium.vet.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Created on 21/02/18.
 *
 * @author Reda.Housni-Alaoui
 */
public abstract class NonBlankString {

  private final String value;

  protected NonBlankString(String value) {
    if (StringUtils.isBlank(value)) {
      throw new IllegalArgumentException(getClass().getSimpleName() + " can't be blank");
    }
    this.value = value;
  }

  public String value() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    NonBlankString that = (NonBlankString) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return value;
  }
}