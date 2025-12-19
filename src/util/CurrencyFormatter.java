package util;

public class CurrencyFormatter {
    public static String formatcompact(double amount){
        if (amount >= 1000000) {
            double millions = amount / 1000000;
            return "KES " + formatDecimal(millions, 1) + "M";
        } else if (amount >= 1000) {
            double thousands = amount / 1000;
            return "KES " + formatDecimal(thousands, 1) + "K";
        } else {
            return "KES " + formatDecimal(amount, 0);
        }
    }

    public static String formatDecimal(double value, int decimalPlaces){
        if (decimalPlaces == 0) {
            return String.format("%.0f", value);
        } else {
            String formatted = String.format("%." + decimalPlaces + "f", value);
            formatted = formatted.replaceAll("0+$", "").replaceAll("\\.$", "");
            return formatted;
        }
    }
}
