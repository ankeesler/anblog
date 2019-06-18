require 'anblog'

# TODO: but why!
require 'anblog/cli/editor'
require 'anblog/cli/lister'
require 'anblog/cli/list_command'
require 'anblog/cli/opener'
require 'anblog/cli/open_command'
require 'anblog/cli/runner'
require 'anblog/cli/timer'

module Anblog
  module CLI
    class App
      def run(args)
        debug = ENV['ANBLOG_DEBUG'] != nil

        begin
          really_run(args, debug)
        rescue => e
          puts "error: #{e}"
          if debug
            puts "\nBACKTRACE:"
            e.backtrace.each { |b| puts "  #{b}" }
          end
        end
      end

      def really_run(args, debug)
        username = ENV['ANBLOG_USERNAME'] || fail("Must set ANBLOG_USERNAME")
        password = ENV['ANBLOG_PASSWORD'] || fail("Must set ANBLOG_PASSWORD")

        configuration = Anblog::Configuration.new do |c|
          c.debugging = debug
          c.username = username
          c.password = password
        end
        api_client = Anblog::ApiClient.new configuration
        post_api_client = Anblog::PostApi.new api_client
        editor = Editor.new ENV['EDITOR'] || "emacs"
        timer = Timer.new
        tmp_path = Dir.tmpdir

        opener = Opener.new(post_api_client, editor, timer, tmp_path)
        lister = Lister.new(post_api_client)

        s = Runner.new([
          OpenCommand.new(opener),
          ListCommand.new(lister),
                       ]).run args
        if s.length > 0
          puts s
        end
      end
    end
  end
end
