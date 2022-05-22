package org.statistical.analysis.pojo;

public class NGramResult implements Comparable<NGramResult> {
    private int nGramSize;
    private int appearancesInA;
    private int appearancesInB;

    public int getNGramSize() {
        return nGramSize;
    }

    public void setNGramSize(int NGramSize) {
        this.nGramSize = NGramSize;
    }

    public int getAppearancesInA() {
        return appearancesInA;
    }

    public void setAppearancesInA(int appearancesInA) {
        this.appearancesInA = appearancesInA;
    }

    public int getAppearancesInB() {
        return appearancesInB;
    }

    public void setAppearancesInB(int appearancesInB) {
        this.appearancesInB = appearancesInB;
    }


    @Override
    public int compareTo(NGramResult o) {
        if (this.nGramSize > o.getNGramSize()) {
            return 1;
        } else if (this.nGramSize < o.getNGramSize()) {
            return -1;
        } else {
            return Integer.compare(this.appearancesInA + this.appearancesInB, o.getAppearancesInA() + o.getAppearancesInB());
        }
    }
}
