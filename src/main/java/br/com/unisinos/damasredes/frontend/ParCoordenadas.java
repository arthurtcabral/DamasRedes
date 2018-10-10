package br.com.unisinos.damasredes.frontend;

import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class ParCoordenadas {

    private boolean valido = false;
    private int x;
    private int y;

    private static final Pattern PATTERN = Pattern.compile("\\d+,\\d+");

    public void processar(String input) {
        if (input == null || PATTERN.matcher(input).matches()) {
            valido = true;
            String[] numbers = input.split(",");
            this.x = Integer.parseInt(numbers[0]);
            this.y = Integer.parseInt(numbers[1]);
        } else {
            valido = false;
        }
    }
}
