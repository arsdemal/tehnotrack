package ru.mail.track;


public class UserStore {

    User users[] = new User[100];
    int count = 0;
    // Вам нужно выбрать, как вы будете хранить ваших пользователей, например в массиве User users[] = new User[100];

    // проверить, есть ли пользователь с таким именем
    // если есть, вернуть true
    boolean isUserExist(String name) {
        for (int i = 0; i < count; i++) {
            if (users[i].getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // Добавить пользователя в хранилище
    void addUser(User user) {
        users[count++] = user;
    }

    // Получить пользователя по имени и паролю
    User getUser(String name, String pass) {
        for (int i = 0; i < count; i++) {
            if (users[i].getName().equals(name) && users[i].getPass().equals(pass)) {
                return users[i];
            }
        }
        return null;
    }
}
