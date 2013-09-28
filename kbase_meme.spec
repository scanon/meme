/* 
	Module KBaseMEME version 1.0
	This module provides a set of methods for work with regulatory motifs. These mathods integrate capabilities of five predictive tools into KBase:
	- MEME is a tool for discovering motifs in a group of related DNA or protein sequences
	- TOMTOM is a tool for comparison of an input DNA motif to the elements of a database of known motifs
	- MAST is a tool for searching biological sequence databases for occurencies of known motifs
	- cMonkey [add description]
	- Inferelator [add description].
	
	Data types summary
	This module works with special data types: Motif and MotifCollection.
	Motif data type represents DNA sequence motif predicted by MEME.
	MotifCollection data type represents results of a single MEME analysis and contains parameters of the search and links to motifs identified.

	Methods summary
	searchMotifsFromSequencesWithMeme - runs MEME for a set of nucleotide sequences and returns collection of sequence motifs
	compareMotifsWithTomtom - runs TOMTOM for two collection of motifs and returns list of hits
	findSitesByMotifCollectionWithMast - runs MAST for collection of motifs vs. sequence database and returns list of hits
	getMotifFromCollection - returns a single motif from collection (are we really need it?)
	
*/

module KbaseMEME
{

	/* Represents a particular sequence from sequence set
		string sequenceId - sequence identifier,  must be unique in SequenceSet
		string sequence - nucleotide sequence 
	*/
	typedef structure{
		string sequenceId;
		string sequence;
	} Sequence;
	
	/* Represents set of sequences
		string sequenceSetId - identifier of sequence set
		list<Sequence> sequences - sequences
	*/
	typedef structure{
		string sequenceSetId;
		list<Sequence> sequences;
	} SequenceSet;
	
	/* Represents a particular site from motif description 
		string kbaseSiteMemeId - KBase identifier of MotifMeme
		string siteSequenceName - identifier of sequence 
		int siteStart - position of site start 
		float sitePvalue - site P-value
		string siteLeftFlank - sequence of left flank
		string siteSequence - sequence of site
		string siteRightFlank - sequence of right flank
	*/
	typedef structure {
		string kbaseMotifMemeId;
		string siteSequenceName;
		int siteStart;
		float sitePvalue;
		string siteLeftFlank;
		string siteSequence;
		string siteRightFlank;
	} SiteMeme;

	/* Represents a particular motif 
		string kbaseMotifMemeId - KBase identifier of MotifMeme
		int motifName - name of motif (often number of motif in collection)
		int motifWidthMeme - width of motif
		int motifSitesMeme - number of sites
		float motifLlrMeme - log likelihood ratio of motif
		float motifEvalueMeme - E-value of motif
		string motifMemeOutput - part of MEME output file with data about this motif
		list<SiteMeme> sites - list of sites
	*/
	typedef structure {
		string kbaseMotifMemeId;
		int motifName;
		int motifWidthMeme;
		int motifSitesMeme;
		float motifLlrMeme;
		float motifEvalueMeme;
		string motifMemeOutput;
		list<SiteMeme> sites;
	} MotifMeme;
	
	/* Represents collection of motifs
		string kbaseMotifCollectionMemeId - KBase identifier of MotifCollectionMeme
		string timestamp - timestamp for creation time of collection
		string versionMeme - version of MEME like "MEME version 4.9.0 (Release date: Wed Oct  3 11:07:26 EST 2012)"
		string inputDatafile - name of input file, DATAFILE field of MEME output
		string alphabetMeme - ALPHABET field of MEME output (like "ACGT")
		string trainingSetMeme - strings from TRAINING SET section, except DATAFILE and ALPHABET fields
		string commandLineMeme - command line of MEME run 
		string modMeme - value of mod parameter of MEME run
		int nmotifsMeme - value of nmotifs parameter of MEME run
		string evtMeme - value of evt parameter of MEME run
		string objectFunctionMeme - value of object function parameter of MEME run
		int minwMeme - value of minw parameter of MEME run
		int maxwMeme - value of maxw parameter of MEME run
		float minicMeme - value of minic parameter of MEME run
		int wgMeme - value of wg parameter of MEME run
		int wsMeme - value of wc parameter of MEME run
		string endgapsMeme - value of endgaps parameter of MEME run
		int minsitesMeme - value of minsites parameter of MEME run
		int maxsitesMeme - value of maxsites parameter of MEME run
		float wnsitesMeme - value of wnsites parameter of MEME run
		int probMeme - value of prob parameter of MEME run
		string spmapMeme - value of spmsp parameter of MEME run
		float spfuzzMeme - value of spfuzz parameter of MEME run
		string substringMeme - value of substring parameter of MEME run
		string branchingMeme - value of branching parameter of MEME run
		string wbranchMeme - value of wbranch parameter of MEME run
		string priorMeme - value of prior parameter of MEME run
		float bMeme - value of b parameter of MEME run
		int maxiterMeme - value of maxiter parameter of MEME run
		float distanceMeme - value of distance parameter of MEME run
		int nMeme - value of n parameter of MEME run
		int nCapMeme - value of N parameter of MEME run
		string strandsMeme - value of strands parameter of MEME run
		int seedMeme - value of seed parameter of MEME run
		int seqfracMeme - value of seqfrac parameter of MEME run
		string letterFreqMeme - letter frequencies in dataset
		string bgFreqMeme - background letter frequencies
		list<Motif> motifs - A list of all motifs in a collection
		string collectionMemeOutput - section of MEME output text file (all before motif data)
	*/
	typedef structure{
		string kbaseMotifCollectionMemeId;
		string timestamp;
		string versionMeme;
		string inputDatafile;
		string alphabetMeme;
		list<string> trainingSetMeme;
		string commandLineMeme;
		string modMeme;
		int nmotifsMeme;
		string evtMeme;
		string objectFunctionMeme;
		int minwMeme;
		int maxwMeme;
		float minicMeme;
		int wgMeme;
		int wsMeme;
		string endgapsMeme;
		int minsitesMeme;
		int maxsitesMeme;
		float wnsitesMeme;
		int probMeme;
		string spmapMeme;
		string spfuzzMeme;
		string substringMeme;
		string branchingMeme;
		string wbranchMeme;
		string priorMeme;
		float bMeme;
		int maxiterMeme;
		float distanceMeme;
		int nMeme;
		int nCapMeme;
		string strandsMeme;
		int seedMeme;
		int seqfracMeme;
		string letterFreqMeme;
		string bgFreqMeme;
		list<MotifMeme> motifs;
		string collectionMemeOutput;
	} MotifCollectionMeme;
	
	/*	Represents a particluar TOMTOM hit
		string queryMotifName - name of query motif
		string targetMotifName - name of target motif
		int optimalOffset - Optimal offset: the offset between the query and the target motif
		float pValue - p-value
		float eValue - E-value
		float qValue - q-value
		int overlap - Overlap: the number of positions of overlap between the two motifs.
		string queryConsensus - Query consensus sequence.
		string targetConsensus - Target consensus sequence.
		string strand - Orientation: Orientation of target motif with respect to query motif.
	*/
	typedef structure{
		string queryMotifName;
		string targetMotifName;
		int optimalOffset;
		float pValue;
		float eValue;
		float qValue;
		int overlap;
		string queryConsensus;
		string targetConsensus;
		string strand;
	} hitTomtom;
	
	/*	Represents a particluar MAST hit
		string sequenceName - name of sequence
		string strand - strand ("+" or "-")
		string motifName - name of motif
		int hitStart - start position of hit
		int hitEnd - end position of hit
		float score - hit score
		float hitPvalue - hit p-value
	*/
	typedef structure{
		string sequenceName;
		string strand;
		string motifName;
		int hitStart;
		int hitEnd;
		float score;
		float hitPvalue;
	} hitMast;
	
	/*
		Returns a collection of motifs generated by MEME with input parameters
		MEME will be run with -dna -text parameters
		SequenceSet sequenceSet - input set of sequences
		string modMeme - distribution of motifs, acceptable values are "oops", "zoops", "anr"
		string nmotifsMeme - maximum number of motifs to find
		string minwMeme - minumum motif width
		string maxwMeme - maximum motif width
		string nsitesMeme - number of sites for each motif
		string minsitesMeme - minimum number of sites for each motif
		string maxsitesMeme - maximum number of sites for each motif
		int palMeme - force palindromes, acceptable values are 0 and 1
		int revcompMeme - allow sites on + or - DNA strands, acceptable values are 0 and 1
	*/
	funcdef searchMotifsFromSequencesWithMeme(SequenceSet sequenceSet, string modMeme, int nmotifsMeme, int minwMeme, int maxwMeme, int nsitesMeme, int minsitesMeme, int maxsitesMeme, int palMeme, int revcompMeme) returns(MotifCollectionMeme motifCollection);

	/*
		Returns a collection of motifs generated by MEME with input parameters
		MEME will be run with -dna -text parameters
		string kbaseSequenceSetId - KBase identifier of sequence set
		string modMeme - distribution of motifs, acceptable values are "oops", "zoops", "anr"
		string nmotifsMeme - maximum number of motifs to find
		string minwMeme - minumum motif width
		string maxwMeme - maximum motif width
		string nsitesMeme - number of sites for each motif
		string minsitesMeme - minimum number of sites for each motif
		string maxsitesMeme - maximum number of sites for each motif
		int palMeme - force palindromes, acceptable values are 0 and 1
		int revcompMeme - allow sites on + or - DNA strands, acceptable values are 0 and 1
	*/
	funcdef searchMotifsFromWorkspaceWithMeme(string kbaseSequenceSetId, string modMeme, int nmotifsMeme, int minwMeme, int maxwMeme, int nsitesMeme, int minsitesMeme, int maxsitesMeme, int palMeme, int revcompMeme) returns(MotifCollectionMeme motifCollection);
	
	/*
		Returns a motif from a collection of motifs
		string kbaseMotifCollectionMemeId - KBase identifier of MotifCollectionMeme
		string motifNumber - number of motif in collection
	
	funcdef getMotifFromCollection(string kbaseMotifCollectionMemeId, string motifNumber) returns(Motif motif);
	*/
	
	/*
		Returns list of TOMTOM hits
		TOMTOM will be run with -text parameter
		MotifCollectionMeme queryMotifCollection - query motifs for TOMTOM run
		MotifCollectionMeme targetMotifCollection - target motifs for TOMTOM run
		float threshTomtom - thresh parameter of TOMTOM run, must be smaller than or equal to 1.0 unless evalueTomtom == 1
		int evalueTomtom - evalue parameter of TOMTOM run (accepable values are "0" and "1")
		string distTomtom - value of dist parameter of TOMTOM run (accepable values are "allr", "ed", "kullback", "pearson", "sandelin")
		int internalTomtom - internal parameter of TOMTOM run (accepable values are "0" and "1")
		int minOverlapTomtom - value of min-overlap parameter of TOMTOM run. In case a query motif is smaller than minOverlapTomtom specified, then the motif's width is used as the minimum overlap for that query.
	*/
	funcdef compareMotifsWithTomtom(MotifCollectionMeme queryMotifCollection, MotifCollectionMeme targetMotifCollection, float threshTomtom, int evalueTomtom, string distTomtom, int internalTomtom, int minOverlapTomtom) returns(list<hitTomtom> hitsTomtom);
	
	/*
		Returns list of MAST hits
		MAST will be run with -hit_list parameter
		MotifCollection queryMotifCollection - query motifs for MAST run
		sequenceSet targetSequenceDatabase - target sequences for MAST run
		float mtMast - value of mt parameter for MAST run
	*/
	funcdef findSitesByMotifCollectionWithMast(MotifCollectionMeme queryMotifCollection, SequenceSet targetSequenceDatabase, float mtMast) returns(list<hitMast> hits);

	/*
		Returns list of MAST hits
		MAST will be run with -hit_list parameter
		MotifCollection queryMotifCollection - query motifs for MAST run
		string kbaseSequenceSetId - identifier of the set of target sequences for MAST run
		float mtMast - value of mt parameter for MAST run
	*/
	funcdef findSitesByMotifCollectionWsWithMast(MotifCollectionMeme queryMotifCollection, string kbaseSequenceSetId, float mtMast) returns(list<hitMast> hits);

};