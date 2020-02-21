module Anblog
  module CLI
    class Opener
      def initialize(post_api_client, editor, timer, filewatcher, tmp_path)
        @post_api_client = post_api_client
        @editor = editor
        @timer = timer
        @filewatcher = filewatcher
        @tmp_path = tmp_path
      end

      def open(path)
        begin
          post = @post_api_client.get_post_by_path(path)
        rescue ApiError => ae
          if ae.code != 404
            "error: #{ae}"
          end
        end

        if post == nil
          now = @timer.now
          post = Post.new :path => path, :created => now, :modified => now
        end

        h = make_file_header post
        file = "#{@tmp_path}/anblog/#{path}"
        FileUtils.mkdir_p File.dirname file
        File.open(file, 'w') do |f|
          f << h
          f << post.content
        end

        @filewatcher.start(file) do |f, e|
          case e
          when :updated
            add_or_update_api(post, file)
          else
            raise "unexpected file event #{e} for file #{f}"
          end
        end
        @editor.edit file
        @filewatcher.stop

        add_or_update_api(post, file)
      end

      private

        def add_or_update_api(post, file)
          content = strip_file_comments File.read(file)
          unless content == nil
            new_post = post.created == post.modified
            now = @timer.now
            post.content = content
            post.modified = now
            if new_post
              @post_api_client.add_post post
            else
              @post_api_client.update_post post.path, post
            end
          end
        end

        def make_file_header(post)
          created = Time.at(post.created).strftime "%F %T"
          modified = Time.at(post.modified).strftime "%F %T"
          %(#
  # path:     #{post.path}
  # created:  #{created}
  # modified: #{modified}
  #
  # lines starting with '#' will be ignored
  # empty messages will not be saved
  #
  ).gsub(/^\s+/, '')
        end

        def strip_file_comments(data)
          content = []
          data.each_line do |line|
            unless line.start_with? '#'
              content << line.rstrip
            end
          end
  
          if content.reject { |c| c.empty? }.length == 0
            nil
          else
            content.join("\n")
          end
        end
    end
  end
end
