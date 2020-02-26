require 'anblog/cli/stat_command'

describe Anblog::CLI::StatCommand do
  let(:post_api_client) { instance_double(Anblog::PostApi) }
  let(:stat_command) { Anblog::CLI::StatCommand.new post_api_client  }

  let(:post) {
    post = Anblog::Post.new
    post.path = '.some.path'
    post.created = 1
    post.modified = 2
    post.labels = { 'foo' => 'bar', 'tuna' => 'fish' }
    post
  }

  context 'path argument is passed' do
    it 'calls the lister with the first argument' do
      expect(post_api_client).to receive(:get_post_by_path)
                                   .with('.some.path', :fields => 'path,created,modified,labels')
                                   .and_return(post)
      s = stat_command.action ['.some.path']
      expect(s).to eq('path:     .some.path
created:  1969-12-31 19:00:01
modified: 1969-12-31 19:00:02
labels:   
  foo: bar
  tuna: fish
')
    end
  end
end
