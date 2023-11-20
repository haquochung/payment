package org.example.payment.service;

import org.example.payment.model.Command;

public interface IService {
    void executeCommand(Command command, String... args) throws Exception;
}
