package model;

public enum ClientCategory {
    INDUSTRIAL(20000, 50000),
    COMMERCIAL(15000, 30000),
    DOMESTIC(7000, 10000);

    private final double surveyFees;
    private final double localAuthorityFees;

    ClientCategory(double surveyFees, double localAuthorityFees) {
        this.surveyFees = surveyFees;
        this.localAuthorityFees = localAuthorityFees;
    }

    public double getSurveyFees(){
        return this.surveyFees;
    }

    public double getLocalAuthorityFees(){
        return this.localAuthorityFees;
    }
}
