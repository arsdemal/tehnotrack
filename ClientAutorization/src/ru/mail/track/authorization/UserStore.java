package ru.mail.track.authorization;

import ru.mail.track.session.User;


/**
 * ������� ���� �������� ���������� ��������� �� �����������
 * ����� ����������� ����� �� ����� �������� � UserStore, �� ���� ��� �� ���������� ������
 *
 * �������� ��� ��� �������� ������ � ����� ������������� UserStore
 *
 */
public interface UserStore {
    // ���������, ���� �� ������������ � ����� ������
    // ���� ����, ������� true
    boolean isUserExist(String name);

    // �������� ������������ � ���������
    void addUser(User user);

    // �������� ������������ �� ����� � ������
    User getUser(String name, String pass);

    Integer getLastId();
}