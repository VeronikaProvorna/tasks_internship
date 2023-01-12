package task4;

import java.util.List;
/*
* Use JWT for auth,don't save password witout hashing, u need add hashing with secret word ( salt ).
*/
public class UserDao {
    private List<User> users = List.of(
            new User("nika", "Provorna Veronika", "nika123"),
            new User("pasha", "Ivanenko Pavel", "pasha123"),
            new User("dasha", "Lipa Darya", "dasha123"),
            new User("ivan", "Pavlenko Ivan", "ivan123"),
            new User("nikita", "Nikitenko Nikita", "nikita123")
    );

    public boolean isUserExist(String login) {
        if (login == null) {
            throw new IllegalArgumentException("Login is empty");
        }

        for (User user : users) {
            if (login.equals(user.getLogin())) return true;
        }
        return false;
    }


    public boolean checkPassword(String login, String pwd) {
        if (login == null || pwd == null) {
            throw new IllegalArgumentException("Login or password is empty");
        }

        for (User user : users) {
            if (login.equals(user.getLogin()) && pwd.equals(user.getPwd())) return true;
        }
        return false;
    }


    public List<User> getUsers() {
        return users;
    }
}
