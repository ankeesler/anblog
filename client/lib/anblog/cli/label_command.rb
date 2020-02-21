module Anblog
  module CLI
    class LabelCommand
      def initialize(post_api_client)
        @post_api_client = post_api_client
      end

      def name
        'label'
      end

      def description
        'set a label on a post'
      end

      def action(args)
        if args.length < 3
          "usage: anblog #{name} <path> <key> <value>"
        else
          label args[0], args[1], args[2]
        end
      end

      private

        def label(path, key, value)
          begin
            post = @post_api_client.get_post_by_path(path)
          rescue ApiError => ae
            if ae.code == 404
              return "error: unknown post with path #{path}"
            else
              return "error: #{ae}"
            end
          end
  
          post.labels[key] = value
          @post_api_client.update_post(post.path, post)
          ''
        end
    end
  end
end
