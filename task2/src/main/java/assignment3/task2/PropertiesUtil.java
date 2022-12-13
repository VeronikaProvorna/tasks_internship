package assignment3.task2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Properties;

public class PropertiesUtil {
    public static <T> T loadFromProperties(Class<T> cls, Path propertiesPath) throws InvocationTargetException, IllegalAccessException, IOException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
        if (cls == null || propertiesPath == null) {
            throw new IllegalArgumentException("Arguments are null");
        }

        if (cls.getDeclaredFields().length == 0) {
            throw new NoSuchFieldException("Class has no fields");
        }

        Properties props = getPropertiesFromFile(propertiesPath);
        if (props.isEmpty()) {
            throw new IOException("Properties file is null");
        }

        Constructor<?> cons = getEmptyConstructor(cls);
        if (cons == null) {
            throw new NullPointerException("Class " + cls + " has no empty constructors");
        }

        Object newObj = cons.newInstance();

        for (Field field : cls.getDeclaredFields()) {
            Property annotations = field.getAnnotation(Property.class);
            String fieldName = field.getName();
            Class fieldType = field.getType();

            //check if there is a value for a field or an annotated field in properties
            //otherwise throw error
            if (annotations != null && !annotations.name().equals("") && !props.containsKey(annotations.name())
                    || annotations == null && !props.containsKey(fieldName)) {
                throw new NullPointerException("No property for field " + fieldName);
            }

            Method setter = getSetterByName(cls, fieldName);
            if (setter == null) {
                throw new NoSuchMethodException("No setter for field " + fieldName);
            }

            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            LocalDateTime ldt = null;

            if (annotations != null && props.containsKey(annotations.name())) {
                if (fieldType == String.class) {
                    setter.invoke(newObj, props.get(annotations.name()));
                }

                if (fieldType == int.class || fieldType == Integer.class) {
                    setter.invoke(newObj, Integer.valueOf(props.getProperty(annotations.name())));
                }

                if (fieldType == Instant.class) {
                    //if the format is given by annotation
                    if (!annotations.format().equals("")) {
                        df = DateTimeFormatter.ofPattern(annotations.format());
                    }
                    //check if data format and value passes
                    try {
                        ldt = LocalDateTime.parse(props.getProperty(annotations.name()), df);
                    } catch (DateTimeParseException e) {
                        throw new DateTimeException("Bad time format " + annotations.format());
                    }

                    Instant newInst = ldt.atZone(ZoneId.systemDefault()).toInstant();
                    setter.invoke(newObj, newInst);
                }
            } else {
                //if is no name annotation
                if (fieldType == String.class) {
                    setter.invoke(newObj, props.get(fieldName));
                }

                if (fieldType == int.class || fieldType == Integer.class) {
                    setter.invoke(newObj, Integer.valueOf(props.getProperty(fieldName)));
                }

                if (fieldType == Instant.class) {
                    //if the format is given by annotation
                    if (!annotations.format().equals("")) {
                        df = DateTimeFormatter.ofPattern(annotations.format());
                    }
                    //check if data format and value passes
                    try {
                        ldt = LocalDateTime.parse(props.getProperty(fieldName), df);
                    } catch (DateTimeParseException e) {
                        throw new DateTimeException("Bad time format " + annotations.format());
                    }

                    Instant newInst = ldt.atZone(ZoneId.systemDefault()).toInstant();
                    setter.invoke(newObj, newInst);
                }
            }
        }
        return (T) newObj;

    }

    private static Constructor getEmptyConstructor(Class cls) {
        if (cls == null) {
            throw new IllegalArgumentException("Class argument is null");
        }

        Constructor<?> emptyConstructor = null;
        for (Constructor cons : cls.getConstructors()) {
            if (cons.getParameters().length == 0) {
                emptyConstructor = cons;
            }
        }
        return emptyConstructor;
    }

    private static Method getSetterByName(Class cls, String fieldName) {
        if (cls == null || fieldName == null) {
            throw new IllegalArgumentException("Arguments are null");
        }

        String setterName = "set" +
                fieldName.substring(0, 1).toUpperCase() +
                fieldName.substring(1);

        Method setter = null;
        for (Method meth : cls.getDeclaredMethods()) {
            if (meth.getName().equals(setterName)) {
                setter = meth;
                break;
            }
        }
        return setter;
    }

    private static Properties getPropertiesFromFile(Path propertiesPath) {
        File propertiesFile = propertiesPath.toFile();
        Properties props = new Properties();

        try (FileInputStream fis = new FileInputStream(propertiesFile)) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }
}

