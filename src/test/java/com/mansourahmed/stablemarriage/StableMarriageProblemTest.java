package com.mansourahmed.stablemarriage;

import com.mansourahmed.stablemarriage.testdata.DataSet;
import com.mansourahmed.stablemarriage.testdata.RandomDataSet;
import com.mansourahmed.stablemarriage.testdata.WikipediaDataSet;
import com.mansourahmed.stablemarriage.domain.Acceptor;
import com.mansourahmed.stablemarriage.domain.Proposer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;
import static org.fest.assertions.Assertions.assertThat;

public class StableMarriageProblemTest {

    private StableMarriageSolver stableMarriageSolver;

    @Before
    public void setup() {
        stableMarriageSolver = new StableMarriageSolver();
    }

    @Test
    public void testOnWikipediaDataSet() {
        System.out.println("----Testing Wikipedia data set----");
        DataSet dataset = new WikipediaDataSet();
        List<Proposer> proposers = dataset.getProposers();
        List<Acceptor> acceptors = dataset.getAcceptors();
        printPreferences(proposers, acceptors);
        Map<Proposer, Acceptor> matchings = stableMarriageSolver.solve(proposers, acceptors);
        assertThat(extractIDs(matchings)).containsOnly(newArrayList(3,4), newArrayList(1,5), newArrayList(2,6));
        printSolution(matchings);
    }

    @Test
    public void testOnRandomDataSet() {
        System.out.println("----Testing Random data set----");
        DataSet dataset = new RandomDataSet();
        List<Proposer> proposers = dataset.getProposers();
        List<Acceptor> acceptors = dataset.getAcceptors();
        printPreferences(proposers, acceptors);
        printSolution(stableMarriageSolver.solve(proposers, acceptors));
    }

    private List<ArrayList<Integer>> extractIDs(Map<Proposer, Acceptor> matchings) {
        return matchings.entrySet().stream().map(e -> newArrayList(e.getKey().getId(), e.getValue().getId())).collect(toList());
    }

    private static void printPreferences(List<Proposer> proposers, List<Acceptor> acceptors) {
        System.out.println("Proposer preferences: ");
        proposers.forEach(p -> System.out.println(p.getId() + " : " + p.getPreferences()));
        System.out.println("Acceptor preferences: ");
        acceptors.forEach(a -> System.out.println(a.getId() + " : " + a.getPreferences()));
    }

    private static void printSolution(Map<Proposer, Acceptor> matchings) {
        System.out.println("Done. Solution: ");
        for (Map.Entry<Proposer, Acceptor> matching : matchings.entrySet()) {
            Proposer proposer = matching.getKey();
            Acceptor acceptor = matching.getValue();
            String match = "Proposer: " + proposer.getId() + " - Acceptor: " + acceptor.getId();
            System.out.println(match);
            int propPref = proposer.getPreferences().indexOf(acceptor.getId()) + 1;
            int accPref = acceptor.getPreferences().indexOf(proposer.getId()) + 1;
            String pref = "This was proposer's " + propPref + "th choice, and acceptor's " + accPref + "th choice";
            System.out.println(pref);
        }
    }

}