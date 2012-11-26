#!/usr/bin/ruby

require "FileUtils"

def mark_collect_stats(file)
  outname = file + ".bak"
  
  fin = File.new(file, "r")
  fout = File.new(outname, "w")

  while line = fin.gets
    #make sure this operation is idempotent
    line = line.sub(/\{@collect.stats\}/, "")
    line = line.sub(/\/\*\*/,"/** {@collect.stats}")
    fout.puts(line)
  end
  
  FileUtils.mv(outname, file)
end

def recursive_mark_collect_stats(dir)
  Dir.chdir(dir)

  Dir.foreach(".") {|file| 
                      if file != "." && file != ".." 
                        if File.directory?(file)
                          recursive_mark_collect_stats(file)
                        elsif file =~ /.*[.]java/
                          mark_collect_stats(file) 
                        end
                      end 
                   }
 
  Dir.chdir("..")
end

if __FILE__ == $0
  if ARGV[0] == nil
    puts "usage is ./gen-packages.rb <root_dir>+"
    exit 
  end
  ARGV.each { |dir|
    recursive_mark_collect_stats(dir)
  }
end
