package main.java.basics;

import java.util.*;

/*

You are given two strings A (length N) and B (length M), consisting of lowercase english letters.
Your task is to transform string A into string B using a sequence of operations minimising the total cost.
There are three types of operations.
1. Delete a character form A. This operation has a fixed cost D.
2. Insert a character into A. This operation has a fixed cost I.
2. Replace a character A[i] with B[j]. The cost of this operation is | rank(A[i])-rank(B[j]) |, where rank(c) is the position of character c in the alphabet (e.g. rank('a') = 1, rank('b') = 2, ... rank('z') = 26). If A[i] is already equal to B[j], the cost of replacement is zero.
Find the minimum total cost to transform string A into string B.

*/

public class Main {

    public static int solve(int D, int I, String A, String B) {
        int n = A.length();
        int m = B.length();

        // Create a 2D array to store the minimum cost of transforming A[0..i] to B[0..j]
        int[][] dp = new int[n + 1][m + 1];

        // Initialize the base cases
        for (int i = 0; i <= n; i++) {
            dp[i][0] = i * D; // Cost of deleting all characters from A
        }
        for (int j = 0; j <= m; j++) {
            dp[0][j] = j * I; // Cost of inserting all characters from B
        }

        // Fill the dp array
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                // int costReplace = Math.abs(A.charAt(i - 1) - B.charAt(j - 1)); // Cost of replacing A[i-1] with B[j-1]
                int costReplace = Math.abs((A.charAt(i - 1)-'a') - (B.charAt(j - 1)-'a')); // Assuming characters are lowercase letters
                dp[i][j] = Math.min(dp[i - 1][j] + D, // Cost of deleting A[i-1]
                            Math.min(dp[i][j - 1] + I, // Cost of inserting B[j-1]
                                    dp[i - 1][j - 1] + costReplace)); // Cost of replacing A[i-1] with B[j-1]
            }
        }

        return dp[n][m]; // The minimum cost to transform A into B
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int D = sc.nextInt();
        int I = sc.nextInt();
        String A = sc.next();
        String B = sc.next();

        int result = solve(D, I, A, B);
        System.out.println(result);
    }
}
