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
          status_code = ae.code
        end

        if post == nil
          "error: unknown post for path #{path}"
        else
          post.content
        end
      end
    end
  end
end
