* Each byte represents a symbol in the input sequence (each byte is a symbol)

* Constant access of a given symbol when the file is loaded into RAM. My understanding is that upon loading a file into the RAM we will not want to decompress the whole thing but use parts of it, which is why the constant access is important. In order to do this, in the RAM what I would do is, load the dictionary onto the RAM (the dictionary maps codes to the actual symbols). In my implmentation I have made it a point to have to dictionary at the beginning of the file for easy loading. And then after that, is the file which can be accessed in constant time due to each code being the same length. So codeLength * index + offset (offset is the amount of memory it took to store the dictionary) will give us the element at the required index.

* Data is uniform i.e. all the symbols are nearly equally likely. Which means that algorithms like Huffman and LZ will not be effective enough. 

* Number of symbols in the sequence is small and can thus be stored in a small number of bits. 

* An application that takes an input file and produces a file with a compact representation. 

* An application that takes the compact representation and produces the actual decompressed file. 

* Stress on being able to allow constant-time access. 

* Space savings should be larger with smaller alphabet size (need to remember that the sequence will be really long and thus, will cause memory problems. But don't assume that it's too long).

* Input file should be treated as binary rather than text. 

* README file should be included. Design choices, and any features or limitations of my implementations. 

* Evaluations:
	* Code quality and correctness. -> Done. 
	* Error dectection and handling/reporting. -> Done.
	* Appropriate use of commenting. -> Done. 
	* Documentation on how to build and run your solution. 
	* Use of appropriate build tools. -> Done.
	* Edge case handling. -> Done.
	* Presence of automable testing. -> Done

* Submission as a zip file.

