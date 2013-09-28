Module KBaseMEME version 1.0 (created Aug 2013)

This module provides a set of methods for work with regulatory motifs. These mathods integrate capabilities of five predictive tools into KBase:
- MEME is a tool for discovering motifs in a group of related DNA or protein sequences
- TOMTOM is a tool for comparison of an input DNA motif to the elements of a database of known motifs
- MAST is a tool for searching biological sequence databases for occurencies of known motifs

Requires MEME suite 4.8.1 with patches 1-5: http://ebi.edu.au/ftp/software/MEME/index.html

Configuration parameters for MEME installation:
./configure --prefix=/kb/runtime/meme --enable-build-libxml2 --enable-build-libxslt --with-mpicc=mpicc --with-mpidir=/usr


