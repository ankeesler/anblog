module Anblog
  module CLI
    class RmCommand
      def initialize(post_api_client)
        @post_api_client = post_api_client
      end

      def name
        'rm'
      end

      def description
        'delete a post'
      end

      def action(args)
        if args.length == 0
          "usage: anblog #{name} <path>"
        else
          rm args[0]
        end
      end

      private

        def rm(path)
          begin
            post = @post_api_client.delete_post(path)
          rescue ApiError => ae
            if ae.code == 404
              return "error: unknown post with path #{path}"
            else
              return "error: #{ae}"
            end
          end
          ''
        end
    end
  end
end
