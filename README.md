## Maze solve generator
# Description
Implementation of maze solution generator.The software consists to generate binaries files in `example.mze` with the next format:
# Use
Run the program with the standard input (see the solution in a SVG format) and output (generate maze  binaries files), ensure get java 11 + version:
 * Standard input: `cat file.mze | java -jar target/proyecto > solution.svg` where "file" is the name of your file with mze format and
    "solution" the name of the solution file, the last one being whatever name you want, the ">" implies output redirection. `cat` is exclusive
   to Unix based system Linux/MacOS, if you are in windows can use `type` instead of `cat` command.
 * Standard Output: `java -jar target/proyecto3.jar -g -w X -h Y > file.mze`  where  X and Y are the width and height respectively.
# Examples
