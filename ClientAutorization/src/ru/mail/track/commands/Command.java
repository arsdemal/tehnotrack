package ru.mail.track.commands;

import ru.mail.track.session.Session;

import java.io.IOException;

/**
 * ��������� ��� ���� ������
 *
 *  �� ����, ���� ���� ����������� ���������� ����� ����������� ����� execute() � ��������� ���������
 * ������ ��� ��������� ���������� ��������� (��������)
 *
 * � ���� ������������ ������ - ����������������� ����
 */
public interface Command {


    /**
     * ����� ����� ���������� ���������, ��������� ��� ����� �������
     * ��������� ���������� ��������������� � ����� ������� Result
     *
     * � �������� ������ ������� void
     */
    void execute(Session session, String[] args) throws IOException;
}