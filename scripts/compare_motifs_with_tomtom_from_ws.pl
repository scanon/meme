use strict;
use Data::Dumper;
use Carp;

=head1 NAME

compare_motifs_with_tomtom_from_ws - find motifs that are similar to a given DNA motif by searching against a database of known motifs.

=head1 SYNOPSIS

compare_motifs_with_tomtom_from_ws [--url=http://140.221.84.195:7049/ --ws=<workspace ID> --query=<MemePSPM ID> --target=<MemePSPMCollection ID> --thresh=<threshold> --evalue --dist=<allr|ed|kullback|pearson|sandelin> --internal --min_overlap=<value> --user=<username> --pw=<password>] 

=head1 DESCRIPTION

Find motifs that are similar to a given DNA motif by searching a database of known motifs.

=head2 Documentation for underlying call

Returns KBase ID of TOMTOM run result.

=head1 OPTIONS

=over 6

=item B<--url>=I<[http://140.221.84.195:7049/]>
the service url

=item B<-h> B<--help>
print help information

=item B<--version>
print version information

=item B<--query>
KBase ID of the query PSPM 

=item B<--target>
KBase ID of the target PSPM collection

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

=item B<--user>
    User name for access to workspace

=item B<--pw>
    Password for access to workspace

=back

=head1 EXAMPLE

 compare_motifs_with_tomtom_from_ws --url=http://140.221.84.195:7049/ --ws=AKtest --query="kb|memepspm.1" --target="kb|memepspmcollection.2" --thresh=0.0000001 --evalue --dist=pearson --internal --min_overlap=12
 compare_motifs_with_tomtom_from_ws --help
 compare_motifs_with_tomtom_from_ws --version

=head1 VERSION

1.0

=cut

use Getopt::Long;
use Bio::KBase::meme::Client;
use Bio::KBase::AuthToken;
use Bio::KBase::AuthUser;

my $usage = "Usage: compare_motifs_with_tomtom_from_ws [--url=http://140.221.84.195:7049/ --ws=<workspace ID> --query=<MemePSPM ID> --target=<MemePSPMCollection ID> --thresh=<threshold> --evalue --dist=<allr|ed|kullback|pearson|sandelin> --internal --min_overlap=<value> --user=<username> --pw=<password>]\n";

my $url        = "http://140.221.84.195:7049/";
my $ws		   = "";
my $query      = "";
my $target     = "";
my $thresh     = 0;
my $evalue     =  0;
my $dist       = "";
my $internal   = 0;
my $min_overlap= 0;
my $user       = "";
my $pw         = "";
my $help       = 0;
my $version    = 0;

GetOptions("help"           => \$help,
           "version"        => \$version,
           "ws=s"           => \$ws,
           "query=s"        => \$query,
           "target=s"       => \$target,
           "thresh=f"       => \$thresh,
           "evalue"         => \$evalue,
           "dist=s"         => \$dist,
           "internal"       => \$internal,
           "min_overlap:i"  => \$min_overlap,
           "user=s"         => \$user,
           "pw=s"           => \$pw,
           "url=s"          => \$url) or 
           		exit(1);
           
if($help){
print "NAME\n";
print "compare_motifs_with_tomtom_from_ws - search a sequence database for occurences of known motifs by MAST.\n";
print "\n";
print "\n";
print "VERSION\n";
print "1.0\n";
print "\n";
print "SYNOPSIS\n";
print "compare_motifs_with_tomtom_from_ws [--url=http://140.221.84.195:7049/ --ws=<workspace ID> --query=<MemePSPM ID> --target=<MemePSPMCollection ID> --thresh=<threshold> --evalue --dist=<allr|ed|kullback|pearson|sandelin> --internal --min_overlap=<value> --user=<username> --pw=<password>] \n";
print "\n";
print "DESCRIPTION\n";
print "INPUT:            This command requires the URL of the service, IDs of two PSPM collections and parameters.\n";
print "\n";
print "OUTPUT:           The output of this command is an ID of TomtomRunResult.\n";
print "\n";
print "PARAMETERS:\n";
print "--url             The URL of the service, --url=http://140.221.84.195:7049/, required.\n";
print "\n";
print "--ws              Workspace ID, required.\n";
print "\n";
print "--query           KBase ID of the query PSPM collection, required.\n";
print "\n";
print "--target          KBase ID of the target PSPM collection, required.\n";
print "\n";
print "--thresh          Only report matches with significance values ≤ thresh parameter value. \n";
print "\n";
print "--evalue          Use the E-value of the match as the significance threshold.\n";
print "\n";
print "--dist            Motif distance calculation algorithm (accepable values are \"allr\", \"ed\", \"kullback\", \"pearson\", \"sandelin\").\n";
print "\n";
print "--internal        This parameter forces the shorter motif to be completely contained in the longer motif.\n";
print "\n";
print "--min_overlap     Only report motif matches that overlap by min overlap positions or more.\n";
print "\n";
print "--user            User name for access to workspace.\n";
print "\n";
print "--pw              Password for access to workspace.\n";
print "\n";
print "--help            Display help message to standard out and exit with error code zero; \n";
print "                  ignore all other command-line arguments.  \n";
print "--version         Print version information. \n";
print "\n";
print " \n";
print "EXAMPLES \n";
print "compare_motifs_with_tomtom_from_ws --url=http://140.221.84.195:7049/ --ws=AKtest --query=\"kb|memepspm.1\" --target=\"kb|memepspmcollection.2\" --thresh=0.0000001 --evalue --dist=pearson --internal --min_overlap=12\n";
print "\n";
print "This command for each query motif will return an ID of list of TOMTOM hits.\n";
print "\n";
print " \n";
print "Report bugs to aekazakov\@lbl.gov\n";
exit(0);
}

if($version)
{
    print "compare_motifs_with_tomtom_from_ws\n";
    print "Copyright (C) 2013 DOE Systems Biology Knowledgebase\n";
    print "License GPLv3+: GNU GPL version 3 or later <http://gnu.org/licenses/gpl.html>.\n";
    print "This is free software: you are free to change and redistribute it.\n";
    print "There is NO WARRANTY, to the extent permitted by law.\n";
    print "\n";
	print "Report bugs to aekazakov\@lbl.gov\n";
    exit(0);
}

unless (@ARGV == 0){
    print $usage;
    exit(1);
};

my $auth_user = Bio::KBase::AuthUser->new();
my $token = Bio::KBase::AuthToken->new( user_id => $user, password => $pw);
$auth_user->get( token => $token->token );

if ($token->error_message){
	print $token->error_message."\n\n";
	exit(1);
};

my $tomtom_run_parameters = {
	"thresh"=>$thresh,
    "evalue"=>$evalue,
    "dist"=>$dist,
    "internal"=>$internal,
    "min_overlap"=>$min_overlap
};

my $obj = {
	method => "MEME.compare_motifs_with_tomtom_from_ws",
	params => [$ws, $query, $target, $tomtom_run_parameters],
};

my $client = Bio::KBase::meme::Client::RpcClient->new;
$client->{token} = $token->token;

my $result = $client->call($url, $obj);

my @keys = keys % { $result };


if (${$result}{is_success} == 1){
	my $result_id = ${$result}{jsontext};
	$result_id =~ s/\"\]\}$//;
	$result_id =~ s/^.*\"\,\"result\"\:\[\"//;
	print $result_id."\n\n";
	exit(0);
}
else {
	print ${$result}{jsontext}."\n";
	exit(1);
}
exit(1);

