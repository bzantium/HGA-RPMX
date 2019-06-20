package module;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class utils {
    public static valTable readFile(String file, boolean rect) throws IOException {
        Scanner input = new Scanner(new File(file));
        int num = Integer.parseInt(input.next());
        int nrow = num;
        int ncol = num;
        if (rect) {
            for (int i = 2; i < num / 2; i++) {
                if (num % i == 0) {
                    if (nrow + ncol > i + num / i) {
                        nrow = i;
                        ncol = num / i;
                    }
                }
            }
        } else {
            nrow = 1;
            ncol = num;
        }

        int[][] flow = new int[num][num];
        int[][] dist = new int[num][num];
        valTable valueTable = new valTable(nrow, ncol);

        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                flow[i][j] = Integer.parseInt(input.next());
            }
        }

        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                dist[i][j] = Integer.parseInt(input.next());
            }
        }
        valueTable.setFlow(flow);
        valueTable.setDist(dist);
        return valueTable;
    }

    public static solSet readSol(String file) throws IOException {
        String solFile = file.substring(0, file.length()-3);
        solFile += "sln";
        Scanner input = new Scanner(new File(solFile));
        int n = Integer.parseInt(input.next());
        int opt = Integer.parseInt(input.next());
        int[] solution = new int[n];
        for (int i=0; i<n; i++) {
            solution[i] = Integer.parseInt(input.next());
        }
        return new solSet(opt, solution);
    }

    public static void initialize(int[] p) {
        int i;
        int n = p.length;
        ArrayList<Integer> array = new ArrayList<>();
        for (i=0; i<n; i++) array.add(i);
        Collections.shuffle(array);
        for (i=0; i<n; i++) p[i] = array.get(i);
    }

    public static double calPD(double found, double sol) {
        return (found-sol)/sol * 100;
    }

    public static int calFitness(int[] solution, valTable table) {
        int size = table.getNum();
        int fitness = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                fitness += (table.getFlowValue(i, j) * table.getDistValue(solution[i], solution[j]));
            }
        }
        return fitness;
    }

    public static int getFittestIndex(int[][] population, valTable table) {
        int n = population.length;
        int j = 0;
        int fitness;
        int best = calFitness(population[0], table);
        for (int i=1; i<n; i++) {
            fitness = calFitness(population[i], table);
            if(fitness < best) {
                best = fitness;
                j = i;
            }
        }
        return j;
    }

    public static ArrayList<Integer> getRandomNonRepeatingIntegers(int n, int m) {
        ArrayList<Integer> numbers = new ArrayList<>();
        Random random = new Random();

        while (numbers.size() < m) {
            int num = random.nextInt(n);
            if (!numbers.contains(num)) { numbers.add(num); }
        }
        return numbers;
    }

    public static int[][] sortPop(int[][] population, valTable table) {
        int npop = population.length;
        int n = population[0].length;
        int[][] newPop = new int[npop][n];
        Iterator<Integer> it = sortByValue(population, table).iterator();
        for(int i=0; i<npop; i++)
            newPop[npop-i-1] = population[it.next()];
        return newPop;
    }

    public static List<Integer> sortByValue(int[][] pop, valTable table) {
        HashMap<Integer, Float> map = new HashMap<Integer, Float>();
        for(int i=0; i<pop.length; i++) {
            map.put(i, (float)utils.calFitness(pop[i], table));
        }
        List<Integer> list = new ArrayList<Integer>();
        list.addAll(map.keySet());

        Collections.sort(list, new Comparator<Object>() {
            @SuppressWarnings({ "rawtypes", "unchecked" })
            public int compare(Object o1,Object o2) {
                Object v1 = map.get(o1);
                Object v2 = map.get(o2);
                return ((Comparable) v2).compareTo(v1);
            }
        });
        return list;
    }

    public static boolean isSame(int[] A, int[] B) {
        boolean same = true;
        int n = A.length;
        for (int i=0; i<n; i++) {
            if (A[i] != B[i]) {
                same = false;
                break;
            }
        }
        return same;
    }


    public static boolean isSameWithSolution(int[] found, int[] solution) {
        boolean same = true;
        int n = found.length;
        for (int i=0; i<n; i++) {
            if (found[i]+1 != solution[i]) {
                same = false;
                break;
            }
        }
        return same;
    }

    public static void copyPop(int[][] pop, int[][] newpop) {
        int n = pop.length;
        int m = pop[0].length;
        for (int i=0; i<n; i++) {
            for (int j=0; j<m; j++) {
                newpop[i][j] = pop[i][j];
            }
        }
    }

    public static void copyGene(int[] indiv, int[] newindiv) {
        int n = indiv.length;
        for (int i=0; i<n; i++) {
            newindiv[i] = indiv[i];
        }
    }
}
