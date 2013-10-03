use strict;
use Data::Dumper;
use Carp;

=head1 NAME

    meme_search_motifs_from_workspace_with_meme - search motifs in a set of sequences stored in workspace using MEME

=head1 SYNOPSIS

    meme_search_motifs_from_workspace_with_meme [--url=http://kbase.us/services/meme --input=<sequence set ID> --mod=<oops|zoops|anr> --nmotifs=<nmotifs> --minw=<minw> --maxw=<maxw> --nsites=<nsites> --minsites=<minsites> --maxsites=<maxsites> --pal --revcomp]

=head1 DESCRIPTION

    Find conserved motifs in a set of nucleotide sequences stored in workspace

=head2 Documentation for underlying call

    Returns a collection of MEME motifs.

=head1 OPTIONS

=over 6

=item B<-u> I<[http://kbase.us/services/meme]> B<--url>=I<[http://kbase.us/services/meme]>
    the service url

=item B<-h> B<--help>
    print help information

=item B<--version>
    print version information

=item B<--input>
    workspace ID of the input sequence set

=item B<--mod>
    distribution of motifs (oops =  One Occurrence Per Sequence, zoops = Zero or One Occurrence Per Sequence, anr = Any Number of Repetitions)

=item B<--nmotifs>
    maximum number of motifs to find

=item B<--minw>
    minumum motif width

=item B<--maxw>
    maximum motif width

=item B<--nsites>
    number of sites for each motif

=item B<--minsites>
    minimum number of sites for each motif

=item B<--maxsites>
    maximum number of sites for each motif

=item B<--pal>
    force palindromes

=item B<--revcomp>
    allow sites on + or - DNA strands. Default: look for DNA motifs only on the strand given in the training set.

=back

=head1 EXAMPLE

    meme_search_motifs_from_workspace_with_meme 
    meme_search_motifs_from_workspace_with_meme --help
    meme_search_motifs_from_workspace_with_meme --version

=head1 VERSION

    1.0

=cut

use Getopt::Long;
use Bio::KBase::KbaseMEME::Client;

my $usage = "Usage: meme_search_motifs_from_workspace_with_meme [--url=http://kbase.us/services/meme --input=<sequence set ID> --mod=<oops|zoops|anr> --nmotifs=<nmotifs> --minw=<minw> --maxw=<maxw> --nsites=<nsites> --minsites=<minsites> --maxsites=<maxsites> --pal --revcomp]\n";

my $url       = "http://kbase.us/services/meme";
my $input     = "";
my $mod       = "oops";
my $nmotifs       = 2;
my $minw       = 14;
my $maxw       = 28;
my $nsites       = 0;
my $minsites       = 0;
my $maxsites       = 0;
my $pal        = 0;
my $revcomp    = 0;
my $help       = 0;
my $version    = 0;

GetOptions("help"       => \$help,
           "version"    => \$version,
           "input=s"    => \$input,
           "mod=s"    => \$mod,
           "nmotifs=i"    => \$nmotifs,
           "minw=i"    => \$minw,
           "maxw=i"    => \$maxw,
           "nsites=i"    => \$nsites,
           "minsites=i"    => \$minsites,
           "maxsites=i"    => \$maxsites,
           "pal"    => \$pal,
           "revcomp"    => \$revcomp,
           "url=s"     => \$url) or {
               print $usage;
               exit(1);
           }
           
           
if($help){
print "NAME\n";
print "meme_search_motifs_from_workspace_with_meme - This command reconstructs motifs with MEME in a set of sequences stored in workspace.\n";
print "\n";
print "\n";
print "VERSION\n";
print "1.0\n";
print "\n";
print "SYNOPSIS\n";
print "meme_search_motifs_from_workspace_with_meme [--url=http://kbase.us/services/meme --input=<sequence set ID> --mod=<oops|zoops|anr> --nmotifs=<nmotifs> --minw=<minw> --maxw=<maxw> --nsites=<nsites> --minsites=<minsites> --maxsites=<maxsites> --pal --revcomp]\n";
print "\n";
print "DESCRIPTION\n";
print "INPUT:            This command requires the URL of the service, ID of a sequence set and parameters.\n";
print "\n";
print "OUTPUT:           The output of this command is a collection of MEME motifs.\n";
print "\n";
print "PARAMETERS:\n";
print "--url             The URL of the service, --url=http://kbase.us/services/meme, required.\n";
print "\n";
print "--input           Workspace ID of the input sequence set, required.\n";
print "\n";
print "--mod             Distribution of motifs (oops =  One Occurrence Per Sequence, zoops = Zero or One Occurrence Per Sequence, anr = Any Number of Repetitions), required.\n";
print "\n";
print "--nmotifs         Maximum number of motifs to find, required.\n";
print "\n";
print "--minw            Minumum motif width, required.\n";
print "\n";
print "--maxw            Maximum motif width, required.\n";
print "\n";
print "--nsites          Number of sites for each motif.\n";
print "\n";
print "--minsites        Minimum number of sites for each motif.\n";
print "\n";
print "--maxsites        Maximum number of sites for each motif.\n";
print "\n";
print "--pal             Force palindromes.\n";
print "\n";
print "--revcomp         Allow sites on + or - DNA strands. Default: look for DNA motifs only on the strand given in the training set.\n";
print "\n";
print "--help            Display help message to standard out and exit with error code zero; \n";
print "                  ignore all other command-line arguments.  \n";
print "--version         Print version information. \n";
print "\n";
print " \n";
print "EXAMPLES \n";
print "meme_search_motifs_from_workspace_with_meme --url=http://kbase.us/services/meme --input=kbase.sequenceset.012345 --mod=oops --nmotifs=2 --minw=14 --maxw=28 \n";
print "\n";
print "This command will return a collection of two motifs with length between 14 and 28 bp.\n";
print "\n";
print "\n";
print "Report bugs to aekazakov\@lbl.gov\n";
exit(0);
}

if($version)
{
    print "meme_search_motifs_from_workspace_with_meme version 1.0\n";
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
my $result = $oc->searchMotifsFromWorkspaceWithMeme($input, $mod, $nmotifs, $minw, $maxw, $nsites, $minsites, $maxsites, $pal, $revcomp);
#TODO insert output code 

#old code
#foreach my $rh (@{$results}) {
#    print $rh->{"id"}."\t".$rh->{'networkType'}."\t".$rh->{"sourceReference"}."\t".$rh->{"name"}."\t".$rh->{"description"}."\n";
#}
exit(0);