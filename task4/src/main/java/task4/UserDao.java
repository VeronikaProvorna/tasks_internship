package task4;

import java.util.List;

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
            if (login.equals(user.getLogin())) {
                return true;
            }
        }
        return false;
    }

    ;

    public boolean checkPassword(String login, String pwd) {
        if (login == null || pwd == null) {
            throw new IllegalArgumentException("Login or password is empty");
        }

        for (User user : users) {

            //find user by login
            if (login.equals(user.getLogin())) {

                //check user's password
                if (pwd.equals(user.getPwd())) {
                    return true;
                } else {
                    //if we found user and his pwd is wrong - go out of the loop and return false
                    break;
                }

            }
        }
        return false;
    }


    public List<User> getUsers() {
        return users;
    }
}
