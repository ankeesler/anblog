require 'anblog/cli/lister'

describe Anblog::CLI::Lister do
  let(:post_api_client) { instance_double(Anblog::PostApi) }

  let(:lister) { Anblog::CLI::Lister.new(post_api_client) }

  let(:expected_posts) {
    [
      Anblog::Post.new(:path => '.file1'),
      Anblog::Post.new(:path => '.dir1a.file2'),
      Anblog::Post.new(:path => '.dir1a.file2.file3'),
      Anblog::Post.new(:path => '.dir1b.dir2.file3'),
    ]
  }

  describe 'list' do
    context 'when the root path is passed' do
      it 'returns all paths' do
        expect(post_api_client).to receive(:get_all_posts)
                                     .with(:prefix => '.', :fields => 'path')
                                     .and_return(expected_posts)

        actual_posts = lister.list '.'
        expect(actual_posts).to eq(expected_posts)
      end
    end

    context 'when a sub path is passed' do
      it 'returns all paths under that path' do
        expected_filtered_posts = expected_posts.select { |p| p.path.start_with? '.dir1a' }
        expect(post_api_client).to receive(:get_all_posts)
                                     .with(:prefix => '.dir1a', :fields => 'path')
                                     .and_return(expected_filtered_posts)

        actual_posts = lister.list '.dir1a'
        expect(actual_posts).to eq(expected_filtered_posts)
      end
    end
  end
end
