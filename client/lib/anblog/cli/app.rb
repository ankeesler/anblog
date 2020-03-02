require 'anblog'

# TODO: but why!
require 'anblog/cli/cat_command'
require 'anblog/cli/catter'
require 'anblog/cli/editor'
require 'anblog/cli/filewatcher'
require 'anblog/cli/grep_command'
require 'anblog/cli/help_command'
require 'anblog/cli/label_command'
require 'anblog/cli/lister'
require 'anblog/cli/list_command'
require 'anblog/cli/opener'
require 'anblog/cli/open_command'
require 'anblog/cli/runner'
require 'anblog/cli/rm_command'
require 'anblog/cli/stat_command'
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
          c.host = ENV['ANBLOG_HOST'] || 'anblog.cfapps.io'
          c.scheme = ENV['ANBLOG_SCHEME'] || 'https'
        end
        api_client = Anblog::ApiClient.new configuration
        post_api_client = Anblog::PostApi.new api_client
        editor = Editor.new ENV['EDITOR'] || "emacs"
        filewatcher = Filewatcher.new
        timer = Timer.new
        tmp_path = Dir.tmpdir

        catter = Catter.new(post_api_client)
        lister = Lister.new(post_api_client)
        opener = Opener.new(post_api_client, editor, timer, filewatcher, tmp_path)

        s = Runner.new([
          CatCommand.new(catter),
          GrepCommand.new(post_api_client),
          HelpCommand.new,
          LabelCommand.new(post_api_client),
          ListCommand.new(lister),
          OpenCommand.new(opener),
          RmCommand.new(post_api_client),
          StatCommand.new(post_api_client),
                       ]).run args
        if s.length > 0
          puts s
        end
      end
    end
  end
end
