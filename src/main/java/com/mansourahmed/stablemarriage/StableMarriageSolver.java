package com.mansourahmed.stablemarriage;

import com.mansourahmed.stablemarriage.domain.Acceptor;
import com.mansourahmed.stablemarriage.domain.Proposer;

import java.util.*;

/**
 * Solve using Gale-Shapley algorithm (assumes equal number of proposers and acceptors)
 * aka Deferred-acceptance algorithm
 * https://en.wikipedia.org/wiki/Stable_marriage_problem
 */
public class StableMarriageSolver {

    public Map<Proposer, Acceptor> solve(List<Proposer> proposers, List<Acceptor> acceptors) {
        List<Proposer> proposersWhoAreFreeAndHaveAcceptorsTheyHaveYetToProposeTo = new ArrayList<>(proposers);
        Map<Proposer, List<Acceptor>> acceptorsToWhomTheseProposersHaveNotAlreadyProposed = new HashMap<>();
        proposers.forEach(p -> acceptorsToWhomTheseProposersHaveNotAlreadyProposed.put(p, acceptors));
        return galeShapley(proposersWhoAreFreeAndHaveAcceptorsTheyHaveYetToProposeTo,
                            acceptorsToWhomTheseProposersHaveNotAlreadyProposed);
    }

    private static Map<Proposer, Acceptor> galeShapley(List<Proposer> proposersWhoAreFreeAndHaveAcceptorsTheyHaveYetToProposeTo,
                                    Map<Proposer, List<Acceptor>> acceptorsToWhomTheseProposersHaveNotAlreadyProposed) {
        Map<Proposer, Acceptor> matchings = new HashMap<>();
        while (!proposersWhoAreFreeAndHaveAcceptorsTheyHaveYetToProposeTo.isEmpty()) {
            Proposer proposer = proposersWhoAreFreeAndHaveAcceptorsTheyHaveYetToProposeTo.remove(0);
            List<Acceptor> pendingProposal = acceptorsToWhomTheseProposersHaveNotAlreadyProposed.get(proposer);
            Acceptor acceptor = getHighestRankingPendingWoman(pendingProposal, proposer.getPreferences());
            if (!acceptor.isEngaged()) {
                engage(matchings, proposer, acceptor);
            } else { //some pair (otherProposer, acceptor) already exists
                Proposer otherProposer = getTentativeProposer(matchings, acceptor);
                if (prefers(acceptor, proposer, otherProposer)) {//if acceptor prefers proposer to otherProposer
                    jilt(proposersWhoAreFreeAndHaveAcceptorsTheyHaveYetToProposeTo,
                            acceptorsToWhomTheseProposersHaveNotAlreadyProposed,
                            matchings, proposer, acceptor, otherProposer);
                }
                //else (otherProposer, acceptor) remain engaged
            }
            acceptorsToWhomTheseProposersHaveNotAlreadyProposed.get(proposer).remove(acceptor);
        }
        return matchings;
    }

    private static void jilt(List<Proposer> proposersWhoAreFreeAndHaveAcceptorsTheyHaveYetToProposeTo,
                             Map<Proposer, List<Acceptor>> acceptorsToWhomTheseProposersHaveNotAlreadyProposed,
                             Map<Proposer, Acceptor> matchings, Proposer proposer, Acceptor acceptor, Proposer otherProposer) {
        matchings.put(proposer, acceptor);
        proposer.setEngaged(true);
        matchings.remove(otherProposer);
        otherProposer.setEngaged(false);
        proposersWhoAreFreeAndHaveAcceptorsTheyHaveYetToProposeTo.add(otherProposer);
        acceptorsToWhomTheseProposersHaveNotAlreadyProposed.get(otherProposer).remove(acceptor);
    }

    private static void engage(Map<Proposer, Acceptor> matchings, Proposer proposer, Acceptor acceptor) {
        matchings.put(proposer, acceptor);
        acceptor.setEngaged(true);
        proposer.setEngaged(true);
    }

    private static Proposer getTentativeProposer(Map<Proposer, Acceptor> matchings, Acceptor woman) {
        for (Map.Entry<Proposer, Acceptor> matching : matchings.entrySet()) {
            if (woman.equals(matching.getValue())) {
                return matching.getKey();
            }
        }
        return null;
    }

    private static Acceptor getHighestRankingPendingWoman(List<Acceptor> pendingProposal, List<Integer> preferences) {
        List<Acceptor> listCopy = new ArrayList<>(pendingProposal);
        Comparator<Acceptor> comparator = (o1, o2) -> {
            if (o1.equals(o2)) {
                return 0;
            } else if (preferences.indexOf(o1.getId()) < preferences.indexOf(o2.getId())) {
                return -1;
            } else {
                return 1;
            }
        };
        Collections.sort(listCopy, comparator);
        return listCopy.get(0);
    }

    private static boolean prefers(Acceptor acceptor, Proposer proposer, Proposer otherProposer) {
        List<Integer> preferences = acceptor.getPreferences();
        return preferences.indexOf(proposer.getId()) < preferences.indexOf(otherProposer.getId());
    }

}