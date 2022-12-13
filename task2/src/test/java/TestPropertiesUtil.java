import assignment3.task2.PropertiesUtil;
import assignment3.task2.testClasses.EmptyPerson;
import assignment3.task2.testClasses.Person;
import assignment3.task2.testClasses.PersonWithMistakeInFormat;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class TestPropertiesUtil {

    @Test
    public void testWithCorrectFiledAndValues() throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
        Person expectedPerson = new Person();
        expectedPerson.setStringProperty("nika");
        expectedPerson.setMyNumber(10);
        LocalDateTime ldt = LocalDateTime.parse("29.11.2022 18:30", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        expectedPerson.setTimeProperty(ldt.atZone(ZoneId.systemDefault()).toInstant());

        ClassLoader classLoader = PropertiesUtil.class.getClassLoader();
        File file = new File(classLoader.getResource("pr1.properties").getFile());
        Path propertyFilePath = file.toPath();
        Object instanceLoadedFromProps = PropertiesUtil.loadFromProperties(Person.class, propertyFilePath);

        assertThat(instanceLoadedFromProps)
                .usingRecursiveComparison()
                .isEqualTo(expectedPerson);

    }

    @Test(expected = NullPointerException.class)
    public void testWithLackOfProperties() throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
        ClassLoader classLoader = PropertiesUtil.class.getClassLoader();
        File file = new File(classLoader.getResource("pr2.properties").getFile());
        Path propertyFilePath = file.toPath();
        Object obj = PropertiesUtil.loadFromProperties(Person.class, propertyFilePath);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithNullArgs() throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
        Object obj = PropertiesUtil.loadFromProperties(null, null);
    }

    @Test(expected = IOException.class)
    public void testWithEmptyPropFile() throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
        ClassLoader classLoader = PropertiesUtil.class.getClassLoader();
        File file = new File(classLoader.getResource("empty.properties").getFile());
        Path propertyFilePath = file.toPath();
        Object obj = PropertiesUtil.loadFromProperties(Person.class, propertyFilePath);
    }

    @Test(expected = DateTimeException.class)
    public void testBadTimeFormat() throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
        ClassLoader classLoader = PropertiesUtil.class.getClassLoader();
        File file = new File(classLoader.getResource("pr1.properties").getFile());
        Path propertyFilePath = file.toPath();
        Object obj = PropertiesUtil.loadFromProperties(PersonWithMistakeInFormat.class, propertyFilePath);
    }

    @Test(expected = NoSuchFieldException.class)
    public void testWithClassWithoutFields() throws IOException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        ClassLoader classLoader = PropertiesUtil.class.getClassLoader();
        File file = new File(classLoader.getResource("pr1.properties").getFile());
        Path propertyFilePath = file.toPath();
        Object obj = PropertiesUtil.loadFromProperties(EmptyPerson.class, propertyFilePath);
    }
}
