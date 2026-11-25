package shared;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

// Melinda
public class PieceData {
    // Database of all pieces, defined as boolean[][]
    public final static boolean[][][] pieces;

    static {
        String[] fileNames = {
            "One1.txt", "Two1.txt", "Three1.txt", "Three2.txt",
            "Four1.txt", "Four2.txt", "Four3.txt", "Four4.txt", "Four5.txt",
            "Five1.txt", "Five2.txt", "Five3.txt", "Five4.txt", "Five5.txt",
            "Five6.txt", "Five7.txt", "Five8.txt", "Five9.txt", "Five10.txt",
            "Five11.txt", "Five12.txt"
        };

        ArrayList<boolean[][]> list = new ArrayList<>();

        // Loop through file names and load each one
        for (String name : fileNames) {
            boolean[][] piece = loadPiece(name);
            if (piece != null) {
                list.add(piece);
            }
        }

        pieces = list.toArray(new boolean[0][][]);
    }

    private static boolean[][] loadPiece(String fileName) {
        File file = new File("resources/files/" + fileName);
        if (!file.exists()) {
            file = new File("src/resources/files/" + fileName);
        }
        if (!file.exists()) return null;

        ArrayList<String> lines = new ArrayList<>();
        int maxLen = 0;
        boolean capturingFirstOrientation = false;
        int expectedRows = 0;
        int currentRowCount = 0;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                
                if (line.trim().matches("\\d+")) {
                    if (!lines.isEmpty()) {
                        break;
                    }
                    expectedRows = Integer.parseInt(line.trim());
                    currentRowCount = 0;
                    capturingFirstOrientation = true;
                    continue;
                }

                if (capturingFirstOrientation && currentRowCount < expectedRows) {
                    lines.add(line);
                    if (line.length() > maxLen) {
                        maxLen = line.length();
                    }
                    currentRowCount++;
                }
            }
        } catch (Exception e) {
            return null;
        }

        if (lines.isEmpty()) return null;

        boolean[][] grid = new boolean[lines.size()][maxLen];
        for (int r = 0; r < lines.size(); r++) {
            String l = lines.get(r);
            for (int c = 0; c < maxLen; c++) {
                if (c < l.length()) {
                    char ch = l.charAt(c);
                    grid[r][c] = (ch == 'x' || ch == 'X');
                }
            }
        }
        return grid;
    }
}