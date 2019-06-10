require 'spec_helper'

describe Anblog::Treeer do
  let(:post_api_client) { instance_double(Anblog::PostApi) }

  let(:treeer) { Anblog::Treeer.new(post_api_client) }

  let(:expected_posts) {
    [
      Anblog::Post.new(:path => '.file1'),
      Anblog::Post.new(:path => '.dir1a.file2'),
      Anblog::Post.new(:path => '.dir1a.file2.file3'),
      Anblog::Post.new(:path => '.dir1b.dir2.file3'),
    ]
  }

  describe 'open' do
    context 'when the root path is passed' do
      it 'returns all paths' do
        expect(post_api_client).to receive(:get_all_posts)
                                     .and_return(expected_posts)

        actual_posts = treeer.tree '.'
        expect(actual_posts).to eq(expected_posts)
      end
    end

    context 'when a sub path is passed' do
      it 'returns all paths under that path' do
        expect(post_api_client).to receive(:get_all_posts)
                                     .with(:prefix => '.dir1a')
                                     .and_return(expected_posts)

        actual_posts = treeer.tree '.dir1a'
        expect(actual_posts).to eq(expected_posts.select { |p| p.path.start_with? '.dir1a' })
      end
    end
  end
end
