package com.mylearnings.java.datastructures.matrix;

public class NumberOfZeroGroups {

    public static void main() {

        int[][] matrix = {
                {0, 1, 0, 0, 1},
                {0, 1, 1, 0, 1},
                {1, 1, 0, 1, 0},
                {0, 0, 1, 1, 1},
                {1, 0, 0, 0, 1}
        };

        NumberOfZeroGroups numberOfZeroGroups = new NumberOfZeroGroups();

        int groups = numberOfZeroGroups.countZeroGroups(matrix, 5, 5);
        System.out.println("Number of zero groups: " + groups);
    }

    private int countZeroGroups(int[][] grid, int rowSize, int columnSize) {

        boolean[][] visited = new boolean[rowSize][columnSize];
        int count = 0;

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {

                if (!visited[i][j] && grid[i][j] == 0) {
                    searchForGroupsUsingDfs(grid, visited, i, j, rowSize, columnSize);
                    count++;
                }

            }
        }

        return count;

    }

    private static void searchForGroupsUsingDfs(int[][] grid, boolean[][] visited, int i, int j, int rowSize, int columnSize) {

        if (!(i < 0 || j < 0 || i >= rowSize || j >= columnSize || visited[i][j] || grid[i][j] != 0)) {
            visited[i][j] = true;

            searchForGroupsUsingDfs(grid, visited, i + 1, j, rowSize, columnSize);
            searchForGroupsUsingDfs(grid, visited, i - 1, j, rowSize, columnSize);
            searchForGroupsUsingDfs(grid, visited, i, j + 1, rowSize, columnSize);
            searchForGroupsUsingDfs(grid, visited, i, j - 1, rowSize, columnSize);
        }

    }

}
