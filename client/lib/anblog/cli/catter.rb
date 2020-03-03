module Anblog
  module CLI
    class Catter
      def initialize(post_api_client)
        @post_api_client = post_api_client
      end

      def cat(path)
        begin
          post = @post_api_client.get_post_by_path(path, :fields => 'content')
        rescue ApiError => ae
          if ae.code == 404
            return "error: unknown post with path #{path}"
          else
            return "error: #{ae}"
          end
        end

        if post == nil
        else
          post.content
        end
      end
    end
  end
end
