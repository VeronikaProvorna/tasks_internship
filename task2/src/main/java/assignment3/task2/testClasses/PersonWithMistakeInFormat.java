package assignment3.task2.testClasses;

import assignment3.task2.Property;

import java.time.Instant;

public class PersonWithMistakeInFormat {
    private String stringProperty;

    private Integer numberProperty;

    @Property(format = "dd.MM.yyyy HH")
    private Instant timeProperty;

    public String getStringProperty() {
        return stringProperty;
    }

    public void setStringProperty(String stringProperty) {
        this.stringProperty = stringProperty;
    }

    public int getNumberProperty() {
        return numberProperty;
    }

    public void setNumberProperty(int myNumber) {
        this.numberProperty = myNumber;
    }

    public Instant getTimeProperty() {
        return timeProperty;
    }

    public void setTimeProperty(Instant timeProperty) {
        this.timeProperty = timeProperty;
    }
}
