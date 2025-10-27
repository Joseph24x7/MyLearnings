package com.mylearnings.java.designpatterns.creational;

import lombok.ToString;

@ToString
class Products {

    private final String part1;
    private final String part2;
    private final String part3;

    private Products(String part1, String part2, String part3) {
        this.part1 = part1;
        this.part2 = part2;
        this.part3 = part3;
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder {
        private String part1;
        private String part2;
        private String part3;

        private ProductBuilder() {
        }

        public ProductBuilder part1(String part1) {
            this.part1 = part1;
            return this;
        }

        public ProductBuilder part2(String part2) {
            this.part2 = part2;
            return this;
        }

        public ProductBuilder part3(String part3) {
            this.part3 = part3;
            return this;
        }

        public Products build() {
            return new Products(part1, part2, part3);
        }
    }
}

public class BuilderDesignPattern {

    public static void main(String[] args) {
        Products product = Products.builder()
                .part1("Part 1")
                .part2("Part 2")
                .part3("Part 3")
                .build();

        // Display the product
        System.out.println(product);
    }
}

// @Builder from lombok has been implemented internally
