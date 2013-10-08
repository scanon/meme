use Bio::KBase::meme::memeImpl;

use Bio::KBase::meme::Service;
use Plack::Middleware::CrossOrigin;



my @dispatch;

{
    my $obj = Bio::KBase::meme::memeImpl->new;
    push(@dispatch, 'GeneralTypes' => $obj);
}


my $server = Bio::KBase::meme::Service->new(instance_dispatch => { @dispatch },
				allow_get => 0,
			       );

my $handler = sub { $server->handle_input(@_) };

$handler = Plack::Middleware::CrossOrigin->wrap( $handler, origins => "*", headers => "*");
