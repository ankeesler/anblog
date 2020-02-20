require 'anblog/cli/catter'

describe Anblog::CLI::Catter do
  let(:post_api_client) { instance_double(Anblog::PostApi) }

  let(:catter) { Anblog::CLI::Catter.new(post_api_client) }

  let(:expected_post) {
    expected_post = Anblog::Post.new
    expected_post.path = '.some.path'
    expected_post.content = "some content\n\non multiple lines\n  with spaces"
    expected_post.created = 1
    expected_post.modified = 1
    expected_post
  }

  describe 'cat' do
    context 'the post does not exist' do
      it 'raises an error' do
        expect(post_api_client).to receive(:get_post_by_path)
                                    .with('.some.path')
                                    .and_raise(Anblog::ApiError.new(
                                                 :code => 404,
        ))

        expect(catter.cat '.some.path').to eq('error: unknown post for path .some.path')
      end
    end

    context 'the post exists' do
      it 'prints the file out with line numbers' do
        expect(post_api_client).to receive(:get_post_by_path)
                                    .with('.some.path')
                                    .and_return(expected_post)
        expect(catter.cat '.some.path').to eq(expected_post.content)
      end
    end
  end
end
