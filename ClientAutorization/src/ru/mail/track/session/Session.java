package ru.mail.track.session;

/**
 * ����� �������� ���������� � ������� ������ ��������������
 * ���� ��� ��������� ������� �����, �������� ����������� ��� �����-�� ���������
 */
public class Session {

    private User sessionUser;

    public Session() {
    }

    public User getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(User sessionUser) {
        this.sessionUser = sessionUser;
    }
}