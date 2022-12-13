package assignment3.task2.testClasses;

import assignment3.task2.Property;

import java.time.Instant;

public class Person {
    private String stringProperty;

    @Property(name = "numberProperty")
    private int myNumber;

    @Property(format = "dd.MM.yyyy HH:mm")
    private Instant timeProperty;

    public String getStringProperty() {
        return stringProperty;
    }

    public void setStringProperty(String stringProperty) {
        this.stringProperty = stringProperty;
    }

    public int getMyNumber() {
        return myNumber;
    }

    public void setMyNumber(int myNumber) {
        this.myNumber = myNumber;
    }

    public Instant getTimeProperty() {
        return timeProperty;
    }

    public void setTimeProperty(Instant timeProperty) {
        this.timeProperty = timeProperty;
    }

    @Override
    public String toString() {
        return "Person{" +
                "stringProperty='" + stringProperty + '\'' +
                ", myNumber=" + myNumber +
                ", timeProperty=" + timeProperty +
                '}';
    }
}
