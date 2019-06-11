module Anblog
  module CLI
    class Editor
      def initialize(editor)
        @editor = editor
      end

      def edit(file)
        system(@editor, file) || raise("Failed to run editor")
      end
    end
  end
end
