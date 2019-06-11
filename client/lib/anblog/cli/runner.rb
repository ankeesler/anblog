module Anblog
  module CLI
    class Runner
      def initialize(commands)
        @commands = commands
      end

      def run(args)
        if args.length == 0
          return usage
        end

        name = args[0]

        command = @commands.detect { |c| c.name == name }
        if command == nil
          return "unknown command: #{name}"
        end

        return command.action args[1..args.length-1]
      end

      def usage
        s = "anblog cli\n\n"
        @commands.each do |c|
          s << "usage: anblog #{c.name}\n"
          s << "  #{c.description}\n"
        end
        return s
      end

    end
  end
end
