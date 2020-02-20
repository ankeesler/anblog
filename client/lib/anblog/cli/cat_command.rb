module Anblog
  module CLI
    class CatCommand
      def initialize(catter)
        @catter = catter
      end

      def name
        "cat"
      end

      def description
        "print out a post's content"
      end

      def action(args)
        if args.length == 0
          "usage: anblog #{name} <path>"
        else
          @catter.cat args[0]
        end
      end
    end
  end
end
