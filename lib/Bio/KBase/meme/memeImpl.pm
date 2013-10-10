package Bio::KBase::meme::memeImpl;
use strict;
use Bio::KBase::Exceptions;
# Use Semantic Versioning (2.0.0-rc.1)
# http://semver.org 
our $VERSION = "0.1.0";

=head1 NAME

GeneralTypes

=head1 DESCRIPTION

Module GeneralTypes provides data types that seem to be not unique for MEME package

dev-prototype branch of typecomp can include this module from a separate file as well
#include <general_types.types>

=cut

#BEGIN_HEADER
#END_HEADER

sub new
{
    my($class, @args) = @_;
    my $self = {
    };
    bless $self, $class;
    #BEGIN_CONSTRUCTOR
    #END_CONSTRUCTOR

    if ($self->can('_init_instance'))
    {
	$self->_init_instance();
    }
    return $self;
}

=head1 METHODS



=head2 version 

  $return = $obj->version()

=over 4

=item Parameter and return types

=begin html

<pre>
$return is a string
</pre>

=end html

=begin text

$return is a string

=end text

=item Description

Return the module version. This is a Semantic Versioning number.

=back

=cut

sub version {
    return $VERSION;
}

=head1 TYPES



=head2 Sequence

=over 4



=item Description

Represents a particular sequence from sequence set
string sequence_id - sequence identifier,  must be unique in SequenceSet
string sequence - nucleotide sequence


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
sequence_id has a value which is a string
sequence has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
sequence_id has a value which is a string
sequence has a value which is a string


=end text

=back



=head2 SequenceSet

=over 4



=item Description

Represents set of sequences
string sequence_set_id - identifier of sequence set
list<Sequence> sequences - sequences


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
sequence_set_id has a value which is a string
sequences has a value which is a reference to a list where each element is a Sequence

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
sequence_set_id has a value which is a string
sequences has a value which is a reference to a list where each element is a Sequence


=end text

=back



=head2 MemeSite

=over 4



=item Description

Represents a particular site from MEME motif description 
string source_sequence_id - ID of sequence where the site was found
int start - position of site start 
float pvalue - site P-value
string left_flank - sequence of left flank
string sequence - sequence of site
string right_flank - sequence of right flank


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
source_sequence_id has a value which is a string
start has a value which is an int
pvalue has a value which is a float
left_flank has a value which is a string
right_flank has a value which is a string
sequence has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
source_sequence_id has a value which is a string
start has a value which is an int
pvalue has a value which is a float
left_flank has a value which is a string
right_flank has a value which is a string
sequence has a value which is a string


=end text

=back



=head2 MemeMotif

=over 4



=item Description

Represents a particular motif found by MEME
string id - KBase ID of MemeMotif
string description - name of motif or number of motif in collection or anything else
int width - width of motif
int sites - number of sites
float llr - log likelihood ratio of motif
float evalue - E-value of motif
string raw_output - part of MEME output file with data about this motif
list<MemeSite> sites - list of sites


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
id has a value which is a string
description has a value which is a string
width has a value which is an int
llr has a value which is a float
evalue has a value which is a float
raw_output has a value which is a string
sites has a value which is a reference to a list where each element is a MemeSite

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
id has a value which is a string
description has a value which is a string
width has a value which is an int
llr has a value which is a float
evalue has a value which is a float
raw_output has a value which is a string
sites has a value which is a reference to a list where each element is a MemeSite


=end text

=back



=head2 MemeRunParameters

=over 4



=item Description

Contains parameters of a MEME run
string mod - distribution of motifs, acceptable values are "oops", "zoops", "anr"
int nmotifs - maximum number of motifs to find
int minw - minumum motif width
int maxw - maximum motif width
int nsites - number of sites for each motif
int minsites - minimum number of sites for each motif
int maxsites - maximum number of sites for each motif
int pal - force palindromes, acceptable values are 0 and 1
int revcomp - allow sites on + or - DNA strands, acceptable values are 0 and 1


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
mod has a value which is a string
nmotifs has a value which is an int
minw has a value which is an int
maxw has a value which is an int
nsites has a value which is an int
minsites has a value which is an int
maxsites has a value which is an int
pal has a value which is an int
revcomp has a value which is an int

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
mod has a value which is a string
nmotifs has a value which is an int
minw has a value which is an int
maxw has a value which is an int
nsites has a value which is an int
minsites has a value which is an int
maxsites has a value which is an int
pal has a value which is an int
revcomp has a value which is an int


=end text

=back



=head2 MemeRunResult

=over 4



=item Description

Represents results of a single MEME run
string id - KBase ID of MemeRunResult
string timestamp - timestamp for creation time of collection
string version - version of MEME like "MEME version 4.9.0 (Release date: Wed Oct  3 11:07:26 EST 2012)"
string input_file_name - name of input file, DATAFILE field of MEME output
string alphabet - ALPHABET field of MEME output (like "ACGT")
string training_set - strings from TRAINING SET section, except DATAFILE and ALPHABET fields
string command_line - command line of MEME run 
string mod - value of mod parameter of MEME run
int nmotifs - value of nmotifs parameter of MEME run
string evt - value of evt parameter of MEME run
string object_function - value of object function parameter of MEME run
int minw - value of minw parameter of MEME run
int maxw - value of maxw parameter of MEME run
float minic - value of minic parameter of MEME run
int wg - value of wg parameter of MEME run
int ws - value of wc parameter of MEME run
string endgaps - value of endgaps parameter of MEME run
int minsites - value of minsites parameter of MEME run
int maxsites - value of maxsites parameter of MEME run
float wnsites - value of wnsites parameter of MEME run
int prob - value of prob parameter of MEME run
string spmap - value of spmsp parameter of MEME run
float spfuzz - value of spfuzz parameter of MEME run
string substring - value of substring parameter of MEME run
string branching - value of branching parameter of MEME run
string wbranch - value of wbranch parameter of MEME run
string prior - value of prior parameter of MEME run
float b - value of b parameter of MEME run
int maxiter - value of maxiter parameter of MEME run
float distance - value of distance parameter of MEME run
int n - value of n parameter of MEME run
int n_cap - value of N parameter of MEME run
string strands - value of strands parameter of MEME run
int seed - value of seed parameter of MEME run
int seqfrac - value of seqfrac parameter of MEME run
string letter_freq - letter frequencies in dataset
string bg_freq - background letter frequencies
list<MemeMotif> motifs - A list of all motifs in a collection
string raw_output - section of MEME output text file (all before motif data)


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
id has a value which is a string
timestamp has a value which is a string
version has a value which is a string
input_file_name has a value which is a string
alphabet has a value which is a string
training_set has a value which is a reference to a list where each element is a string
command_line has a value which is a string
mod has a value which is a string
nmotifs has a value which is an int
evt has a value which is a string
object_function has a value which is a string
minw has a value which is an int
maxw has a value which is an int
minic has a value which is a float
wg has a value which is an int
ws has a value which is an int
endgaps has a value which is a string
minsites has a value which is an int
maxsites has a value which is an int
wnsites has a value which is a float
prob has a value which is an int
spmap has a value which is a string
spfuzz has a value which is a string
substring has a value which is a string
branching has a value which is a string
wbranch has a value which is a string
prior has a value which is a string
b has a value which is a float
maxiter has a value which is an int
distance has a value which is a float
n has a value which is an int
n_cap has a value which is an int
strands has a value which is a string
seed has a value which is an int
seqfrac has a value which is an int
letter_freq has a value which is a string
bg_freq has a value which is a string
motifs has a value which is a reference to a list where each element is a MemeMotif
raw_output has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
id has a value which is a string
timestamp has a value which is a string
version has a value which is a string
input_file_name has a value which is a string
alphabet has a value which is a string
training_set has a value which is a reference to a list where each element is a string
command_line has a value which is a string
mod has a value which is a string
nmotifs has a value which is an int
evt has a value which is a string
object_function has a value which is a string
minw has a value which is an int
maxw has a value which is an int
minic has a value which is a float
wg has a value which is an int
ws has a value which is an int
endgaps has a value which is a string
minsites has a value which is an int
maxsites has a value which is an int
wnsites has a value which is a float
prob has a value which is an int
spmap has a value which is a string
spfuzz has a value which is a string
substring has a value which is a string
branching has a value which is a string
wbranch has a value which is a string
prior has a value which is a string
b has a value which is a float
maxiter has a value which is an int
distance has a value which is a float
n has a value which is an int
n_cap has a value which is an int
strands has a value which is a string
seed has a value which is an int
seqfrac has a value which is an int
letter_freq has a value which is a string
bg_freq has a value which is a string
motifs has a value which is a reference to a list where each element is a MemeMotif
raw_output has a value which is a string


=end text

=back



=head2 MemePSPM

=over 4



=item Description

Represents a position-specific probability matrix fot MEME motif
string id - KBase ID of the matrix object
string source_id - KBase ID of parent object
string source_type - KBase type of parent object
string description - description of motif
string alphabet - ALPHABET field of MEME output ("ACGT" for nucleotide motifs)
int width - width of motif
int nsites - number of sites
float evalue - E-value of motif
list<list<float>> matrix - The letter probability matrix is a table of probabilities where the rows are positions in the motif and the columns are letters in the alphabet. The columns are ordered alphabetically so for DNA the first column is A, the second is C, the third is G and the last is T.


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
id has a value which is a string
source_id has a value which is a string
source_type has a value which is a string
description has a value which is a string
alphabet has a value which is a string
width has a value which is an int
nsites has a value which is an int
evalue has a value which is a float
matrix has a value which is a reference to a list where each element is a reference to a list where each element is a float

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
id has a value which is a string
source_id has a value which is a string
source_type has a value which is a string
description has a value which is a string
alphabet has a value which is a string
width has a value which is an int
nsites has a value which is an int
evalue has a value which is a float
matrix has a value which is a reference to a list where each element is a reference to a list where each element is a float


=end text

=back



=head2 MemePSPMCollection

=over 4



=item Description

Represents collection of MemePSPMs
string id - KBase ID of MotifPSPMMeme
string timestamp - timestamp for creation time of collection
string description - user's description of the collection
string alphabet - ALPHABET field of MEME output ("ACGT" for nucleotide motifs)
list<MemeMotif> pspms - A list of all MemePSPMs in a collection


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
id has a value which is a string
timestamp has a value which is a string
description has a value which is a string
alphabet has a value which is a string
pspms has a value which is a reference to a list where each element is a MemePSPM

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
id has a value which is a string
timestamp has a value which is a string
description has a value which is a string
alphabet has a value which is a string
pspms has a value which is a reference to a list where each element is a MemePSPM


=end text

=back



=head2 TomtomRunParameters

=over 4



=item Description

Contains parameters of a TOMTOM run
float thresh - thresh parameter of TOMTOM run, must be smaller than or equal to 1.0 unless evalueTomtom == 1
int evalue - evalue parameter of TOMTOM run (accepable values are "0" and "1")
string dist - value of dist parameter of TOMTOM run (accepable values are "allr", "ed", "kullback", "pearson", "sandelin")
int internal - internal parameter of TOMTOM run (accepable values are "0" and "1")
int min_overlap - value of min-overlap parameter of TOMTOM run. In case a query motif is smaller than minOverlapTomtom specified, then the motif's width is used as the minimum overlap for that query.


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
thresh has a value which is a float
evalue has a value which is an int
dist has a value which is a string
internal has a value which is an int
min_overlap has a value which is an int

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
thresh has a value which is a float
evalue has a value which is an int
dist has a value which is a string
internal has a value which is an int
min_overlap has a value which is an int


=end text

=back



=head2 TomtomHit

=over 4



=item Description

Represents a particluar TOMTOM hit
string query_pspm_id - id of query MemePSPM
string target_pspm_id - id of target MemePSPM
int optimal_offset - Optimal offset: the offset between the query and the target motif
float pvalue - p-value
float evalue - E-value
float qvalue - q-value
int overlap - Overlap: the number of positions of overlap between the two motifs.
string query_consensus - Query consensus sequence.
string target_consensus - Target consensus sequence.
string strand - Orientation: Orientation of target motif with respect to query motif.


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
query_pspm_id has a value which is a string
target_pspm_id has a value which is a string
optimal_offset has a value which is an int
pvalue has a value which is a float
evalue has a value which is a float
qvalue has a value which is a float
overlap has a value which is an int
query_consensus has a value which is a string
target_consensus has a value which is a string
strand has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
query_pspm_id has a value which is a string
target_pspm_id has a value which is a string
optimal_offset has a value which is an int
pvalue has a value which is a float
evalue has a value which is a float
qvalue has a value which is a float
overlap has a value which is an int
query_consensus has a value which is a string
target_consensus has a value which is a string
strand has a value which is a string


=end text

=back



=head2 TomtomRunResult

=over 4



=item Description

Represents result of a single TOMTOM run
string id - KBase ID of TomtomRunResult
string timestamp - timestamp for creation time of TomtomRunResult
float thresh - thresh parameter of TOMTOM run
int evalue - evalue parameter of TOMTOM run
string dist - dist parameter of TOMTOM run (accepable values are "allr", "ed", "kullback", "pearson", "sandelin")
int internal - internal parameter of TOMTOM run ("0" or "1")
int min_overlap - min-overlap parameter of TOMTOM run
list<TomtomHit> hits - A list of all hits found by TOMTOM


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
id has a value which is a string
timestamp has a value which is a string
thresh has a value which is a float
evalue has a value which is an int
dist has a value which is a string
internal has a value which is an int
min_overlap has a value which is an int
hits has a value which is a reference to a list where each element is a TomtomHit

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
id has a value which is a string
timestamp has a value which is a string
thresh has a value which is a float
evalue has a value which is an int
dist has a value which is a string
internal has a value which is an int
min_overlap has a value which is an int
hits has a value which is a reference to a list where each element is a TomtomHit


=end text

=back



=head2 MastHit

=over 4



=item Description

Represents a particluar MAST hit
string sequence_id - name of sequence
string strand - strand ("+" or "-")
string pspm_id - id of MemePSPM
int hit_start - start position of hit
int hit_end - end position of hit
float score - hit score
float hitPvalue - hit p-value


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
sequence_id has a value which is a string
strand has a value which is a string
pspm_id has a value which is a string
hit_start has a value which is an int
hit_end has a value which is an int
score has a value which is a float
hit_pvalue has a value which is a float

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
sequence_id has a value which is a string
strand has a value which is a string
pspm_id has a value which is a string
hit_start has a value which is an int
hit_end has a value which is an int
score has a value which is a float
hit_pvalue has a value which is a float


=end text

=back



=head2 MastRunResult

=over 4



=item Description

Represents result of a single MAST run
string id - KBase ID of MastRunResult
string timestamp - timestamp for creation time of MastRunResult
float mt - mt parameter of MAST run (p-value threshold)
list<MastHit> hits - A list of all hits found by MAST


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
id has a value which is a string
timestamp has a value which is a string
mt has a value which is a float
hits has a value which is a reference to a list where each element is a MastHit

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
id has a value which is a string
timestamp has a value which is a string
mt has a value which is a float
hits has a value which is a reference to a list where each element is a MastHit


=end text

=back



=cut

1;
