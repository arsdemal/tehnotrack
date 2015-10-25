package ru.mail.track.authorization;

import ru.mail.track.session.User;


/**
 * ’ороша€ иде€ спр€тать реализацию хранилища за интерфейсом
 * “огда практически везде мы будем работать с UserStore, не знаю как он реализован внутри
 *
 * ѕомен€ть код нам придетс€ только в месте инициализации UserStore
 *
 */
public interface UserStore {
    // проверить, есть ли пользователь с таким именем
    // если есть, вернуть true
    boolean isUserExist(String name);

    // ƒобавить пользовател€ в хранилище
    void addUser(User user);

    // ѕолучить пользовател€ по имени и паролю
    User getUser(String name, String pass);

    Integer getLastId();
}