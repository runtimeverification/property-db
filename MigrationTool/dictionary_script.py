#!/usr/bin/python2.7

import sys
import getopt
import os
import shutil
import re
import diff_match_patch as dmp
import datetime
from bs4 import BeautifulSoup

class dictionary_script:

	def __init__(self):
		self.old_dir = ''
		self.new_dir = ''
		self.output_dir = ''

		self.old_file_flag_dict = dict([])
		self.new_file_flag_dict = dict([])

		self.old_file_translation = dict([])
		self.new_file_translation = dict([])

		self.new_suffix_dict = dict([])

		self.f_old = ''
		self.f_out = ''
		self.working_output = ''
		
		self.new_path = ''
		self.out_path = ''

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


			# self.fucked = open('report', 'a')
			self.darn = open('check_rough_merge', 'a')

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
				found_comment = re.sub('[^0-9a-zA-Z]+', '', found_comment)
				found_comment = found_comment.replace("collectstats", "").replace("descriptionopen","").replace("descriptionclose","").replace("propertyopen","").replace("propertyclose","").replace("newopen","").replace("newclose","")
				self.old_file_translation[found_comment] = orig_comment

				self.old_file_flag_dict[found_comment] = 0 # comments unattended

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
				found_comment = re.sub('[^0-9a-zA-Z]+', '', found_comment)
				found_comment = found_comment.replace("collectstats", "").replace("descriptionopen","").replace("descriptionclose","").replace("propertyopen","").replace("propertyclose","").replace("newopen","").replace("newclose","")
				self.new_file_translation[found_comment] = orig_comment

				self.new_file_flag_dict[found_comment] = 0 # comments unattended

			# find exact matches

			for key in self.old_file_flag_dict.keys():	
				if "collect.stats" in self.old_file_translation[key] and self.new_file_flag_dict.get(key) is not None:
					self.match_num = self.match_num + 1
					self.old_file_flag_dict[key] = 1
					self.new_file_flag_dict[key] = 1

					cool_diffs = self.diff_tool.diff_main(self.new_file_translation[key], self.old_file_translation[key])
					better_diffs = []
					for t in cool_diffs:
						if ((t[0] is 1) and ("collect.stats" in t[1]) or ("description.open" in t[1]) or ("description.close" in t[1]) or ("property.open" in t[1]) or ("property.close" in t[1]) or ("new.open" in t[1]) or ("new.close" in t[1])) or (t[0] is 0):
							better_diffs.append(t)
						else: ##ERROR HANDLE
							# print "ERROR 1" -- shouldn't include in diff even!**
							pass
					patches = self.diff_tool.patch_make(self.new_file_translation[key], better_diffs)
					patched_output = self.diff_tool.patch_apply(patches, self.new_file_translation[key])
					self.working_output = re.sub(re.escape(self.new_file_translation[key]), patched_output[0], self.working_output)
				else: ## ERROR HANDLE
					# print "ERROR 2"
					# print self.old_file_translation[key] #-- shouldn't even care, not relevant comment (not exact match!)**
					pass
			
			string1=[]
			string2=[]

			# find near matches
			print "FILE: ", output_file
			time = datetime.datetime.now().time()
			print time 

			# self.fucked.write("FILE: " + output_file + '\n')
			# self.fucked.write("DIRECTORY: " + self.output_dir[1] + '\n')
			self.darn.write("FILE: " + output_file + '\n')
			self.darn.write("DIRECTORY: " + self.output_dir[1] + '\n')

			for key in self.old_file_flag_dict.keys():
				if "collect.stats" in self.old_file_translation[key] and self.old_file_flag_dict[key] is not 1:			
					for k in self.new_file_flag_dict.keys(): # diffs between raw text

						if (k[:len(k)/6] in key or k[-len(k)/6:] in key) and self.new_file_flag_dict[k] is not 1 and len(k) > 0 and abs( (float) (len(key) - len(k)) / len(k) ) < .3:
							differences = self.diff_tool.diff_main(key, k) 
							if self.diff_tool.diff_levenshtein(differences) < len(k)/4:
								string2.append(key)
								string2.append(k)
								self.old_file_flag_dict[key] = 1
								self.new_file_flag_dict[k] = 1
								
								old_text = self.old_file_translation[key]
								new_text = self.new_file_translation[k]


								# old_text_desc_blocks = re.finditer('(?<={@property[.]open})(.|\n)*?(?={@property[.]close})', old_text)
								# for old_text_desc_block in old_text_desc_blocks:
								# 	#print old_text_desc_block.group(0)
								# 	old_loc = old_text_desc_block.start(0)
								# 	match_loc = self.diff_tool.match_main(new_text, re.escape(old_text_desc_block.group(0)), old_loc)
								# 	#print new_text[:match_loc] # got all desc blocks
								# 	sub_diffs = self.diff_tool.diff_main(new_text[match_loc:], '{@property.open}\n' + old_text_desc_block.group(0) + '{@property.close}\n')
								# 	new_diffs = []
								# 	for diff in sub_diffs:
								# 		if (diff[0] is 0) or (diff[0] is 1):
								# 			new_diff = [diff[0], diff[1].replace('\r', '')]
								# 			new_diffs.append(new_diff)
								# 		else:
								# 			pass

								#	print match_loc

								# self.diff_tool.match_main(new_text, old_text_desc_block.group(0))

								## too dumb an approach --> parse subsections (@desc.open, @property.open) & match subtext

								# match description block
								# (?<={@description[.]open})(.|\n)*(?={@description[.]close})

								# match property block 
								# (?<={@property[.]open})(.|\n)*(?={@property[.]close})

								# match new block 
								# (?<={@new[.]open})(.|\n)*(?={@new[.]close})
								



								good_diffs = self.diff_tool.diff_main(self.new_file_translation[k], self.old_file_translation[key])
								self.diff_tool.diff_cleanupSemantic(good_diffs)
							
								new_diffs = []
								for diff in good_diffs:
									if (diff[0] is 0) or (diff[0] is 1 and (("collect.stats" in diff[1]) or ("description.open" in diff[1]) or ("description.close" in diff[1]) or ("property.open" in diff[1]) or ("property.close" in diff[1]) or ("new.open" in diff[1]) or ("new.close" in diff[1]))):
										new_diff = [diff[0], diff[1].replace('\r', '')]
										new_diffs.append(new_diff)
									else:
										pass
								self.diff_tool.diff_cleanupSemantic(new_diffs)
								self.diff_tool.diff_cleanupEfficiency(new_diffs)
								## INSTEAD OF TRYING TO MERGE IN END TAG, JUST PUT IT RIGHT BEFORE THE PARAMETER LIST!
								patches = self.diff_tool.patch_make(self.new_file_translation[k], new_diffs)
								patched_output = self.diff_tool.patch_apply(patches, self.new_file_translation[k])
								if 'property.open' in patched_output[0]:
									# print new_diffs
									# print patched_output[0]
									text = self.diff_tool.patch_toText(patches)
									# print text
									# # print patched_output[0] ## if ^M in output, doesn't print to console
									# print "^Mtest"
								self.working_output = re.sub(re.escape(self.new_file_translation[k]), patched_output[0], self.working_output) # sometimes splices text with insertion
								self.darn.write("OLD COMM:: " + self.old_file_translation[key] + "\n")

			
						else:
							## ERROR HANDLE --- misses when looking thru dictionary -- process later**
							# print "ERROR 3"
							# print self.old_file_translation[key]
							# print self.new_file_translation[k]
							pass
				else: ## ERROR HANDLE
					# print "ERROR 4" -- shouldn't even worry about these comments**
					pass
			time = datetime.datetime.now().time()					
			print time

			# for key in self.old_file_flag_dict.keys():
			# 	if self.old_file_flag_dict[key] is not 1 and "collect.stats" in self.old_file_translation[key]:
			# 		old_text_desc_blocks = re.finditer('(?<={@description[.]open})(.|\n)*?(?={@description[.]close})', self.old_file_translation[key])
			# 		for l in old_text_desc_blocks:
			# 			self.fucked.write("DESC BLOCK::: " + l.group(0) + "\n")
						
			# 		## property can have name
			# 		old_text_prop_blocks = re.finditer('(?<={@property[.]open)(.|\n)*?(?={@property[.]close})', self.old_file_translation[key])
			# 		for a in old_text_prop_blocks:
			# 			self.fucked.write("PROP BLOCK:::" + a.group(0) + "\n")

			# 		old_text_new_blocks = re.finditer('(?<={@new[.]open})(.|\n)*?(?={@new[.]close})', self.old_file_translation[key])
			# 		for a in old_text_new_blocks:
			# 			self.fucked.write("NEW BLOCK:::" + a.group(0) + "\n")


			# self.fucked.close()

			f = open(self.out_path, 'w')
			f.write(self.working_output)
			f.close

	def run_remaining_subdirectories(self):
		# hold onto parent directory	
		start_old_dir = self.old_dir
		start_new_dir = self.new_dir
		start_out_dir = self.output_dir

	
		# clean this up, gee wiz
		for common_dir in self.common_directories:
			# f = open('directory_traversal', 'a')
			# f.write(common_dir)
			# f.close()
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
			new_d = dictionary_script() # should save time by creating new dictionaries
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



	def run(self):
		self.get_cmd_line_args()
		self.separate_files_direcs()
		self.create_dictionaries_merge()
		self.run_remaining_subdirectories()
		return


if __name__ == "__main__":
	d = dictionary_script()
	d.run()


