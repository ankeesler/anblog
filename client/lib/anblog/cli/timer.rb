require 'time'

module Anblog
  module CLI
    class Timer
      def now
        Time.now.iso8601
      end
    end
  end
end
