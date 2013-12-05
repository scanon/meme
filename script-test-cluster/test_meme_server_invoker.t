#!/usr/bin/perl

# Script for testing back-end of meme service

use strict;
use warnings;

use Bio::KBase::AuthToken;
use Bio::KBase::AuthUser;
use Bio::KBase::userandjobstate::Client;

my $user = "aktest";
my $pw = "1475rokegi";

my $auth_user = Bio::KBase::AuthUser->new();
my $token = Bio::KBase::AuthToken->new( user_id => $user, password => $pw);
$auth_user->get( token => $token->token );

if ($token->error_message){
	print $token->error_message."\n\n";
	exit(1);
};

my $auth_token = $token->token;

my $job_client = Bio::KBase::userandjobstate::Client->new("http://140.221.84.180:7083", "user_id", $user, "password", $pw);

my $deployment_dir = "/kb/deployment/meme/";

my $command_line = "java -jar ".$deployment_dir."meme_cluster.jar";

my $ws = "AKtest";
my $sequence_set_id = "KBase.SequenceSet.12345";
my $meme_run_result_id = "\"kb|memerunresult.11\"";
my $meme_pspm_collection_id = "\"kb|memepspmcollection.2\"";
my $meme_pspm_id = "\"kb|memepspm.14\"";

my $test_command = "";


#1 help
$test_command = $command_line." --help";
print $test_command."\n\n";
system ($test_command);

#2 find_motifs_with_meme_job_from_ws
my $job = $job_client->create_job();
$test_command = $command_line." --method find_motifs_with_meme_job_from_ws --job $job --ws $ws --query $sequence_set_id --mod oops --nmotifs 2 --minw 14 --maxw 28 --pal 1 --token \"$auth_token\"";
print $test_command."\n\n";
system ($test_command);

#3 get_pspm_collection_from_meme_result_from_ws
$job = $job_client->create_job();
$test_command = $command_line." --method get_pspm_collection_from_meme_result_job_from_ws --job $job --ws $ws --query $meme_run_result_id --token \"$auth_token\"";
print $test_command."\n\n";
system ($test_command);

#4 compare_motifs_with_tomtom_by_collection_from_ws
$job = $job_client->create_job();
$test_command = $command_line." --method compare_motifs_with_tomtom_job_by_collection_from_ws --job $job --ws $ws --query $meme_pspm_collection_id --target $meme_pspm_collection_id --thresh 0.000001 --evalue 1 --dist pearson --min_overlap 12 --internal 1 --token \"$auth_token\"";
print $test_command."\n\n";
system ($test_command);

#5 compare_motifs_with_tomtom_from_ws
$job = $job_client->create_job();
$test_command = $command_line." --method compare_motifs_with_tomtom_job_from_ws --job $job --ws $ws --query $meme_pspm_id --target $meme_pspm_collection_id --thresh 0.000001 --evalue 1 --dist pearson --min_overlap 12 --internal 1 --token \"$auth_token\"";
print $test_command."\n\n";
system ($test_command);

#6 find_sites_with_mast_from_ws
$job = $job_client->create_job();
$test_command = $command_line." --method find_sites_with_mast_job_from_ws --job $job --ws $ws --query $meme_pspm_id --target $sequence_set_id --thresh 0.001 --token \"$auth_token\"";
print $test_command."\n\n";
system ($test_command);

#7 find_sites_with_mast_by_collection_from_ws
$job = $job_client->create_job();
$test_command = $command_line." --method find_sites_with_mast_job_by_collection_from_ws --job $job --ws $ws --query $meme_pspm_collection_id --target $sequence_set_id --thresh 0.001 --token \"$auth_token\"";
print $test_command."\n\n";
system ($test_command);

exit(0);
