module Anblog
  module CLI
    class OpenCommand
      def initialize(opener)
        @opener = opener
      end

      def name
        'open'
      end

      def description
        'open and edit a post'
      end

      def action(args)
        if args.length == 0
          "usage: anblog #{name} <path>"
        else
          @opener.open args[0]
          ''
        end
      end
    end
  end
end
