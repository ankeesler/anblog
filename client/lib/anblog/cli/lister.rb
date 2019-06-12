module Anblog
  module CLI
    class Lister
      def initialize(post_api_client)
        @post_api_client = post_api_client
      end

      def list(path)
        @post_api_client.get_all_posts(:prefix => path)
      end
    end
  end
end
