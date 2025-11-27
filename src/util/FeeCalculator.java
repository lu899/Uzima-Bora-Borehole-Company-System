package util;

public class FeeCalculator {
    public static double calculateTieredCost(double meters){
        double cost = 0;
        if (meters > 0 && meters <= 100) {
            cost = meters * 1000;
        } else if (meters >= 101 && meters <=200) {
            cost = meters * 1500;
        } else if (meters >= 201 && meters <= 300) {
            cost = meters * 2000;
        } else if (meters > 300) {
            cost = meters * 2500;
        } else {
            throw new IllegalArgumentException("Invalid unit of measure");
        }
        return cost;
    }

    public static double calculateDepthCharges(double depth){
        return calculateTieredCost(depth);
    }

    public static double calculateHeightCharges(double height){
        return calculateTieredCost(height);
    }

    public static double calculatePipeTieredCost(double diameter){
        double cost = 0;
        if (diameter > 0 && diameter <= 0.5) {
            cost = 150;
        } else if (diameter > 0.5 && diameter <= 0.75) {
            cost = 200;
        } else if (diameter <= 1.0) {
            cost = 300;
        } else if (diameter <= 1.25) {
            cost = 400;
        } else if (diameter <= 1.5) {
            cost = 500;
        } else if (diameter <= 2.0) {
            cost = 700;
        } else if (diameter <= 2.5) {
            cost = 900;
        } else if (diameter <= 3.0) {
            cost = 1200;
        } else if (diameter <= 4.0) {
            cost = 1600;
        } else if (diameter > 4.0) {
            cost = 2000;
        } else {
            throw new IllegalArgumentException("Invalid Pipe Diameter");
        }
        return cost;
    }

    public static double calculateOutletCosts(int numOfOutlets){
        double cost = 0;
        if(numOfOutlets > 0 && numOfOutlets <= 3){
            cost = 2500;
        } else if (numOfOutlets >= 4 && numOfOutlets <=6) {
            cost = 2000;
        } else if (numOfOutlets >= 7 && numOfOutlets <= 10) {
            cost = 1800;
        } else if (numOfOutlets  > 10) {
            cost = 1500;
        }
        return cost;
    }

    public static double calculateOutletCharges(int numOfOutlets){
        return calculateOutletCosts(numOfOutlets) * numOfOutlets;
    }

    public static double calculateDiamterCharges(double diameter, double length){
        return calculatePipeTieredCost(diameter) * length;
    }
}
