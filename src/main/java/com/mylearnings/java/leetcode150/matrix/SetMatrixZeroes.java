package com.mylearnings.java.leetcode150.matrix;

public class SetMatrixZeroes {

    public static void main(String[] args) {

        int[][] matrix = {
                {1, 1, 1},
                {1, 0, 1},
                {1, 1, 1}
        };

        SetMatrixZeroes zeroes = new SetMatrixZeroes();
        zeroes.setZeroes(matrix); // Output: [[1,0,1],[0,0,0],[1,0,1]]
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void setZeroes(int[][] matrix) {
        int rowSize = matrix.length;
        int columnSize = matrix[0].length;
        boolean[][] transformed = new boolean[rowSize][columnSize];
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                if (matrix[i][j] == 0 && !transformed[i][j]) {
                    applyZeroesToColumns(matrix, j, rowSize, transformed);
                    applyZeroesToRows(matrix, i, columnSize, transformed);
                }
            }
        }
    }

    private void applyZeroesToRows(int[][] matrix, int rowIndex, int columnSize, boolean[][] transformed) {
        for (int i = 0; i < columnSize; i++) {
            if (matrix[rowIndex][i] != 0) {
                transformed[rowIndex][i] = true;
                matrix[rowIndex][i] = 0;
            }
        }
    }

    private void applyZeroesToColumns(int[][] matrix, int columnIndex, int rowSize, boolean[][] transformed) {
        for (int i = 0; i < rowSize; i++) {
            if (matrix[i][columnIndex] != 0) {
                transformed[i][columnIndex] = true;
                matrix[i][columnIndex] = 0;
            }
        }
    }

}
