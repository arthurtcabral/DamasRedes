package br.com.unisinos.damasredes;

import lombok.extern.log4j.Log4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Log4j
public class GameProps {

    private static GameProps instance;

    private Properties properties;

    public static Properties get() {
        if (instance == null) {
            instance = new GameProps();
        }
        return instance.properties;
    }

    private GameProps() {
        readPropsFile();
    }

    private void readPropsFile() {
        try {
            InputStream input = new FileInputStream("damasredes.properties");
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            log.error("Problema na leitura do arquivo de properties");
        }
    }
}
