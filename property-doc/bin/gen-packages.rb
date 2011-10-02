#!/usr/bin/ruby

$packages = ""

def recursive_package_build(dir, parent)
  package = parent ? (parent + "." + dir) : dir
  Dir.chdir(dir) 
  #puts Dir.pwd
  javaFileFound = false
  Dir.foreach(".") {|file| 
                      if file != "." && file != ".." 
                        if File.directory?(file)
                          recursive_package_build(file, package)
                        elsif !javaFileFound && file =~ /.*[.]java/
                          javaFileFound = true
                          $packages += " " + package 
                        end
                      end 
                   }
  Dir.chdir("..")
end

if __FILE__ == $0
  if ARGV[0] == nil
    puts "usage is rb gen-packages.rb <root_dir>"
    exit 
  end
  ARGV.each { |dir|
    recursive_package_build(dir, nil)
  }
  puts $packages
end


