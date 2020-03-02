require 'anblog/cli/grep_command'

describe Anblog::CLI::GrepCommand do
  let(:post_api_client) { instance_double(Anblog::PostApi) }
  let(:grep_command) { Anblog::CLI::GrepCommand.new post_api_client  }

  let(:expected_posts) {
    [
      Anblog::Post.new(:path => '.file1'),
      Anblog::Post.new(:path => '.dir1a.file2'),
      Anblog::Post.new(:path => '.dir1a.file2.file3'),
      Anblog::Post.new(:path => '.dir1b.dir2.file3'),
    ]
  }

  context 'one argument is passed' do
    it 'calls the api with the regex argument' do
      expect(post_api_client).to receive(:get_all_posts)
                                 .with(:fields => 'path,content', :content => '.*regex.*', :prefix => '.')
                                 .and_return(expected_posts)
      ac_s = grep_command.action ['regex']
      ex_s = %(.file1
.dir1a.file2
.dir1a.file2.file3
.dir1b.dir2.file3).gsub(/^\s+/, '')
      expect(ac_s).to eq(ex_s)
    end
  end
end
