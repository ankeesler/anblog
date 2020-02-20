require 'filewatcher'

module Anblog
  module CLI
    class Filewatcher
      def initialize(file, callbacks)
        @file = file
        @callbacks = callbacks
      end

      def start
        @fw = ::Filewatcher.new [@file]
        @thread = Thread.new {
          @fw.watch { |f, e| callback(f, e) }
        }
      end

      def stop
        @fw.stop
        @fw.finalize { |f, e| callback(f, e) }
        @thread.join
      end

      private

      def callback(f, e)
        case e
        when :created
          @callbacks.on_create f
        when :updated
          @callbacks.on_update f
        when :deleted
          @callbacks.on_delete f
        end
      end
    end
  end
end
