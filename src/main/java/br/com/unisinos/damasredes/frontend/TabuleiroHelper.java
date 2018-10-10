package br.com.unisinos.damasredes.frontend;

import static java.lang.String.format;

public class TabuleiroHelper {

    private static final String LABELS_HEADER = "   A B C D E F G H  \n";
    private static final String HORIZONTAL_BORDER = "  ─────────────────  \n";
    private static final String GAME_LINE = "%d| %s %s %s %s %s %s %s %s |\n";

    public static String print(int[][] tabuleiro) {
        StringBuilder builder = new StringBuilder()
                .append(LABELS_HEADER)
                .append(HORIZONTAL_BORDER);

        for (int y = 0; y < 8; y++) {
            builder.append(getLine(y, tabuleiro));
        }

        return builder
                .append(HORIZONTAL_BORDER)
                .toString();
    }

    private static String getLine(int y, int[][] tab) {
        return format(GAME_LINE, y, tab[y][0], tab[y][1], tab[y][2], tab[y][3], tab[y][4], tab[y][5], tab[y][6], tab[y][7]);
    }
}
