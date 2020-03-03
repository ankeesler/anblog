require 'anblog/cli/grep_command'

describe Anblog::CLI::GrepCommand do
  let(:post_api_client) { instance_double(Anblog::PostApi) }
  let(:grep_command) { Anblog::CLI::GrepCommand.new post_api_client  }

  let(:expected_posts) {
    [
      Anblog::Post.new(:path => ".file1", :content => "some-content\nfile1\n"),
      Anblog::Post.new(:path => ".dir1a.file2", :content => "some-content\nfile2\n"),
      Anblog::Post.new(:path => ".dir1a.file2.file3", :content => "some-content\nfile2\nfile3\n"),
      Anblog::Post.new(:path => ".dir1b.dir2.file3", :content => "some-content\nfile2\nfile3"),
    ]
  }

  context 'one argument is passed' do
    it 'calls the api with the regex argument' do
      expect(post_api_client).to receive(:get_all_posts)
                                 .with(:fields => 'path,content', :content => '.*file.*', :prefix => '.')
                                 .and_return(expected_posts)
      ac_s = grep_command.action ['file']
      ex_s = %(.file1: file1
.dir1a.file2: file2
.dir1a.file2.file3: file2
.dir1a.file2.file3: file3
.dir1b.dir2.file3: file2
.dir1b.dir2.file3: file3
).gsub(/^\s+/, '')
      expect(ac_s).to eq(ex_s)
    end
  end

  context 'two arguments are passed' do
    it 'calls the api with the regex argument and the prefix argument' do
      expect(post_api_client).to receive(:get_all_posts)
                                 .with(:fields => 'path,content', :content => '.*some[\-]con.ent.*', :prefix => '.dir1a')
                                 .and_return(expected_posts[1..2])
      ac_s = grep_command.action ['some[\-]con.ent', '.dir1a']
      ex_s = %(.dir1a.file2: some-content
.dir1a.file2.file3: some-content
).gsub(/^\s+/, '')
      expect(ac_s).to eq(ex_s)
    end
  end
end
