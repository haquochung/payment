package org.example.payment.service;

import org.example.payment.model.Account;
import org.example.payment.model.Command;

import java.beans.PropertyChangeEvent;
import java.util.Objects;

import static org.example.payment.service.ObservableService.LOGIN_STATE;

public class HelpService extends AbstractService {
    @Override
    public void executeCommand(Command command, String... args) {
        if (Objects.requireNonNull(command) == Command.HELP) {
            printHelp();
        }
    }

    private void printHelp() {
        System.out.println("Usage:");
        System.out.println("\t<command> [arguments]");
        System.out.println("Commands:");
        for (Command command : Command.values()) {
            if (command == Command.HELP || command == Command.EXIT
                    || (account == null && !command.isRequiredLogin())
                    || (account != null && command.isRequiredLogin())) {
                System.out.println("\t" + command);
            }
        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(LOGIN_STATE)) {
            this.account = (Account) evt.getNewValue();
        }
    }
}
