#!/usr/bin/perl

#This is a command line testing script

use strict;
use warnings;

use Test::More tests => 24;
use Test::Cmd;
use JSON;

use Bio::KBase::AuthToken;
use Bio::KBase::AuthUser;
use Bio::KBase::workspace::Client;


my $url = "http://localhost:7108/";
my $bin  = "scripts";

my $ws = "AKtest";
my $user = "aktest";
my $pw = "1475rokegi";


my $auth_user = Bio::KBase::AuthUser->new();
my $token = Bio::KBase::AuthToken->new( user_id => $user, password => $pw);
$auth_user->get( token => $token->token );

if ($token->error_message){
	print $token->error_message."\n\n";
	exit(1);
};

#my $ws_client = Bio::KBase::workspace::Client->new("https://kbase.us/services/ws", "user_id", $user, "password", $pw);

my $sequence_set_ref = "\"AKtest/mod_desulfovibrio\"";
my $meme_pspm_collection_ref = "\"AKtest/kb|memepspmcollection.57\"";
my $meme_pspm_id = "\"kb|memepspm.115\"";
my $meme_run_result_ref = "\"AKtest/kb|memerunresult.187\"";


#1
my $tes = Test::Cmd->new(prog => "$bin/find_motifs_with_meme_from_ws.pl", workdir => '', interpreter => '/kb/runtime/bin/perl');
ok($tes, "creating Test::Cmd object for find_motifs_with_meme_from_ws");
$tes->run(args => "--url=$url --ws=$ws --input=$sequence_set_ref --mod=oops --nmotifs=2 --minw=14 --maxw=28");
ok($? == 0,"Running find_motifs_with_meme_from_ws");
my $tem=$tes->stdout;
print "MemeRunResult ID:\t",$tem,"\n";
ok($tem =~ /memerunresult/, "MEME run successfuly");

#2
$tes = Test::Cmd->new(prog => "$bin/find_motifs_with_meme_job_from_ws.pl", workdir => '', interpreter => '/kb/runtime/bin/perl');
ok($tes, "creating Test::Cmd object for find_motifs_with_meme_job_from_ws");
$tes->run(args => "--url=$url --ws=$ws --input=$sequence_set_ref --mod=oops --nmotifs=2 --minw=14 --maxw=28");
ok($? == 0,"Running find_motifs_with_meme_job_from_ws");
$tem=$tes->stdout;
print "Job ID:\t",$tem,"\n";
ok($tem =~ /[1-9]+/, "MEME run successfuly");


#3
$tes = Test::Cmd->new(prog => "$bin/get_pspm_collection_from_meme_result_from_ws.pl", workdir => '', interpreter => '/kb/runtime/bin/perl');
ok($tes, "creating Test::Cmd object for get_pspm_collection_from_meme_result_from_ws");
$tes->run(args => "--url=$url --ws=$ws --input=$meme_run_result_ref");
ok($? == 0,"Running get_pspm_collection_from_meme_result_from_ws");
$tem=$tes->stdout;
print "MemePSPMCollection ID:\t",$tem,"\n";
ok($tem =~ /memepspmcollection/, "MEME PSPM collection generated successfuly");

#4
$tes = Test::Cmd->new(prog => "$bin/get_pspm_collection_from_meme_result_job_from_ws.pl", workdir => '', interpreter => '/kb/runtime/bin/perl');
ok($tes, "creating Test::Cmd object for get_pspm_collection_from_meme_result_job_from_ws");
$tes->run(args => "--url=$url --ws=$ws --input=$meme_run_result_ref");
ok($? == 0,"Running get_pspm_collection_from_meme_result_job_from_ws");
$tem=$tes->stdout;
print "Job ID:\t",$tem,"\n";
ok($tem =~ /[1-9]+/, "MEME PSPM collection generated successfuly");


#5
$tes = Test::Cmd->new(prog => "$bin/compare_motifs_with_tomtom_by_collection_from_ws.pl", workdir => '', interpreter => '/kb/runtime/bin/perl');
ok($tes, "creating Test::Cmd object for compare_motifs_with_tomtom_by_collection_from_ws");
$tes->run(args => "--url=$url --ws=$ws --query=$meme_pspm_collection_ref --target=$meme_pspm_collection_ref --thresh=0.000001 --evalue --dist=pearson --min_overlap=12");
ok($? == 0,"Running compare_motifs_with_tomtom_by_collection_from_ws");
$tem=$tes->stdout;
print "TomtomRunResult ID:\t",$tem,"\n";
ok($tem =~ /tomtomrunresult/, "TOMTOM run successfuly");

#6
$tes = Test::Cmd->new(prog => "$bin/compare_motifs_with_tomtom_job_by_collection_from_ws.pl", workdir => '', interpreter => '/kb/runtime/bin/perl');
ok($tes, "creating Test::Cmd object for compare_motifs_with_tomtom_job_by_collection_from_ws");
$tes->run(args => "--url=$url --ws=$ws --query=$meme_pspm_collection_ref --target=$meme_pspm_collection_ref --thresh=0.000001 --evalue --dist=pearson --min_overlap=12");
ok($? == 0,"Running compare_motifs_with_tomtom_job_by_collection_from_ws");
$tem=$tes->stdout;
print "Job ID:\t",$tem,"\n";
ok($tem =~ /[1-9]+/, "TOMTOM run successfuly");

#7
$tes = Test::Cmd->new(prog => "$bin/find_sites_with_mast_by_collection_from_ws.pl", workdir => '', interpreter => '/kb/runtime/bin/perl');
ok($tes, "creating Test::Cmd object for find_sites_with_mast_by_collection_from_ws");
$tes->run(args => "--url=$url --ws=$ws --query=$meme_pspm_collection_ref --target=$sequence_set_ref --mt=0.001");
ok($? == 0,"Running find_sites_with_mast_by_collection_from_ws");
$tem=$tes->stdout;
print "MastRunResult ID:\t",$tem,"\n";
ok($tem =~ /mastrunresult/, "MAST run successfuly");

#8
$tes = Test::Cmd->new(prog => "$bin/find_sites_with_mast_job_by_collection_from_ws.pl", workdir => '', interpreter => '/kb/runtime/bin/perl');
ok($tes, "creating Test::Cmd object for find_sites_with_mast_job_by_collection_from_ws");
$tes->run(args => "--url=$url --ws=$ws --query=$meme_pspm_collection_ref --target=$sequence_set_ref --mt=0.001");
ok($? == 0,"Running find_sites_with_mast_job_by_collection_from_ws");
$tem=$tes->stdout;
print "Job ID:\t",$tem,"\n";
ok($tem =~ /[1-9]+/, "MAST run successfuly");

