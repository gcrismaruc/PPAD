package main.actions;

import main.Client;
import main.threads.GetResult;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetResultActionListener implements ActionListener {
    Client client;

    public GetResultActionListener(Client client) {
        this.client = client;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new Thread(new GetResult(client)).start();
    }
}
