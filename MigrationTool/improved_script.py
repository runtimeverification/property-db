#!/usr/bin/python2.7

import sys
import getopt
import os
import shutil
import re
import diff_match_patch as dmp
import datetime
from bs4 import BeautifulSoup
from lxml import etree

# install lxml --> sudo apt-get install python-lxml


## my feeling is that although dmp provides a patch functionality, it's too unpredictable and we should manually
## control patches for accuracy
class dictionary_script:

	def __init__(self):
		self.old_dir = ''
		self.new_dir = ''
		self.output_dir = ''

		self.old_file_flag_dict = dict([])
		self.new_file_flag_dict = dict([])

		self.old_file_translation = dict([])
		self.new_file_translation = dict([])

		self.old_file_no_tags_html = dict([])
		self.new_file_no_tags_html = dict([])
		
		self.old_prop_flag = dict([])
		self.old_desc_flag = dict([])
		self.old_new_flag = dict([])

		self.pattern_flag = dict([])

		self.old_file_pattern_count = dict([])

		self.which_file = dict([])

		self.new_suffix_dict = dict([])

		self.f_old = ''
		self.f_out = ''
		self.working_output = ''
		
		self.new_path = ''
		self.out_path = ''

		self.count_old = dict([])
		self.count_new = dict([])
		self.common_directories = []

		self.match_num = 0
		self.comment_num = 0

		self.diff_tool = dmp.diff_match_patch() 

		return

	def get_cmd_line_args(self):
		# take command line arguments from user
		try:
			options, arguments = getopt.getopt(sys.argv[1:] ,'', ['old=', 'new=', 'out='])
		except getopt.GetoptError:
			print 'upgrade.py --old <old directory> --new <new directory> --out <output directory>' 
			sys.exit(2)

		# parse input options 
		for option in options:
			if "old" in option[0]:
				self.old_dir = option
			elif "new" in option[0]:
				self.new_dir = option
			elif "out" in option[0]:
				self.output_dir = option
			else: 
				continue
		# if you're left with any arguments, you know there's a problem
		if len(arguments) > 0:
			print 'Did you forget a flag?' 
			sys.exit(2)

	def separate_files_direcs(self):
		
		# first check that old and new directories exist
		if not os.path.exists(self.old_dir[1]) or not os.path.exists(self.old_dir[1]): 
			print 'Old or new directory does not exist!'
			sys.exit(2)

		# check that output directory does not already exist, prevent accidental overwrite
		if os.path.exists(self.output_dir[1]):
			print 'Please specify an output directory that doesn\'t already exist!'
			sys.exit(2)
		os.makedirs(self.output_dir[1]);
		
		# compare old directory file names to new directory file names 
		old_files = os.listdir(self.old_dir[1])
		new_files = os.listdir(self.new_dir[1])
		

		# iterate thru and find files that don't exist in both directs
		# report dissimilarities in files_not_in_both_packages.txt
		old_not_in_new = []
		new_not_in_old =  []
		consistent_files = []
		for old_file in old_files:
			if old_file not in new_files:
				old_not_in_new.append(old_file)
			else:
				consistent_files.append(old_file)


		for new_file in new_files:
			if new_file not in old_files:
				new_not_in_old.append(new_file)

		if os.path.exists('./files_not_in_both_packages.txt'):
			print 'files_not_in_both_packages already exists! Results will be appended.'
		f = open('files_not_in_both_packages.txt', 'a')

		header_string = "***\n Comparing old directory " + self.old_dir[1] + " to new directory " + self.new_dir[1] + ".\n Merged results will be output to directory " + self.output_dir[1] + ".\n***\n"
		f.write(header_string)

		header_string = "DIRECTORY: " + self.output_dir[1] + "\nOld files or subdirectories not seen in new package:\n"
		f.write(header_string)
		for file in old_not_in_new:
			f.write(file + '\n')

		header_string = "DIRECTORY: " + self.output_dir[1] + "\nNew files or subdirectories not seen in old package:\n"
		f.write(header_string)
		for file in new_not_in_old:
			f.write(file + '\n')
		f.write('------\n')

		f.close()

		# copy consistent files from new directory to output directory
		# idea is we'll start with updated packages and then splice in the @property taglets
		for consistent_file in consistent_files:
			if "java" in consistent_file: 
				path_to_new = os.path.normpath(self.new_dir[1] + "/" + consistent_file)
				path_to_out	= os.path.normpath(self.output_dir[1] + "/" + consistent_file)
				shutil.copyfile(path_to_new, path_to_out)
			else: # check for consistency between subdirectories here too!
				self.common_directories.append(consistent_file)

				path_to_sub_new = os.path.normpath(self.new_dir[1] + "/" + consistent_file)
				path_to_sub_old = os.path.normpath(self.old_dir[1] + "/" + consistent_file)

				# could probably encapsulate in function (see above repeated code)!!!!!
				subdir_files_old = os.listdir(path_to_sub_old)
				subdir_files_new = os.listdir(path_to_sub_new)

				subdir_old_not_in_new = []
				subdir_new_not_in_old = []
				subdir_consistent = []


				for old_file in subdir_files_old:
					if old_file not in subdir_files_new:
						subdir_old_not_in_new.append(old_file)
					else:
						subdir_consistent.append(old_file)

				for new_file in subdir_files_new:
					if new_file not in subdir_files_old:
						subdir_new_not_in_old.append(new_file)

				f = open('files_not_in_both_packages.txt', 'a')
				f.write("SUBDIRECTORY: " + consistent_file + "\nNew version does not contain these old files:\n")
				f.write('\n'.join(subdir_old_not_in_new) + '\n')
				f.write("SUBDIRECTORY: " + consistent_file + "\nOld version does not contain these new files:\n")
				f.write('\n'.join(subdir_new_not_in_old) + '\n' + '------' + '\n')
				f.close()

				# make new subdirectory
				new_sub = os.path.normpath(self.output_dir[1] + "/" + consistent_file)
				os.makedirs(new_sub)

				for cons in subdir_consistent:
					if "java" in cons:
						path_to_new = os.path.normpath(self.new_dir[1] + "/" + consistent_file + "/" + cons)
						path_to_out	= os.path.normpath(self.output_dir[1] + "/" + consistent_file + "/" + cons)
						shutil.copyfile(path_to_new, path_to_out)

		return 

	def create_dictionaries_merge(self):

		# iterate thru files in output directory, compare them to files in old directory
		output_files = os.listdir(self.output_dir[1])
		output_files = [output_file for output_file in output_files if "java" in output_file]	

		for output_file in output_files:
			self.old_path = os.path.normpath(self.old_dir[1] + "/" + output_file)
			self.out_path = os.path.normpath(self.output_dir[1] + "/" + output_file)
			with open(self.old_path, 'r') as oldie:
				# read file into string
				self.f_old = oldie.read()

			with open(self.out_path, 'r') as outie:
				# read file into read string
				self.f_out = outie.read()

				# start working output file
				self.working_output = self.f_out


			self.food = open('report', 'a')


			pattern = re.finditer('/\*+([^*]|(\*+([^*/])))*\*+/', self.f_old)
			for p in pattern:
				self.comment_num = self.comment_num + 1
				found_comment = p.group(0) 
				found_comment = found_comment.split("* @", 1)[0]
				orig_comment = found_comment # grab comment without parameters list
				properties = re.finditer('{@property.open[ .:a-zA-Z0-9_]*}', found_comment)
				for l in properties:
					# output properties
					found_comment = found_comment.replace(l.group(0), "")
				javadoc_tags = re.finditer('{@[a-z]+[\n* ]+[^}]*}', found_comment)
				for tag in javadoc_tags:
					tag_text = re.search('(?<=[ ])[^}]*', tag.group(0))
					try:
						found_comment = re.sub(re.escape(tag.group(0)), tag_text.group(0), found_comment)
					except:
						found_comment = re.sub(re.escape(tag.group(0)), '', found_comment)
				soup = BeautifulSoup(found_comment, 'html.parser')
				found_comment = soup.get_text()
				no_tag_or_html = found_comment # grab no tags version
				found_comment = re.sub('[^0-9a-zA-Z]+', '', found_comment)
				found_comment = found_comment.replace("collectstats", "").replace("descriptionopen","").replace("descriptionclose","").replace("propertyopen","").replace("propertyclose","").replace("newopen","").replace("newclose","")
				self.old_file_translation[found_comment] = orig_comment
				self.old_file_no_tags_html[found_comment] = no_tag_or_html
				self.old_file_flag_dict[found_comment] = 0 # comments unattended
				self.which_file[found_comment] = output_file

			pattern = re.finditer('/\*+([^*]|(\*+([^*/])))*\*+/', self.f_out)
			for p in pattern:
				found_comment = p.group(0) 
				split = found_comment.split("* @", 1)
				found_comment = split[0]
				orig_comment = found_comment # grab comment without parameters list
				if len(split) > 1:
					self.new_suffix_dict[orig_comment] = split[1]
				javadoc_tags = re.finditer('{@[a-z]+[\n* ]+[^}]*}', found_comment)
				for tag in javadoc_tags:
					tag_text = re.search('(?<=[ ])[^}]*', tag.group(0))
					try:
						found_comment = re.sub(re.escape(tag.group(0)), tag_text.group(0), found_comment)
					except: # backslash in tag_text
						found_comment = re.sub(re.escape(tag.group(0)), '', found_comment)
				soup = BeautifulSoup(found_comment, 'html.parser')
				found_comment = soup.get_text()
				no_tag_or_html = found_comment # grab no tags version
				found_comment = re.sub('[^0-9a-zA-Z]+', '', found_comment)
				found_comment = found_comment.replace("collectstats", "").replace("descriptionopen","").replace("descriptionclose","").replace("propertyopen","").replace("propertyclose","").replace("newopen","").replace("newclose","")
				self.new_file_translation[found_comment] = orig_comment
				self.new_file_no_tags_html[found_comment] = no_tag_or_html


				self.new_file_flag_dict[found_comment] = 0 # comments unattended

			# find exact matches

			for key in self.old_file_flag_dict.keys():	
				if "collect.stats" in self.old_file_translation[key]: 
					if self.new_file_flag_dict.get(key) is not None:
					# self.match_num = self.match_num + 1
					# self.old_file_flag_dict[key] = 1
					# self.new_file_flag_dict[key] = 1

						self.make_merge(key, key)

					else: # if the comment isn't an annotation, pass over it
						if "collect.stats" in self.old_file_translation[key] and self.new_file_flag_dict.get(key) is None:
							for k in self.new_file_flag_dict.keys(): # diffs between raw text

									if (k[:len(k)/6] in key or k[-len(k)/6:] in key) and self.new_file_flag_dict[k] is not 1 and len(k) > 0 and abs( (float) (len(key) - len(k)) / len(k) ) < .3:
										differences = self.diff_tool.diff_main(key, k) 
										if self.diff_tool.diff_levenshtein(differences) < len(k)/4:
											self.make_merge(key, k)		
			

			# find near matches
			print "FILE: ", output_file
			time = datetime.datetime.now().time()
			print time 
			
			self.food.write("FILE: " + output_file + '\n')
			self.food.write("DIRECTORY: " + self.output_dir[1] + '\n')


			time = datetime.datetime.now().time()					
			print time

			# write new version of the file
			f = open(self.out_path, 'w')
			f.write(self.working_output)
			f.close

			# find the comments which didn't match at all (not exactly, not nearly!)
			for key in self.old_file_flag_dict.keys():
				if self.old_file_flag_dict[key] != 1 and "collect.stats" in self.old_file_translation[key] and (self.which_file[key]==output_file):
										
					# different patterns we will parse text for
					property_pattern = '(?<={@property[.]open)(.|\n)*?(?={@property[.]close})'
					description_pattern = '(?<={@description[.]open)(.|\n)*?(?={@description[.]close})'
					new_pattern = '(?<={@new[.]open)(.|\n)*?(?={@new[.]close})'

					# list of pattern we will search iteratively for
					pattern_list = [property_pattern, description_pattern, new_pattern]

					# flag so we only print out file name and nearest file signature if at least 1 pattern not already merged is found
					found_pattern = 0

					for pattern in pattern_list:
						old_text_desc_blocks = re.finditer(pattern, self.old_file_translation[key])
						for l in old_text_desc_blocks:

							# grab parsed text
							out_text = l.group(0)
							orig_body = out_text

							# should we abridge the text?
							if len(out_text) > 1000:
								out_text = out_text[:499] + "\n...\n" + out_text[len(out_text)-499:len(out_text)]

							# contains headers and footers for writing report file
							print_suite = { description_pattern:["* {@description.open", "{@description.close}", "DESC BLOCK::: ", "ADDRESSED DESC::: []\n" ], property_pattern:["* {@property.open", "{@property.close}", "PROP BLOCK:::", "ADDRESSED PROP::: []\n"], new_pattern:["* {@new.open", "{@new.close}", "NEW BLOCK:::", "ADDRESSED NEW::: []\n" ] }

						

							if (key + orig_body) in self.pattern_flag.keys() and self.pattern_flag[key + orig_body]==1:
								continue

							# if we find at least 1 pattern not already merged, then we can print file name and func sig, but never print it again!
							if found_pattern == 0:
								# find comment in old file so we can look around it and try to grab the nearest function signature (non-greedy!)
								find_comment = re.search(re.escape(self.old_file_translation[key]), self.f_old)
								if find_comment is not None and find_comment.group(0) is not None:
									start_loc = find_comment.end(0)
									text = self.f_old[start_loc:]
									find_sig = re.search('(?<=[*]/)(.|\n)*?(?={)', text)
									if find_sig is not None and find_sig.group(0) is not None:
										self.food.write("NEAREST SIGNATURE::: " + find_sig.group(0) + "\n")
								self.food.write("ORIG COMMENT::: " + self.old_file_translation[key] + "\n" + "~" + "\n")
								found_pattern = 1

							out_text = print_suite[pattern][0] + out_text + print_suite[pattern][1]
							self.food.write(print_suite[pattern][2] + out_text + "\n")
							self.food.write(print_suite[pattern][3])

							self.food.write("---------\n")
						
		return

	def run_remaining_subdirectories(self):
		# hold onto parent directory	
		start_old_dir = self.old_dir
		start_new_dir = self.new_dir
		start_out_dir = self.output_dir

		for common_dir in self.common_directories:
			if os.path.isdir(os.path.normpath(self.old_dir[1] + "/" + common_dir)):
				old_dir = os.path.normpath(self.old_dir[1] + "/" + common_dir)
				files_in_old_dir = os.listdir(old_dir)

				new_dir = os.path.normpath(self.new_dir[1] + "/" + common_dir)
				files_in_new_dir = os.listdir(new_dir)

				in_both = [file for file in files_in_old_dir if file in files_in_new_dir]
				in_both_path = [ os.path.normpath(common_dir + "/" + file) for file in in_both ]
				in_both_path = [ file for file in in_both_path if os.path.isdir(os.path.normpath(self.old_dir[1] + "/" + file)) ]

				old_not_new = [ file for file in files_in_old_dir if file not in files_in_new_dir ]
				old_not_new_path = [ os.path.normpath(common_dir + "/" + file) for file in old_not_new ]
				old_not_new_path = [ file for file in old_not_new_path if os.path.isdir(os.path.normpath(self.old_dir[1] + "/" + file)) ]

				new_not_old = [ file for file in files_in_new_dir if file not in files_in_old_dir]
				new_not_old_path = [ os.path.normpath(common_dir + "/" + file) for file in new_not_old ]
				new_not_old_path = [ file for file in new_not_old_path if os.path.isdir(os.path.normpath(self.old_dir[1] + "/" + file)) ]
				
				f = open('files_not_in_both_packages.txt', 'a')
				f.write("SUBDIRECTORY: " + common_dir + "\nNew version does not contain these old directories:\n")
				f.write('\n'.join(old_not_new_path) + '\n')
				f.write("SUBDIRECTORY: " + common_dir + "\nOld version does not contain these new directories:\n")
				f.write('\n'.join(new_not_old_path) + '\n' + '------' + '\n')
				f.close()

				if len(in_both_path) >= 1:
					for item in in_both_path:
						self.common_directories.append(item)
		
			print common_dir
			new_d = dictionary_script()
			new_d.old_dir = start_old_dir
			new_d.new_dir = start_new_dir
			new_d.output_dir = start_out_dir

			new_d.old_dir = [None, os.path.normpath(self.old_dir[1] + "/" + common_dir)]
			new_d.new_dir  = [None, os.path.normpath(self.new_dir[1] + "/" + common_dir)]
			new_d.output_dir  = [None, os.path.normpath(self.output_dir[1] + "/" + common_dir)]

			if not os.path.isdir(new_d.output_dir[1]):
				new_d.separate_files_direcs()
			new_d.create_dictionaries_merge()
		return

	def make_merge(self, old_key, new_key):
		# keep track of evolutions in output
		evolving_text = self.new_file_translation[new_key]

		# different patterns we will parse text for
		property_pattern = '(?<={@property[.]open)(.|\n)*?(?={@property[.]close})'
		description_pattern = '(?<={@description[.]open)(.|\n)*?(?={@description[.]close})'
		new_pattern = '(?<={@new[.]open)(.|\n)*?(?={@new[.]close})'

		# list of pattern we will search iteratively for
		pattern_list = [property_pattern, description_pattern, new_pattern]

		for pattern in pattern_list:
			old_text_prop_blocks = re.finditer(pattern, self.old_file_translation[old_key])
			for desc in old_text_prop_blocks:
				text = desc.group(0)
				# grab orignal text body so we can mark that we've dealt with it at the proper time
				original_text = text
				# grab pattern name (if it exists --> description and new tags don't tend to have names, but properties do)
				prop_name = re.search('(.|\n)*?(?=})', text)
				prop = prop_name.group(0)
				text_grab = re.finditer('(?<=})(.|\n)*', text)
				text = [ i.group(0) for i in text_grab ]
				text = ''.join(text)
				soup = BeautifulSoup(text, 'html.parser')
				text = soup.get_text() # take out html
				javadoc_tags = re.finditer('{@[a-z]+[\n* ]+[^}]*}', text) # take out dang javadocs (had to parse prop body) --> could have other data structure -- function
				for tag in javadoc_tags:
					tag_text = re.search('(?<=[ ])[^}]*', tag.group(0))
					try:
						text = re.sub(re.escape(tag.group(0)), tag_text.group(0), text)
					except: # backslash in tag_text
						text = re.sub(re.escape(tag.group(0)), '', text)
				text = re.sub('[^0-9a-zA-Z_ ]+', '', text)
				text = text.split()

				#initialize array to keep track of individual word counts in old comment
				count_text = [ 0 for i in text ]

				# grab ahold of new comment (before we process it to make it easier to handle)
				pure_new = self.new_file_translation[new_key].split(' ')
				pure_new_strip = dict([])
				new_text = self.new_file_no_tags_html[new_key] # here we pull the new comment we stripped at top of this function
				new_text = re.sub('[^0-9a-zA-Z_ ]+', '', new_text)
				new_text = new_text.split()


				# connect stripped text to actual text (for insertion)
				""" it's worth noting that we use the stripped text to make meaningful matches with the old comment
				 but we have to hold onto the nasty original version of the new comment to orient where we'll insert changes """
				i = 0
				j = 0
				connect = [ 0 for n in new_text ] # connect array will connect stripped new comment text to indices of orginal new comment text
				while i < len(new_text):
					while j < len(pure_new):
						soup = BeautifulSoup(pure_new[j], 'html.parser') # remove html
						pure_new_no_tags = soup.get_text()
						soup = BeautifulSoup(pure_new_no_tags, "xml") # remove the xml
						pure_new_no_tags = soup.get_text()
						pure_new_no_tags = re.sub('[^0-9a-zA-Z_ ]+', '', pure_new[j])

						if new_text[i] in pure_new_no_tags:
							connect[i] = j # store index where we have a match
							pure_new_strip[i] = pure_new_no_tags # store text in comment which matches
						
							j = j + 1
							break;
						else:
							j = j + 1
					i = i + 1

				for  t in text:
					count_text[text.index(t)] += new_text.count(t) # now we actually find word counts
					word_index = text.index(t)
					
					if count_text[word_index] == 1: # found a lodging point with which to survey new comment
						located_match = new_text.index(t)
						start_match = located_match
						try: 
							while start_match > 0:
								if ((len(text) <= 3 and text[0] in pure_new_strip[start_match]) or (text[0] in pure_new_strip[start_match] and text[1] in pure_new_strip[start_match+1] and text[2] in pure_new_strip[start_match+2])):
									break
								else:
									start_match = start_match - 1

							end_match = located_match
							while (end_match < len(connect)):
								if ((len(text) <= 3 and text[len(text)-1] in pure_new_strip[end_match]) or (text[len(text)-1] in pure_new_strip[end_match]) and (text[len(text)-2] in pure_new_strip[end_match-1]) and (text[len(text)-3] in pure_new_strip[end_match-2])): # make sure last two words match
									break;
								else:
									end_match = end_match + 1

							if pattern == property_pattern:
								test = ["     \n* {@property.open" + prop + "}\n" + "     *"]
								test.extend(pure_new[connect[start_match]:connect[end_match]+1]) 
								new_out = ' '.join(test) + "\n     * {@property.close}"	
								edit = re.sub(re.escape(' '.join(pure_new[connect[start_match]:connect[end_match]+1])), new_out, evolving_text)
								self.working_output = re.sub(re.escape(evolving_text), edit, self.working_output)
								evolving_text = edit
								self.pattern_flag[key + original_text] = 1
							elif pattern == description_pattern:
								test = ["     \n* {@description.open" + prop + "}\n" + "     *"]
								test.extend(pure_new[connect[start_match]:connect[end_match]+1]) 
								new_out = ' '.join(test) + "\n     * {@description.close}"
								edit = re.sub(re.escape(' '.join(pure_new[connect[start_match]:connect[end_match]+1])), new_out, evolving_text)
								self.working_output = re.sub(re.escape(evolving_text), edit, self.working_output)
								evolving_text = edit
								self.pattern_flag[key + original_text] = 1
							else:
								test = ["     \n* {@new.open" + prop + "}\n" + "     *"]
								test.extend(pure_new[connect[start_match]:connect[end_match]+1]) 
								new_out = ' '.join(test) + "\n     * {@new.close}"
								edit = re.sub(re.escape(' '.join(pure_new[connect[start_match]:connect[end_match]+1])), new_out, evolving_text)
								self.working_output = re.sub(re.escape(evolving_text), edit, self.working_output)
								evolving_text = edit
								self.pattern_flag[key + original_text] = 1
							break
						except: # if there's an error matching text, move on
							break
		# add collect.stats header to new file comment
		new_comment_with_collect = re.sub(re.escape("/**"), "/** {@collect.stats}", evolving_text)
		self.working_output = re.sub(re.escape(evolving_text), new_comment_with_collect, self.working_output)
		if old_key != new_key:
			print new_comment_with_collect
		return

	def run(self):
		self.get_cmd_line_args()
		self.separate_files_direcs()
		self.create_dictionaries_merge()
		self.run_remaining_subdirectories()
		return


if __name__ == "__main__":
	d = dictionary_script()
	d.run()

	
