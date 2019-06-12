require 'anblog/cli/editor'
require 'anblog/cli/opener'

describe Anblog::CLI::Opener do
  let(:post_api_client) { instance_double(Anblog::PostApi) }
  let(:editor) { instance_double(Anblog::CLI::Editor) }
  let(:timer) { class_double(Time) }

  let(:opener) { Anblog::CLI::Opener.new(post_api_client, editor, timer, '/tmp' ) }

  let(:expected_post) {
    expected_post = Anblog::Post.new
    expected_post.path = '.some.path'
    expected_post.content = "some content\n\non multiple lines"
    expected_post.created = 1
    expected_post.modified = 1
    expected_post
  }

  describe 'open' do
    context 'the post does not exist' do
      context 'the user enters uncommented lines' do
        it 'edits the file and creates a post' do
          expect(timer).to receive(:now).and_return(1)
          expect(post_api_client).to receive(:get_post_by_path)
                                      .with('.some.path')
                                      .and_raise(Anblog::ApiError.new(
                                                   :code => 404,
          ))
          expect(post_api_client).to receive(:add_post)
                                      .with(expected_post)
                                      .and_return(expected_post)
          expect(editor).to receive(:edit) do |file|
            expected_content = %(#
# path:     .some.path
# created:  1969-12-31 19:00:01
# modified: 1969-12-31 19:00:01
#
# lines starting with '#' will be ignored
# empty messages will not be saved
#
)
            actual_content = File.read(file)
            expect(expected_content).to eq(actual_content)

            open(file, 'a') do |f|
              f << "some content\n\non multiple lines"
            end
          end

          opener.open '.some.path'
        end
      end
    end

    context 'the post exists' do
      context 'when the user does not update any uncommented lines' do
        it 'edits the file and does not update the post' do
          expect(timer).to receive(:now).and_return(2)
          expect(post_api_client).to receive(:get_post_by_path)
                                      .with('.some.path')
                                      .and_return(expected_post)
          expect(editor).to receive(:edit) do |file|
            expected_content = %(#
# path:     .some.path
# created:  1969-12-31 19:00:01
# modified: 1969-12-31 19:00:01
#
# lines starting with '#' will be ignored
# empty messages will not be saved
#
some content

on multiple lines)
            actual_content = File.read(file)
            expect(expected_content).to eq(actual_content)

            new_content = %(#
# path:     .some.path
# created:  1969-12-31 19:00:01
# modified: 1969-12-31 19:00:01
#
# lines starting with '#' will be ignored
# empty messages will not be saved
#
)
            open(file, 'w') do |f|
              f << new_content
            end
          end

          opener.open '.some.path'
        end
      end

      context 'the user enters uncommented lines' do
        it 'edits the file and updates the post' do
          expected_new_post = Marshal.load(Marshal.dump(expected_post))
          expected_new_post.modified = 2
          expected_new_post.content << "\nsome content\n\non multiple lines"

          expect(timer).to receive(:now).and_return(2)
          expect(post_api_client).to receive(:get_post_by_path)
                                      .with('.some.path')
                                      .and_return(expected_post)
          expect(post_api_client).to receive(:update_post)
                                      .with('.some.path', expected_new_post)
                                      .and_return(expected_new_post)
          expect(editor).to receive(:edit) do |file|
            expected_content = %(#
# path:     .some.path
# created:  1969-12-31 19:00:01
# modified: 1969-12-31 19:00:01
#
# lines starting with '#' will be ignored
# empty messages will not be saved
#
some content

on multiple lines)
            actual_content = File.read(file)
            expect(expected_content).to eq(actual_content)

            open(file, 'a') do |f|
              f << "\nsome content\n\non multiple lines"
            end
          end

          opener.open '.some.path'
        end
      end
    end
  end
end
