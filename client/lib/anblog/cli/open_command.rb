module Anblog
  module CLI
    class OpenCommand
      def initialize(opener)
        @opener = opener
      end

      def name
        return 'open'
      end

      def description
        return 'open and edit a post'
      end

      def action(args)
        if args.length == 0
          return "usage: anblog #{name} <path>"
        else
          @opener.open args[0]
          return ''
        end
      end
    end
  end
end
