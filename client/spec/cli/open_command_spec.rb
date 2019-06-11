require 'anblog/cli/open_command'
require 'anblog/cli/opener'

describe Anblog::CLI::OpenCommand do
  let(:opener) { instance_double(Anblog::CLI::Opener) }
  let(:open_command) { Anblog::CLI::OpenCommand.new opener }

  context 'no args are passed' do
    it 'prints usage' do
      s = open_command.action []
      expect(s).to eq('usage: anblog open <path>')
    end
  end

  context 'path argument is passed' do
    it 'calls the opener' do
      expect(opener).to receive(:open).with('.some.path')
      s = open_command.action ['.some.path']
      expect(s).to eq('')
    end
  end
end
