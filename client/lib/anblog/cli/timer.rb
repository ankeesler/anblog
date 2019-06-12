require 'time'

module Anblog
  module CLI
    class Timer
      def now
        Time.now.to_i
      end
    end
  end
end
