package task4;

public class User {
    private String login;
    private String name;
    private String pwd;

    public User(String login, String name, String pwd) {
        this.login = login;
        this.name = name;
        this.pwd = pwd;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getPwd() {
        return pwd;
    }
}
