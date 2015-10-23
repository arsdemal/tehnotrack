package ru.mail.track.commands;

import java.util.Map;

import ru.mail.track.session.Session;

/**
 * ������� ������
 */
public class HelpCommand implements Command {

    private Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(Session session, String[] args) {
        System.out.println("Executing help");
        /*
        � ������� ������ ������ ������� ������ �� �������
        ���� ����� �������� ���� ����, �� ������� �������� �������� ����� ������ ��� ������ � �����
         */
    }
}