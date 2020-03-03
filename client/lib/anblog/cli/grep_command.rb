module Anblog
  module CLI
    class GrepCommand
      def initialize(post_api_client)
        @post_api_client = post_api_client
      end

      def name
        'grep'
      end

      def description
        'search for regex in posts'
      end

      def action(args)
        if args.length == 0
          "usage: anblog #{name} <content regex> [path prefix]"
        else
          grep args[0], args.length < 2 ? '.' : args[1]
        end
      end

      private

        def grep(regex, prefix)
          begin
            posts = @post_api_client.get_all_posts(:fields => 'path,content', :content => ".*#{regex}.*", :prefix => prefix)
          rescue ApiError => ae
            return "error: #{ae}"
          end
          posts.map { |p| p.path }.join("\n")
        end
    end
  end
end
