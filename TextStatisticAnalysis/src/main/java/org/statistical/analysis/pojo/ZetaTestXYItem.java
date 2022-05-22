package org.statistical.analysis.pojo;

import java.util.Objects;

public class ZetaTestXYItem {
    private String text;
    private double percentageAuthorA;
    private double percentageAuthorB;

    public ZetaTestXYItem() {
    }

    public ZetaTestXYItem(String text, double percentageAuthorA, double percentageAuthorB) {
        this.text = text;
        this.percentageAuthorA = percentageAuthorA;
        this.percentageAuthorB = percentageAuthorB;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getPercentageAuthorA() {
        return percentageAuthorA;
    }

    public void setPercentageAuthorA(double percentageAuthorA) {
        this.percentageAuthorA = percentageAuthorA;
    }

    public double getPercentageAuthorB() {
        return percentageAuthorB;
    }

    public void setPercentageAuthorB(double percentageAuthorB) {
        this.percentageAuthorB = percentageAuthorB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZetaTestXYItem that = (ZetaTestXYItem) o;
        return Double.compare(that.percentageAuthorA, percentageAuthorA) == 0 && Double.compare(that.percentageAuthorB, percentageAuthorB) == 0 && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, percentageAuthorA, percentageAuthorB);
    }
}
