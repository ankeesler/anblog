module Anblog
  module CLI
    class StatCommand
      def initialize(post_api_client)
        @post_api_client = post_api_client
      end

      def name
        'stat'
      end

      def description
        'print out post metadata'
      end

      def action(args)
        if args.length == 0
          "usage: anblog #{name} <path>"
        else
          stat args[0]
        end
      end

      private

        def stat(path)
          begin
            post = @post_api_client.get_post_by_path(path)
          rescue ApiError => ae
            if ae.code == 404
              return "error: unknown post with path #{path}"
            else
              return "error: #{ae}"
            end
          end
  
          created = Time.at(post.created).strftime "%F %T"
          modified = Time.at(post.modified).strftime "%F %T"
          labels = format_labels post.labels
          %(path:     #{post.path}
  created:  #{created}
  modified: #{modified}
  labels:   #{labels}
  ).gsub(/^\s+/, '')
        end

        def format_labels(labels)
          s = ''
          labels.each do |k, v|
            s << "\n    #{k}: #{v}"
          end unless labels.nil?
          s
        end
    end
  end
end
