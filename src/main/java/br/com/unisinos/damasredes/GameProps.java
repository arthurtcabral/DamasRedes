package br.com.unisinos.damasredes;

import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Log4j
public class GameProps {

    private static GameProps instance;

    private Properties serverProperties;
    private Properties clientProperties;

    public static Properties getForServer() {
        if (instance == null) {
            instance = new GameProps();
        }
        return instance.serverProperties;
    }

    public static Properties getForClient() {
        if (instance == null) {
            instance = new GameProps();
        }
        return instance.clientProperties;
    }

    private GameProps() {
        readPropsFile();
    }

    private void readPropsFile() {
        try {
            InputStream serverInput = this.getClass().getClassLoader().getResourceAsStream("server.properties");
            serverProperties = new Properties();
            serverProperties.load(serverInput);

            InputStream clientInput = this.getClass().getClassLoader().getResourceAsStream("client.properties");
            clientProperties = new Properties();
            clientProperties.load(clientInput);
        } catch (IOException e) {
            log.error("Problema na leitura do arquivo de properties", e);
        }
    }
}
