module Anblog
  module CLI
    class ListCommand
      def initialize(lister)
        @lister = lister
      end

      def name
        return 'list'
      end

      def description
        return 'list post paths'
      end

      def action(args)
        if args.length == 0
          path = '.'
        else
          path = args[0]
        end
        return @lister.list(path).map{ |p| p.path }.join("\n")
      end
    end
  end
end
