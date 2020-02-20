require 'anblog/cli/filewatcher'

describe Anblog::CLI::Filewatcher do
  let(:file) { '/tmp/some-file.txt' }

  before(:each) do
    File.open(file, "w") {|f| f.write("tuna") }
  end

  after(:each) do
    #File.delete(file)
  end

  context 'file is created' do
    it 'calls :on_create' do
      File.delete(file)

      c = double("callbacks")
      expect(c).to receive(:on_create).with file
      fw = Anblog::CLI::Filewatcher.new(file, c)

      fw.start
      sleep 2 # hack
      File.open(file, "w") {|f| f.write("fish") }
      fw.stop
    end
  end

  context 'file is updated' do
    it 'calls :on_update' do
      c = double("callbacks")
      expect(c).to receive(:on_update)
      fw = Anblog::CLI::Filewatcher.new(file, c)

      fw.start
      sleep 2 # hack
      File.open(file, "w") {|f| f.write("fish") }
      fw.stop
    end
  end

  context 'file is deleted', :focus do
    it 'calls :on_delete' do
      c = double("callbacks")
      expect(c).to receive(:on_delete)
      fw = Anblog::CLI::Filewatcher.new(file, c)

      fw.start
      File.delete(file)
      fw.stop
    end
  end
end
