## Maze solve generator
# Description
Implementation of maze solution generator. Generate binary maze files using algorithms as DFS (Depth First Search) and others, solve them with the minimun trajectory algorithm
and represent the solution in SVG format file. 
For each room in the maze has a bytes score that being the four most significant bits the score of the room, and the other
four most insignificant bits implies the room status, the door is or not open:
 * 0010: implies north door of the room is open.
 * 0001: implies east door of the room is open.
 * 1000: implies west door of the room is open.
Obvoiusly, you would have rooms with more door opened. 
The software consists to generate binaries files in `example.mze` with the next format:
 * The first four bytes from the file represents de word `MAZE` and the other n bytes the room`s score, which implies that we need to encode and decode each room write
   binaries files.
 * There has two rooms with don`t have two with te rules score expection, these are the entry and exist rooms. 
# Use
Run the program with the standard input (see the solution in a SVG format) and output (generate maze  binaries files), ensure get java 11 + version:
 * Standard input: `cat file.mze | java -jar target/proyecto > solution.svg` where "file" is the name of your file with mze format and
    "solution" the name of the solution file, the last one being whatever name you want, the ">" implies output redirection. `cat` is exclusive
   to Unix based system Linux/MacOS, if you are in windows can use `type` instead of `cat` command.
 * Standard Output: `java -jar target/proyecto3.jar -g -w X -h Y > file.mze`  where  X and Y are the width and height respectively.
# Examples
