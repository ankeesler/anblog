module Anblog
  module CLI
    class ListCommand
      def initialize(lister)
        @lister = lister
      end

      def name
        'list'
      end

      def description
        'list post paths (with optional prefix)'
      end

      def action(args)
        if args.length == 0
          path = '.'
        else
          path = args[0]
        end
        @lister.list(path).map { |p| p.path }.join("\n")
      end
    end
  end
end
