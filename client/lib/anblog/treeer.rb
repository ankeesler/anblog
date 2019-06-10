module Anblog
  class Treeer
    def initialize(post_api_client)
      @post_api_client = post_api_client
    end

    def tree(path)
      @post_api_client.get_all_posts(:prefix => path)
    end

  end
end
