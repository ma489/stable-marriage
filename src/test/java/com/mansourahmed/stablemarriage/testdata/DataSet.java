package com.mansourahmed.stablemarriage.testdata;

import com.mansourahmed.stablemarriage.domain.Acceptor;
import com.mansourahmed.stablemarriage.domain.Proposer;

import java.util.List;

/**
 * Created by mansour on 05/12/15.
 */
public interface DataSet {

    List<Proposer> getProposers();

    List<Acceptor> getAcceptors();

}