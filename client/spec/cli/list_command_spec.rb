require 'anblog/cli/list_command'
require 'anblog/cli/lister'

describe Anblog::CLI::ListCommand do
  let(:lister) { instance_double(Anblog::CLI::Lister) }
  let(:list_command) { Anblog::CLI::ListCommand.new lister }

  let(:expected_posts) {
    [
      Anblog::Post.new(:path => '.file1'),
      Anblog::Post.new(:path => '.dir1a.file2'),
      Anblog::Post.new(:path => '.dir1a.file2.file3'),
      Anblog::Post.new(:path => '.dir1b.dir2.file3'),
    ]
  }

  context 'no args are passed' do
    it 'calls lister with root path' do
      expect(lister).to receive(:list).with('.').and_return(expected_posts)
      s = list_command.action []
      expect(s).to eq(expected_posts.map{|p| p.path}.join "\n")
    end
  end

  context 'path argument is passed' do
    it 'calls the lister with the first argument' do
      expect(lister).to receive(:list).with('.some.path').and_return(expected_posts)
      s = list_command.action ['.some.path']
      expect(s).to eq(expected_posts.map{|p| p.path}.join "\n")
    end
  end
end
