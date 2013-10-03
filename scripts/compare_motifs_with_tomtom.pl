use strict;
use Data::Dumper;
use Carp;

=head1 NAME

compare_motifs_with_tomtom - find motifs that are similar to a given DNA motif by searching a database of known motifs.

=head1 SYNOPSIS

#TODO compare_motifs_with_tomtom [--url=http://kbase.us/services/meme --thresh=<threshold> --evalue --dist=<allr|ed|kullback|pearson|sandelin> --internal --min_overlap ] < motif_collection_meme_object

=head1 DESCRIPTION

Find motifs that are similar to a given DNA motif by searching a database of known motifs.

=head2 Documentation for underlying call

Returns a list of TOMTOM hits.

=head1 OPTIONS

=over 6

=item B<-u> I<[http://kbase.us/services/meme]> B<--url>=I<[http://kbase.us/services/meme]>
the service url

=item B<-h> B<--help>
print help information

=item B<--version>
print version information

#TODO =item B<--sequence_set>
#TODO workspace ID of the motif collection

=item B<--thresh>
Only report matches with significance values ≤ thresh parameter value. 

=item B<--evalue>
Use the E-value of the match as the significance threshold.

=item B<--dist>
Motif distance calculation algorithm (accepable values are "allr", "ed", "kullback", "pearson", "sandelin").

=item B<--internal>
This parameter forces the shorter motif to be completely contained in the longer motif.

=item B<--min_overlap>
Only report motif matches that overlap by min overlap positions or more.

=back

=head1 EXAMPLE

#TODO  kbws-get MotifCollectionMeme KBase.MotifCollectionMeme.1380668722433 -w AKtest | meme_search_motifs_from_sequences_with_meme  --url=http://kbase.us/services/meme --sequence_set=KBase.SequenceSet.12345 --mt=0.001 
 meme_search_motifs_from_sequences_with_meme --help
 meme_search_motifs_from_sequences_with_meme --version

=head1 VERSION

1.0

=cut

use Getopt::Long;
use Bio::KBase::KbaseMEME::Client;
#TODO the following code has not been completed yet
my $usage = "Usage: find_sites_by_motif_collection_ws_with_mast [--url=http://kbase.us/services/meme --sequence_set=<sequence set ID> --mt=<mt> ] < motif_collection_meme_object\n";

my $url       = "http://kbase.us/services/meme";
my $sequence_set     = "";
my $thresh       = 0;
my $evalue     =  0;
my $dist         = "";
my $internal     = 0;
my $min_overlap     = 0;
my $help       = 0;
my $version    = 0;

GetOptions("help"       => \$help,
           "version"    => \$version,
           "thresh=f"    => \$thresh,
           "evalue"    => \$evalue,
           "dist=s"    => \$dist,
           "internal"    => \$internal,
           "min_overlap"    => \$min_overlap,
           "url=s"     => \$url) or {
               print $usage;
               exit(1);
           }
           
if($help){
print "NAME\n";
#TODO print "find_sites_by_motif_collection_ws_with_mast - search a sequence database for occurences of known motifs by MAST.\n";
print "\n";
print "\n";
print "VERSION\n";
print "1.0\n";
print "\n";
print "SYNOPSIS\n";
#TODO print "find_sites_by_motif_collection_ws_with_mast [--url=http://kbase.us/services/meme --sequence_set=<sequence set ID> --mt=<mt> ] < motif_collection_meme_object\n";
print "\n";
print "DESCRIPTION\n";
print "INPUT:            This command requires the URL of the service, two motif collection objects and parameters.\n";
print "\n";
print "OUTPUT:           The output of this command is a list of TOMTOM hits.\n";
print "\n";
print "PARAMETERS:\n";
print "--url             The URL of the service, --url=http://kbase.us/services/meme, required.\n";
print "\n";
print "--thresh          Only report matches with significance values ≤ thresh parameter value. \n";
print "\n";
print "--evalue          Use the E-value of the match as the significance threshold.\n";
print "\n";
print "--dist            Motif distance calculation algorithm (accepable values are "allr", "ed", "kullback", "pearson", "sandelin").\n";
print "\n";
print "--internal        This parameter forces the shorter motif to be completely contained in the longer motif.\n";
print "\n";
print "--min_overlap     Only report motif matches that overlap by min overlap positions or more.\n";
print "\n";
print "--help            Display help message to standard out and exit with error code zero; \n";
print "                  ignore all other command-line arguments.  \n";
print "--version         Print version information. \n";
print "\n";
print " \n";
print "EXAMPLES \n";
#TODO print "kbws-get MotifCollectionMeme KBase.MotifCollectionMeme.1380668722433 -w AKtest | meme_search_motifs_from_sequences_with_meme  --url=http://kbase.us/services/meme --sequence_set=KBase.SequenceSet.12345 --mt=0.001\n";
print "\n";
print "This command for each query motif will return a list of target motifs, ranked by p-value.\n";
print "\n";
print " \n";
print "Report bugs to aekazakov\@lbl.gov\n";
exit(0);
}

if($version)
{
    print "compare_motifs_with_tomtom\n";
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
my $results = $oc->searchMotifsFromWorkspaceWithMeme(#TODO $motif_collection, $sequence_set, $mt);

foreach my $hit (@{$results}) {
#TODO insert output code here

    #old code
    #  print $rh->{"id"}."\t".$rh->{'networkType'}."\t".$rh->{"sourceReference"}."\t".$rh->{"name"}."\t".$rh->{"description"}."\n";
}
exit(0);