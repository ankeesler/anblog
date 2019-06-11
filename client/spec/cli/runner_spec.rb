require 'anblog/cli/runner'

describe Anblog::CLI::Runner do
  let(:tuna_command) { double('tuna_command') }
  let(:fish_command) { double('fish_command') }
  let(:runner) { Anblog::CLI::Runner.new [tuna_command, fish_command] }

  context 'valid command is passed' do
    context 'one arg is passed' do
      it 'calls the action of that command with one arg' do
        expect(tuna_command).to receive(:name).and_return('tuna')

        expect(tuna_command).to receive(:action).with([]).and_return('some tuna stuff')

        s = runner.run ['tuna']
        expect(s).to eq('some tuna stuff')
      end
    end

    context 'multiple args are passed' do
      it 'calls the action of that command with multiple args' do
        expect(tuna_command).to receive(:name).and_return('tuna')
        expect(fish_command).to receive(:name).and_return('fish')

        expect(fish_command).to receive(:action).with(['arg0', 'arg1']).and_return('some fish stuff')

        s = runner.run ['fish', 'arg0', 'arg1']
        expect(s).to eq('some fish stuff')
      end
    end
  end

  context 'invalid command is passed' do
    it 'prints unknown command error' do
      expect(tuna_command).to receive(:name).and_return('tuna')
      expect(fish_command).to receive(:name).and_return('fish')

      s = runner.run ['marlin', 'arg0', 'arg1']
      expect(s).to eq('unknown command: marlin')
    end
  end

  context 'no args are passed' do
    it 'prints usage information' do
      expect(tuna_command).to receive(:name).and_return('tuna')
      expect(tuna_command).to receive(:description).and_return('tuna description')
      expect(fish_command).to receive(:name).and_return('fish')
      expect(fish_command).to receive(:description).and_return('fish description')

      s = runner.run []
      expect(s).to eq("anblog cli\n\nusage: anblog tuna\n  tuna description\nusage: anblog fish\n  fish description\n")
      # TODO: make this a multiline string!
    end
  end

end
