import java.util.Random;
import java.util.Scanner;

public class Mastermind {

    enum Color { RED, GREEN, BLUE, YELLOW, BLACK, WHITE, GRAY, PURPLE }

    private static final int NUM_PAWNS = 5;
    private static final int NUM_COLORS = 8;
    private static final int NUM_ATTEMPTS = 10;
    private Color[] hiddenCombination = new Color[NUM_PAWNS];
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Mastermind game = new Mastermind();
        game.playGame();
    }

    private void playGame() {
        generateHiddenCombination();
        boolean hasWon = false;
        for (int attempt = 0; attempt < NUM_ATTEMPTS; attempt++) {
            Color[] proposedCombination = readProposedCombination();
            int[] result = evaluateProposedCombination(hiddenCombination, proposedCombination);
            System.out.println("Well-placed pawns: " + result[0] + ", Misplaced pawns: " + result[1]);

            if (result[0] == NUM_PAWNS) {
                hasWon = true;
                break;
            }
        }
        if (hasWon) {
            System.out.println("Congratulations! You've cracked the code!");
        } else {
            System.out.println("Game over! Better luck next time.");
        }
    }

    private Color[] readProposedCombination() {
        Color[] proposal = new Color[NUM_PAWNS];
        System.out.println("Enter your combination (e.g., RED GREEN BLUE YELLOW BLACK):");
        for (int i = 0; i < NUM_PAWNS; i++) {
            String colorInput = scanner.next().toUpperCase();
            proposal[i] = Color.valueOf(colorInput);
        }
        return proposal;
    }

    private int[] evaluateProposedCombination(Color[] hiddenCombination, Color[] proposedCombination) {
        int wellPlaced = 0;
        int misplaced = 0;
        boolean[] matched = new boolean[NUM_PAWNS];

        for (int i = 0; i < NUM_PAWNS; i++) {
            if (proposedCombination[i] == hiddenCombination[i]) {
                wellPlaced++;
                matched[i] = true;
            }
        }

        for (int i = 0; i < NUM_PAWNS; i++) {
            if (proposedCombination[i] != hiddenCombination[i]) {
                for (int j = 0; j < NUM_PAWNS; j++) {
                    if (i != j && !matched[j] && proposedCombination[i] == hiddenCombination[j]) {
                        misplaced++;
                        matched[j] = true;
                        break;
                    }
                }
            }
        }
        return new int[]{wellPlaced, misplaced};
    }

    private void generateHiddenCombination() {
        Random random = new Random();
        for (int i = 0; i < NUM_PAWNS; i++) {
            hiddenCombination[i] = Color.values()[random.nextInt(NUM_COLORS)];
        }
    }
}
