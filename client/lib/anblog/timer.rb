require 'time'

module Anblog
  class Timer
    def now
      Time.now.iso8601
    end
  end
end
