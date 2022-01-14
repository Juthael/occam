package com.tregouet.occam.alg.denotation_sets_gen.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.IContextObject;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.subseq_finder.ISymbolSeq;
import com.tregouet.subseq_finder.impl.SubseqFinder;

public class DenotatingConstructBldr {

	private static List<List<ISymbolSeq>> objSymbolSeqs;
	private static Set<IConstruct> intent;
	private static int[] arrayDimensions;
	private static int[] coords;
	private static Map<ISymbolSeq, Set<ISymbolSeq>> subsqToMaxSubsq;
	
	private DenotatingConstructBldr() {
	}
	
	public static Set<IConstruct> getDenotations(List<IContextObject> extent) {
		init();
		arrayDimensions = new int[extent.size()];
		coords = new int[extent.size()];
		for (int i = 0 ; i < extent.size() ; i++) {
			List<ISymbolSeq> currObjConstructs = extent.get(i).toSymbolSeqs();
			arrayDimensions[i] = currObjConstructs.size();
			objSymbolSeqs.add(currObjConstructs);
		}
		for (List<ISymbolSeq> obj : objSymbolSeqs) {
			for (ISymbolSeq objSymSeq : obj) {
				if (!subsqToMaxSubsq.containsKey(objSymSeq)) {
					subsqToMaxSubsq.put(objSymSeq, new HashSet<ISymbolSeq>());
				}
			}
		}
		setSubsqToMaxSubsq();
		for (Set<ISymbolSeq> maxSubseqs : subsqToMaxSubsq.values()) {
			for (ISymbolSeq maxSubseq : maxSubseqs)
				intent.add(new Construct(maxSubseq.getStringArray()));
		}
		return intent;
	}

	public static Set<IConstruct> getDenotatingConstructs(Set<IContextObject> extent){
		return getDenotations(new ArrayList<IContextObject>(extent));
	}
	
	//for unit test use only
	public static Map<ISymbolSeq, Set<ISymbolSeq>> getSubsqToMaxSubsq(List<IContextObject> extent){
		init();
		arrayDimensions = new int[extent.size()];
		coords = new int[extent.size()];
		for (int i = 0 ; i < extent.size() ; i++) {
			List<ISymbolSeq> currObjConstructs = extent.get(i).toSymbolSeqs();
			arrayDimensions[i] = currObjConstructs.size();
			objSymbolSeqs.add(currObjConstructs);
		}
		for (List<ISymbolSeq> obj : objSymbolSeqs) {
			for (ISymbolSeq objSymSeq : obj) {
				if (!subsqToMaxSubsq.containsKey(objSymSeq)) {
					subsqToMaxSubsq.put(objSymSeq, new HashSet<ISymbolSeq>());
				}
			}
		}
		setSubsqToMaxSubsq();
		return subsqToMaxSubsq;
	}
	
	private static void init() {
		objSymbolSeqs = new ArrayList<List<ISymbolSeq>>();
		intent = new HashSet<IConstruct>();
		arrayDimensions = null;
		coords = null;
		subsqToMaxSubsq = new HashMap<ISymbolSeq, Set<ISymbolSeq>>();
	}
	
	private static boolean nextCoord(){
		for(int i=0;i<coords.length;++i) {
			if (++coords[i] < arrayDimensions[i])
				return true;
			else coords[i] = 0;
	    }
	    return false;
    }
	
	private static Set<ISymbolSeq> removeNonMaxSeqs(Set<ISymbolSeq> seqs){
		List<ISymbolSeq> seqList = new ArrayList<ISymbolSeq>(seqs);
		int idx1 = 0;
		boolean idx1SeqRemoved = false;
		int idx2;
		int comparison;
		while (idx1 < seqList.size()) {
			idx2 = idx1 + 1;
			while (!idx1SeqRemoved && (idx2 < seqList.size())) {
				comparison = seqList.get(idx1).compareTo(seqList.get(idx2));
				if (comparison == ISymbolSeq.SUBSEQ_OF) {
					seqList.remove(idx1);
					idx1SeqRemoved = true;
				}
				else if (comparison == ISymbolSeq.SUPERSEQ_OF) {
					seqList.remove(idx2);
				}
				else idx2++;
			}
			if (idx1SeqRemoved)
				idx1SeqRemoved = false;
			else idx1++;
		}
		return new HashSet<ISymbolSeq>(seqList);
	}
	
	private static void setSubsqToMaxSubsq() {
		coords[0] = -1;
		while (nextCoord()) {
			List<ISymbolSeq> tuple = new ArrayList<ISymbolSeq>();
			for (int i = 0 ; i < coords.length ; i++) {
				tuple.add(objSymbolSeqs.get(i).get(coords[i]));
			}
			Set<ISymbolSeq> tupleMaxSubseqs;
			SubseqFinder subseqFinder = new SubseqFinder(tuple);
			tupleMaxSubseqs = subseqFinder.getMaxCommonSubseqs();
			for (ISymbolSeq tupleElmnt : tuple) {
				subsqToMaxSubsq.get(tupleElmnt).addAll(tupleMaxSubseqs);
			}
		}
		for (ISymbolSeq seq : subsqToMaxSubsq.keySet())
			subsqToMaxSubsq.put(seq, removeNonMaxSeqs(subsqToMaxSubsq.get(seq)));
	}

}
