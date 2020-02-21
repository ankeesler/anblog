require 'filewatcher'

module Anblog
  module CLI
    class Filewatcher
      def start(file, &block)
        @fw = ::Filewatcher.new [file]
        @thread = Thread.new {
          @fw.watch block
        }
      end

      def stop
        @fw.stop
        @thread.join
      end
    end
  end
end
