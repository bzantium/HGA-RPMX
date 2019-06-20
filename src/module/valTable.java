package module;

import java.util.Random;

public class valTable {
    private int[][] flow, dist;
    private int num;
    private int nrow=0, ncol=0;
    private Random rand = new Random();

    public valTable(int num) {
        this.num = num;
        this.flow = new int[num][num];
        this.dist = new int[num][num];
    }

    public valTable(int nrow, int ncol) {
        this.nrow = nrow;
        this.ncol = ncol;
        this.num = nrow*ncol;
        this.flow = new int[num][num];
        this.dist = new int[num][num];
    }

    public void setFlow(int[][] flow) {
        this.flow = flow;
    }

    public void setDist(int[][] dist) {
        this.dist = dist;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNrow() { return this.nrow; }

    public int getNcol() { return this.ncol; }

    public int[][] getDist() {
        return this.dist;
    }

    public int[][] getFlow() {
        return this.flow;
    }

    public int getNum() {
        return this.num;
    }

    public float getFlowValue(int index1, int index2) {
        return this.flow[index1][index2];
    }

    public float getDistValue(int index1, int index2) {
        return this.dist[index1][index2];
    }

    public void generateTable() {
        int k=1;
        float lift, profit, frequency;
        for (int i=1; i<this.num; i++) {
            for (int j=0; j<i; j++) {
                lift = 0;
                rand.setSeed(1001*k);
                profit = (rand.nextFloat()*3) + 1;
                rand.setSeed(210*k);
                frequency = (rand.nextFloat()*3) + 1;
                rand.setSeed(3001*k);
                if(rand.nextFloat() > 0.1) {
                    rand.setSeed(410*k);
                    lift = (float)(rand.nextFloat()*0.4)+1;
                }
                this.flow[i][j] = -(int)(lift*profit*frequency);
                this.flow[j][i] = this.flow[i][j];
                k += 1;

                if (i - j == 1 && (j+1) % this.ncol != 0) {
                    this.dist[i][j] = 1;
                    this.dist[j][i] = 1;
                }

                if (i - j == this.ncol) {
                    this.dist[i][j] = 1;
                    this.dist[j][i] = 1;
                }
            }
        }
    }

    @Override
    public String toString() {
        String geneString = "Flow\n";
        for (int i = 0; i < nrow*ncol; i++) {
            for (int j = 0; j < nrow*ncol; j++) {
                geneString += getFlowValue(i,j);
                geneString += " ";
            }
            geneString += "\n";
        }

        geneString += "\nDist\n";
        for (int i = 0; i < nrow*ncol; i++) {
            for (int j = 0; j < nrow*ncol; j++) {
                geneString += getDistValue(i,j);
                geneString += " ";
            }
            geneString += "\n";
        }
        return geneString;
    }
}
