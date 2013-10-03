use strict;
use Data::Dumper;
use Carp;

=head1 NAME

find_sites_by_motif_collection_ws_with_mast - search a sequence database for occurences of known motifs by MAST

=head1 SYNOPSIS

find_sites_by_motif_collection_ws_with_mast [--url=http://kbase.us/services/meme --sequence_set=<sequence set ID> --mt=<mt> ] < motif_collection_meme_object

=head1 DESCRIPTION

Search a set of nucleotide sequences for occurences of known motifs. This program assumes 
exactly one occurrence of each motif per sequence, and each sequence in the database is assigned 
a p-value, based on the product of the p-values of the individual motif occurrences in that sequence. 

=head2 Documentation for underlying call

Returns a list of MAST hits.

=head1 OPTIONS

=over 6

=item B<-u> I<[http://kbase.us/services/meme]> B<--url>=I<[http://kbase.us/services/meme]>
the service url

=item B<-h> B<--help>
print help information

=item B<--version>
print version information

=item B<--sequence_set>
workspace ID of the motif collection

=item B<--mt>
show motif matches with p-value < <mt>

=back

=head1 EXAMPLE

 kbws-get MotifCollectionMeme KBase.MotifCollectionMeme.1380668722433 -w AKtest | meme_search_motifs_from_sequences_with_meme  --url=http://kbase.us/services/meme --sequence_set=KBase.SequenceSet.12345 --mt=0.001 
 meme_search_motifs_from_sequences_with_meme --help
 meme_search_motifs_from_sequences_with_meme --version

=head1 VERSION

1.0

=cut

use Getopt::Long;
use Bio::KBase::KbaseMEME::Client;

my $usage = "Usage: find_sites_by_motif_collection_ws_with_mast [--url=http://kbase.us/services/meme --sequence_set=<sequence set ID> --mt=<mt> ] < motif_collection_meme_object\n";

my $url       = "http://kbase.us/services/meme";
my $sequence_set     = "";
my $mt       = 0;
my $help       = 0;
my $version    = 0;

GetOptions("help"       => \$help,
           "version"    => \$version,
           "sequence_set=s"    => \$sequence_set,
           "mt=f"    => \$mt,
           "url=s"     => \$url) or {
               print $usage;
               exit(1);
           }
           
if($help){
print "NAME\n";
print "find_sites_by_motif_collection_ws_with_mast - search a sequence database for occurences of known motifs by MAST.\n";
print "\n";
print "\n";
print "VERSION\n";
print "1.0\n";
print "\n";
print "SYNOPSIS\n";
print "find_sites_by_motif_collection_ws_with_mast [--url=http://kbase.us/services/meme --sequence_set=<sequence set ID> --mt=<mt> ] < motif_collection_meme_object\n";
print "\n";
print "DESCRIPTION\n";
print "INPUT:            This command requires the URL of the service, sequence set ID, p-value threshold and motif collection object.\n";
print "\n";
print "OUTPUT:           The output of this command is a list of MAST hits.\n";
print "\n";
print "PARAMETERS:\n";
print "--url             The URL of the service, --url=http://kbase.us/services/meme, required.\n";
print "\n";
print "--sequence_set    Workspace ID of the motif collection, required.\n";
print "\n";
print "--mt              Show motif matches with p-value < <mt>, required.\n";
print "\n";
print "--help            Display help message to standard out and exit with error code zero; \n";
print "                  ignore all other command-line arguments.  \n";
print "--version         Print version information. \n";
print "\n";
print " \n";
print "EXAMPLES \n";
print "kbws-get MotifCollectionMeme KBase.MotifCollectionMeme.1380668722433 -w AKtest | meme_search_motifs_from_sequences_with_meme  --url=http://kbase.us/services/meme --sequence_set=KBase.SequenceSet.12345 --mt=0.001\n";
print "\n";
print "This command will return a list of occurences of motifs from the collection in the set of sequences with p-value below 0.001.\n";
print "\n";
print " \n";
print "Report bugs to aekazakov\@lbl.gov\n";
exit(0);
}

if($version)
{
    print "find_sites_by_motif_collection_ws_with_mast\n";
    print "Copyright (C) 2013 DOE Systems Biology Knowledgebase\n";
    print "License GPLv3+: GNU GPL version 3 or later <http://gnu.org/licenses/gpl.html>.\n";
    print "This is free software: you are free to change and redistribute it.\n";
    print "There is NO WARRANTY, to the extent permitted by law.\n";
    print "\n";
    print "Written by Alexey Kazakov\n";
    exit(0);
}

unless (@ARGV == 0){
    print $usage;
    exit(1);
};

my $oc = Bio::KBase::KbaseMEME::Client->new($url);
my @input = <STDIN>;                                                                             
my $motif_collection = join("", @input);
my $results = $oc->findSitesByMotifCollectionWsWithMast($motif_collection, $sequence_set, $mt);

foreach my $hit (@{$results}) {
#TODO insert output code here

    #old code
    #  print $rh->{"id"}."\t".$rh->{'networkType'}."\t".$rh->{"sourceReference"}."\t".$rh->{"name"}."\t".$rh->{"description"}."\n";
}
exit(0);