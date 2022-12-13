package assignment3.task1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Violation {
    public String type;
    public String fine_amount;
}
