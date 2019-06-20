package module;

public class solSet {
    private int opt;
    private int[] solution;

    public solSet(int opt, int[] solution) {
        this.opt = opt;
        this.solution = solution;
    }

    public void setOpt(int opt) {
        this.opt = opt;
    }

    public void segSolution(int[] solution) {
        this.solution = solution;
    }

    public int getOpt() {
        return this.opt;
    }

    public int[] getSolution() {
        return this.solution;
    }
}
