Module KBaseMEME version 1.0 (created Aug 2013)

This module provides a set of functions for work with regulatory motifs. These functions integrate capabilities of three predictive tools into KBase:
- MEME is a tool for discovering motifs in a group of related DNA or protein sequences
- TOMTOM is a tool for comparison of an input DNA motif to the elements of a database of known motifs
- MAST is a tool for searching biological sequence databases for occurencies of known motifs

Requires MEME suite 4.8.1 with patches 1-5: http://ebi.edu.au/ftp/software/MEME/index.html
MEME installation script build.meme can be found in bootstrap

MEME suite requires 'csh' package (apt-get install csh)

To install MEME server, run commands:
make
make deploy

Deployment of MEME service requires typecomp (dev-prototype branch) and java type generator (dev branch).
Default installation of MEME server will use cluster service for all functions returning job object ID. To turn off use of cluster service, change 
DEPLOY_CLUSTER = true to DEPLOY_CLUSTER = false in us.kbase.meme.MemeServerConfig source file.


To install MEME service back-end on cluster, run command 'make deploy-cluster'. 
