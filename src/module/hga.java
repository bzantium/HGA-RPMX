package module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class hga {
    public static int[] rpmx(int[] indiv1, int[] indiv2, int nrow, int ncol, valTable table) {
        int n = nrow * ncol;
        int a,b,c,d;

        while (true) {
            a = (int) (Math.random() * nrow);
            b = (int) (Math.random() * nrow);
            c = (int) (Math.random() * ncol);
            d = (int) (Math.random() * ncol);
            if ((a!=b || c!=d)) { break; } }

        int i, j;

        ArrayList<Integer> locationP = new ArrayList<Integer>();
        ArrayList<Integer> remainder = new ArrayList<Integer>();
        ArrayList<Integer> AP = new ArrayList<Integer>();
        ArrayList<Integer> BP = new ArrayList<Integer>();
        ArrayList<Integer> AR = new ArrayList<Integer>();
        ArrayList<Integer> BR = new ArrayList<Integer>();
        ArrayList<Integer> difA;
        ArrayList<Integer> difB;
        ArrayList<Integer> intsec;

        int[] child1 = new int[n];
        int[] child2 = new int[n];
        int[] indivL = new int[n];
        int[] indivR = new int[n];

        if (utils.isSame(indiv1, indiv2)) {
            utils.initialize(indivL);
            utils.initialize(indivR);
            optimize(indivL, table);
            optimize(indivR, table);
        } else {
            utils.copyGene(indiv1, indivL);
            utils.copyGene(indiv2, indivR);
        }

        for (i=0; i<n; i++) {
            child1[i] = indivR[i];
            child2[i] = indivL[i];
        }

        for (i=Math.min(a, b); i<=Math.max(a, b); i++) {
            for (j=Math.min(c, d); j<=Math.max(c, d); j++) {
                locationP.add(ncol*i+j);
            }
        }

        for (i=0; i<nrow*ncol; i++) {
            if(!locationP.contains(i))
                remainder.add(i);
        }

        for (i=0; i<locationP.size(); i++) {
            AP.add(indivL[locationP.get(i)]);
            BP.add(indivR[locationP.get(i)]);
        }

        for (i=0; i<remainder.size(); i++) {
            AR.add(indivL[remainder.get(i)]);
            BR.add(indivR[remainder.get(i)]);
        }

        difA = intersect(AR, BP);
        difB = intersect(BR, AP);
        intsec = intersect(AP, BP);

        for (i=0; i<intsec.size(); i++) {
            swap(child1, indexOf(child1, intsec.get(i)), indexOf(indivL, intsec.get(i)));
            swap(child2, indexOf(child2, intsec.get(i)), indexOf(indivR, intsec.get(i)));
        }

        for (i=0; i<difB.size(); i++) {
            swap(child1, indexOf(indivL, difB.get(i)), indexOf(child1, difB.get(i)));
        }

        for (i=0; i<difA.size(); i++) {
            swap(child2, indexOf(indivR, difA.get(i)), indexOf(child2, difA.get(i)));
        }

        for(i=0; i<locationP.size(); i++) {
            j = locationP.get(i);
            child1[j] = indivL[j];
            child2[j] = indivR[j];
        }

        optimize(child1, table);
        optimize(child2, table);

        if (utils.calFitness(child1, table) < utils.calFitness(child2, table)) {
            return child1;
        } else {
            return child2;
        }
    }

    public static int[] rpmx_rts(int[] indiv1, int[] indiv2, int nrow, int ncol, valTable table,
                                 int min_size, int max_size, int aspiration, int n_iters) {
        int n = nrow * ncol;
        int a,b,c,d;

        while (true) {
            a = (int) (Math.random() * nrow);
            b = (int) (Math.random() * nrow);
            c = (int) (Math.random() * ncol);
            d = (int) (Math.random() * ncol);
            if ((a!=b || c!=d)) { break; } }

        int i, j;

        ArrayList<Integer> locationP = new ArrayList<Integer>();
        ArrayList<Integer> remainder = new ArrayList<Integer>();
        ArrayList<Integer> AP = new ArrayList<Integer>();
        ArrayList<Integer> BP = new ArrayList<Integer>();
        ArrayList<Integer> AR = new ArrayList<Integer>();
        ArrayList<Integer> BR = new ArrayList<Integer>();
        ArrayList<Integer> difA;
        ArrayList<Integer> difB;
        ArrayList<Integer> intsec;

        int[] child1 = new int[n];
        int[] child2 = new int[n];
        int[] indivL = new int[n];
        int[] indivR = new int[n];

        if (utils.isSame(indiv1, indiv2)) {
            utils.initialize(indivL);
            utils.initialize(indivR);
        } else {
            utils.copyGene(indiv1, indivL);
            utils.copyGene(indiv2, indivR);
        }

        for (i=0; i<n; i++) {
            child1[i] = indivR[i];
            child2[i] = indivL[i];
        }

        for (i=Math.min(a, b); i<=Math.max(a, b); i++) {
            for (j=Math.min(c, d); j<=Math.max(c, d); j++) {
                locationP.add(ncol*i+j);
            }
        }

        for (i=0; i<nrow*ncol; i++) {
            if(!locationP.contains(i))
                remainder.add(i);
        }

        for (i=0; i<locationP.size(); i++) {
            AP.add(indivL[locationP.get(i)]);
            BP.add(indivR[locationP.get(i)]);
        }

        for (i=0; i<remainder.size(); i++) {
            AR.add(indivL[remainder.get(i)]);
            BR.add(indivR[remainder.get(i)]);
        }

        difA = intersect(AR, BP);
        difB = intersect(BR, AP);
        intsec = intersect(AP, BP);

        for (i=0; i<intsec.size(); i++) {
            swap(child1, indexOf(child1, intsec.get(i)), indexOf(indivL, intsec.get(i)));
            swap(child2, indexOf(child2, intsec.get(i)), indexOf(indivR, intsec.get(i)));
        }

        for (i=0; i<difB.size(); i++) {
            swap(child1, indexOf(indivL, difB.get(i)), indexOf(child1, difB.get(i)));
        }

        for (i=0; i<difA.size(); i++) {
            swap(child2, indexOf(indivR, difA.get(i)), indexOf(child2, difA.get(i)));
        }

        for(i=0; i<locationP.size(); i++) {
            j = locationP.get(i);
            child1[j] = indivL[j];
            child2[j] = indivR[j];
        }

        tabu_search(child1, table, min_size, max_size, aspiration, n_iters);
        tabu_search(child2, table, min_size, max_size, aspiration, n_iters);

        if (utils.calFitness(child1, table) < utils.calFitness(child2, table)) {
            return child1;
        } else {
            return child2;
        }
    }

    public static int[] pos(int[] indiv1, int[] indiv2, int nrow, int ncol, valTable table) {
        int n = nrow * ncol;
        int m = n/3;
        int i, j;

        ArrayList<Integer> locationP = new ArrayList<Integer>();
        ArrayList<Integer> remainder = new ArrayList<Integer>();
        ArrayList<Integer> AP = new ArrayList<Integer>();
        ArrayList<Integer> BP = new ArrayList<Integer>();
        ArrayList<Integer> AR = new ArrayList<Integer>();
        ArrayList<Integer> BR = new ArrayList<Integer>();
        ArrayList<Integer> difA;
        ArrayList<Integer> difB;
        ArrayList<Integer> intsec;
        ArrayList<Integer> random;

        int[] child1 = new int[n];
        int[] child2 = new int[n];
        int[] indivL = new int[n];
        int[] indivR = new int[n];

        if (utils.isSame(indiv1, indiv2)) {
            utils.initialize(indivL);
            utils.initialize(indivR);
            optimize(indivL, table);
            optimize(indivR, table);
        } else {
            utils.copyGene(indiv1, indivL);
            utils.copyGene(indiv2, indivR);
        }

        utils.copyGene(indivR, child1);
        utils.copyGene(indivL, child2);

        random = utils.getRandomNonRepeatingIntegers(n, m);
        for(i=0; i<m; i++) {
            locationP.add(random.get(i));
        }

        for (i=0; i<nrow*ncol; i++) {
            if(!locationP.contains(i))
                remainder.add(i);
        }

        for (i=0; i<locationP.size(); i++) {
            AP.add(indivL[locationP.get(i)]);
            BP.add(indivR[locationP.get(i)]);
        }

        for (i=0; i<remainder.size(); i++) {
            AR.add(indivL[remainder.get(i)]);
            BR.add(indivR[remainder.get(i)]);
        }

        difA = intersect(AR, BP);
        difB = intersect(BR, AP);
        intsec = intersect(AP, BP);

        for (i=0; i<intsec.size(); i++) {
            swap(child1, indexOf(child1, intsec.get(i)), indexOf(indivL, intsec.get(i)));
            swap(child2, indexOf(child2, intsec.get(i)), indexOf(indivR, intsec.get(i)));
        }

        for (i=0; i<difB.size(); i++) {
            swap(child1, indexOf(indivL, difB.get(i)), indexOf(child1, difB.get(i)));
        }

        for (i=0; i<difA.size(); i++) {
            swap(child2, indexOf(indivR, difA.get(i)), indexOf(child2, difA.get(i)));
        }

        for(i=0; i<locationP.size(); i++) {
            j = locationP.get(i);
            child1[j] = indivL[j];
            child2[j] = indivR[j];
        }

        optimize(child1, table);
        optimize(child2, table);

        if (utils.calFitness(child1, table) < utils.calFitness(child2, table)) {
            return child1;
        } else {
            return child2;
        }
    }

    public static void scrambleMutate(int[] indiv, int k) {
        int n = indiv.length;
        int s;
        if (n <= k) { s = n - 1; }
        else { s = k; }
        ArrayList<Integer> list = utils.getRandomNonRepeatingIntegers(n, s);
        int temp = indiv[list.get(0)];

        for (int i = 0; i < s-1; i++) {
            indiv[list.get(i)] = indiv[list.get(i+1)];
        }
        indiv[list.get(s-1)] = temp;
    }

    public static void swapMutate(int[] indiv) {
        int n = indiv.length;
        int[] temp = new int[n];
        utils.copyGene(indiv, temp);
        for (int i=0; i<n; i++) {
            indiv[i] = temp[n-i-1];
        }
    }

    public static int[][] tourSelc(int[][] pop, int k) {
        int npop = pop.length;
        int n = pop[0].length;
        int[][] candidate = new int[2][n];
        ArrayList<Integer> list = new ArrayList<Integer>();
        int[] tournament = new int[k];

        for(int i=0; i<npop; i++) {
            list.add(i);
        }
        Collections.shuffle(list);

        for(int i=0; i<k; i++) {
            tournament[i] = list.get(i);
        }

        Arrays.sort(tournament);
        candidate[0] = pop[tournament[0]];
        candidate[1] = pop[tournament[1]];
        return candidate;
    }

    public static int[][] expSelc(int[][] pop, int k) {
        int npop = pop.length;
        int n = pop[0].length;
        int[][] candidate = new int[2][n];

        double commonRatio = 0.99;
        double weight = 0.1/(1-Math.pow(commonRatio, pop.length));
        randCollect<Integer> rancol = new randCollect<Integer>();

        for(int i=0; i<npop; i++) {
            rancol.add(weight, i);
            weight *= commonRatio;
        }

        candidate[0] = pop[rancol.next()];
        candidate[1] = pop[rancol.next()];
        return candidate;
    }

    public static void optimize(int[] indiv, valTable table) {
        int k, psize;
        psize = indiv.length;
        float new_value, best_value = 0;
        boolean determine;

        while (true) {
            determine = true;
            for(int i=0; i<psize; i++) {
                k = i;
                for(int j=i+1; j<psize; j++) {
                    new_value = swapCalc(indiv, table, i, j);
                    if(new_value < best_value) {
                        best_value = new_value;
                        k = j;
                    }
                }
                if(k == i)
                    continue;
                swap(indiv, i, k);

                best_value = 0;
                determine = false;
            }
            if(determine)
                break;
        }
    }

    public static ArrayList<Integer> intersect(ArrayList<Integer> list1, ArrayList<Integer> list2) {
        ArrayList<Integer> list = new ArrayList<Integer>(list1);
        list.retainAll(list2);
        return list;
    }

    public static int indexOf(int[] vector, int k) {
        int index = 0;
        for (int i=0; i<vector.length; i++) {
            if (vector[i] == k) {
                index = i;
                break;
            }
            if (i == vector.length-1) {
                System.out.println("vector has no given value!");
            }
        }
        return index;
    }

    public static int swapCalc(int[] p, valTable table, int i, int j) {
        int n = p.length;
        int[][] a = table.getFlow();
        int[][] b = table.getDist();
        int d; int k;
        d = (a[i][i]-a[j][j])*(b[p[j]][p[j]]-b[p[i]][p[i]]) +
                (a[i][j]-a[j][i])*(b[p[j]][p[i]]-b[p[i]][p[j]]);
        for (k = 0; k < n; k++) {
            if (k!=i && k!=j) {
                d += (a[k][i]-a[k][j])*(b[p[k]][p[j]]-b[p[k]][p[i]]) + (a[i][k]-a[j][k])*(b[p[j]][p[k]]-b[p[i]][p[k]]);
            }
        }
        return d;
    }

    public static int swapCalc_part(int[] p, valTable table, int[][] delta, int i, int j, int r, int s) {
        int[][] a = table.getFlow();
        int[][] b = table.getDist();

        return (delta[i][j] + (a[r][i] - a[r][j] + a[s][j] - a[s][i]) * (b[p[s]][p[i]] - b[p[s]][p[j]] + b[p[r]][p[j]] - b[p[r]][p[i]]) +
                (a[i][r] - a[j][r] + a[j][s] - a[i][s]) * (b[p[i]][p[s]] - b[p[j]][p[s]] + b[p[j]][p[r]] - b[p[i]][p[r]]));
    }

    public static void swap(int[] p, int i, int j) {
        int temp = p[i];
        p[i] = p[j];
        p[j] = temp;
    }

    public static int unif(int min_size, int max_size) {
        return min_size + (int)((max_size - min_size + 1) * Math.random());
    }

    public static void tabu_search(int[] indiv, valTable table, int min_size, int max_size, int aspiration, int n_iters) {
        int n = indiv.length;
        int[][] a = table.getFlow();
        int[][] b = table.getDist();
        int[] p = new int[n];
        int[][] delta = new int[n][n];
        int[][] tabu_list = new int[n][n];
        int c_iter;
        int current_cost, best_cost;
        int i, j, k, i_retained, j_retained;
        final int infinite = 999999999;
        int ccc = 0;

        for (i=0; i<n; i++) { p[i] = indiv[i]; }

        current_cost = 0;
        for (i=0; i<n; i++) {
            for (j=0; j<n; j++) {
                current_cost += a[i][j] * b[p[i]][p[j]];
                if (i < j) {
                    delta[i][j] = swapCalc(p, table, i, j);
                }
            }
        }
        best_cost = current_cost;

        for (i=0; i<n; i++) {
            for (j=0; j<n; j++) {
                tabu_list[i][j] = -(n * i + j);
            }
        }

        for (c_iter=0; c_iter<n_iters; c_iter++) {
            i_retained = infinite;
            j_retained = infinite;
            int min_delta = infinite;
            boolean authorized;
            boolean aspired;
            boolean already_aspired = false;

            for (i=0; i<n-1; i++) {
                for (j=i+1; j<n; j++) {
                    authorized = (tabu_list[i][p[j]] < c_iter) || (tabu_list[j][p[i]] < c_iter);
                    aspired = (tabu_list[i][p[j]] < c_iter - aspiration) ||
                            (tabu_list[j][p[i]] < c_iter - aspiration) ||
                            (current_cost + delta[i][j] < best_cost);

                    if ((aspired && !already_aspired) || (aspired && already_aspired && (delta[i][j] < min_delta)) ||
                            (!aspired && !already_aspired && (delta[i][j] < min_delta) && authorized)) {
                        i_retained = i;
                        j_retained = j;
                        min_delta = delta[i][j];
                        if (aspired) { already_aspired = true; }
                    }
                }
            }

            if (i_retained == infinite) {}
            else {
                swap(p, i_retained, j_retained);
                current_cost += delta[i_retained][j_retained];
                tabu_list[i_retained][p[j_retained]] = c_iter + unif(min_size, max_size);
                tabu_list[j_retained][p[i_retained]] = c_iter + unif(min_size, max_size);

                if (current_cost < best_cost) {
                    best_cost = current_cost;
                    for (k = 0; k < n; k++) indiv[k] = p[k];
                }

                // update matrix of the move costs
                for (i = 0; i < n-1; i++) {
                    for (j = i + 1; j < n; j++) {
                        if (i != i_retained && i != j_retained && j != i_retained && j != j_retained) {
                            delta[i][j] = swapCalc_part(p, table, delta, i, j, i_retained, j_retained);
                        } else {
                            delta[i][j] = swapCalc(p, table, i, j);
                        }
                    }
                }
            }
            if (c_iter % 100000 == 0) {
                ccc++;
                if (ccc % 2 == 0) { aspiration = unif(1, 100); }
                else { aspiration = unif(1, n * n * 10); }
            }
        }
    }
}
