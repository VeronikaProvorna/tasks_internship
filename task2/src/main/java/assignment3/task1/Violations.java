package assignment3.task1;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.ArrayList;
import java.util.List;

public class Violations {
    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Violation> violation = new ArrayList<>();
}
