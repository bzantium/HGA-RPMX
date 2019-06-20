import module.*;
import java.io.IOException;
import java.util.Collections;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class main_rts {
    public static void main (String args[]) {
        ArgumentParser parser = ArgumentParsers.newFor("main").build()
                .defaultHelp(true)
                .description("Test proposed hybrid genetic algorithm");

        parser.addArgument("-f", "--file").setDefault("None").type(String.class)
                .help("Specify path to file");

        parser.addArgument("-npop").setDefault(20).type(Integer.class)
                .help("Specify size of population");

        parser.addArgument("-r", "--rect").setDefault(true).type(Boolean.class)
                .help("Convert to Rectangle Format");

        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        int n, i, j, k, sol;
        int nrow, ncol;
        int min_size, max_size;
        int aspiration, n_iters;
        int fitness, bestFit;
        int barlen = 40;
        String progress;


        String file = ns.getString("file");
        boolean rect = ns.getBoolean("rect");
        boolean found = false;
        double startTime, endTime, PD;
        valTable table;
        solSet solset;

        try {
            table = utils.readFile(file, rect);
            solset = utils.readSol(file);
            n = table.getNum();
            nrow = table.getNrow();
            ncol = table.getNcol();

        } catch (IOException e) {
            System.out.println(e);
            return;
        }

        min_size = (int)(0.9 * n);
        max_size = (int)(1.1 * n);
        aspiration = 10 * n * n;
        n_iters = 20 * n;
        bestFit = 999999999;

        sol = solset.getOpt();
        int[] solution = solset.getSolution();
        int[][] candidate;
        int tourSize = 3;
        int ngen = ns.getInt("ngen");
        int npop = ns.getInt("npop") + 1;

        System.out.println("---------------------------------------------------------");
        System.out.println(ns);
        System.out.println("---------------------------------------------------------");

        int[][] population = new int[npop][n];
        int[][] newpop = new int[npop][n];
        int[] bestSol = new int[n];

        startTime = System.currentTimeMillis();
        for (i=0; i<npop; i++) {
            utils.initialize(population[i]);
        }

        for (i=1; i<=ngen; i++) {
            utils.sortPop(population, table);
            utils.copyGene(population[0], newpop[0]);
            k = (int)(i/((double)ngen/barlen));

            for (j=1; j<npop; j++) {
                candidate = hga.tourSelc(population, tourSize);
                newpop[j] = hga.rpmx_rts(candidate[0], candidate[1], nrow, ncol, table,
                        min_size, max_size, aspiration, n_iters);

                fitness = utils.calFitness(newpop[j], table);
                if (fitness < bestFit) {
                    bestFit = fitness;
                    utils.copyGene(newpop[j], bestSol);
                }

                PD = utils.calPD(bestFit, sol);
                progress = "|" + String.join("",Collections.nCopies(k,"="))
                        + String.join("",Collections.nCopies((barlen-k)," "))
                        + "|" + "(gen:" + String.format("%02d", i) + "/" + ngen + ") (pop:" + String.format("%02d", j) + "/" + (npop-1) + ") (PD:" + String.format("%.5f",PD) +")\r";
                System.out.print(progress);

                found = (bestFit <= sol || utils.isSameWithSolution(bestSol, solution));

                if (found) {
                    System.out.println("\nsolution found!");
                    break;
                }
            }

            if (found) { break; }
            utils.copyPop(newpop, population);
        }

        endTime = System.currentTimeMillis();
        System.out.print("\nTime collapsed: ");
        System.out.println(String.format("%.3f", (endTime - startTime) / 1000));
        PD = utils.calPD(bestFit, sol);
        System.out.println("final fitness: " + bestFit);
        System.out.println("final PD: " + String.format("%.5f", PD));
        System.out.println("permutation: ");
        for (j = 0; j < n; j++) {
            System.out.print(bestSol[j] + 1);
            System.out.print(" ");
            if ((j+1) % ncol == 0) { System.out.println(); }
        }
    }
}
